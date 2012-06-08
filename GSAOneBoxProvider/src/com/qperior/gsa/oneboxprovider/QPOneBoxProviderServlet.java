/*
 * Copyright 2012 Ralf Ovelgoenne, Q_PERIOR AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.qperior.gsa.oneboxprovider;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;

import com.qperior.gsa.oneboxprovider.provider.QPIProvider;
import com.qperior.gsa.oneboxprovider.provider.QPProviderInvoker;
import com.qperior.gsa.oneboxprovider.results.QPIOneBoxResults;
import com.qperior.gsa.oneboxprovider.results.QPOneBoxResults;
import com.qperior.gsa.oneboxprovider.results.QPResultCode;
import com.qperior.gsa.oneboxprovider.security.QPISecurityProvider;
import com.qperior.gsa.oneboxprovider.util.QPLogger;
import com.qperior.gsa.oneboxprovider.util.QPProperties;
import com.qperior.gsa.oneboxprovider.util.exception.QPProviderException;
import com.qperior.gsa.oneboxprovider.util.exception.QPRequestValidateException;

/**
 * Base class servlet for providing OneBox results.
 * <p>
 * Services GET method requests from OneBox clients by unpacking their
 * request parameter and delegating to one of the implemented
 * {@link QPIProvider} depending on configurated {@link QPProperties#getProvider()}.
 * To invoke the provider it uses the {@link QPProviderInvoker}.
 * <p>
 * Each provider need to implement the provider {@link QPIProvider} and 
 * the security {@link QPISecurityProvider} interface.
 * 
 * @author Ralf Ovelgoenne
 * 
 * @see QPOneBoxResults
 */
public class QPOneBoxProviderServlet extends HttpServlet
{
	private static final long serialVersionUID = -1645084915849017506L;
	
	private Log log = QPLogger.getLogger(this.getClass());
	
	/**
	 * 
	 */
	protected String webAppBaseURL;
	
	/**
	 * Called by the application server's servlet runner when GET method
	 * requests are made for this servlet.  OneBox clients (such as the
	 * Google Search Appliance) make HTTP requests to OneBox providers
	 * exclusivley using the GET method.
	 */
	@Override
	public void doGet (HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		logHeaders(request);
		this.webAppBaseURL = request.getScheme() + "://" +
						request.getServerName() + ":" + request.getServerPort() +
						request.getContextPath() + "/";
		
		processRequest(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String resString = "Method 'doPost' is not supported!";		
		this.log.warn(resString);
		
		// Defines response MIME type
		response.setContentType("text/xml");
		// Wirte response back as XML string
		PrintWriter out = response.getWriter();
		out.print(resString);
		out.close();
	}

	/**
	 * Convenience method for logging the HTTP headers sent in the request.  This
	 * will log to the application server's log channel set up for this servlet
	 * and is purely meant for use during debugging. 
	 * 
	 * @param request HttpServletRequest
	 */
	private void logHeaders(HttpServletRequest request)
	{	
		this.log.info("--------------------------");
		this.log.info("Request-Headers:");
		@SuppressWarnings("unchecked")
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String)headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			this.log.info("["+headerName +" = "+headerValue+"]");
		}
		this.log.info("--------------------------");
	}
	
	/**
	 * Top level request processor for this OneBox provider.
	 * <p>
	 * Unpacks the HTTP requests parameter and passes them into one of the
	 * <code>provideOneBoxResults</code> methods, delegating to one of the implemented
	 * {@link #QPIProvider QPIProvider} methods depending on configurated
	 * {@link QPProperties#getProviderType()}.
	 * <p>
	 * Returns an HTTP response with XML that adhere's to the Google DTD
	 * defining the schema for OneBox for Enterprise provider results.
	 * 
	 * @param request 
	 * @param response 
	 * @throws ServletException 
	 * @throws IOException 
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		QPIOneBoxResults res = null;
		
		try {
			try {
				
				// read in the request parameter
				QPCallParameter callParameter = new QPCallParameter(request);
				this.log.info("Servlet 1: " + callParameter.toString());			
				
				// get the invoker for the right provider
		        QPProviderInvoker invoker = QPProviderInvoker.createProviderInvoker(QPProperties.getProvider(), QPProperties.getSecurityProvider(), callParameter);
		        this.log.info("Servlet 2: " + invoker.toString());
							
				// AuthType has to be set, see constructor of QPCallParameter
				if (invoker.getSecurityProvider().getActualAuthType() == null)
				{
					res = new QPOneBoxResults();
					this.log.error("User authentication type not recognized: "+ request.getParameter("authType"));
					res.setFailure(QPResultCode.securityFailure,
							"User authentication type not recognized: "+ request.getParameter("authType"), 
							invoker.getProviderName());
				}
				else
				{	
					// AuthType has to be supported by the provider implementation
					if (! invoker.getSecurityProvider().isActualAuthTypeSupported()) {
						
						res = new QPOneBoxResults();
						this.log.error("User authentication type not supported: "+ request.getParameter("authType"));
						res.setFailure(QPResultCode.securityFailure,
								"User authentication type not supported: "+ request.getParameter("authType"),
								invoker.getProviderName());
					}
					else {
						
						try {
							// Invoke the provider
					        res = invoker.invokeProvider();
					        this.log.info("Servlet 3: " + res.toString());
						}
						catch ( QPProviderException pe ) {
							res = new QPOneBoxResults();
							this.log.error("Exception in invoking the provider", pe);
							res.setFailure(QPResultCode.lookupFailure,
									"Exception in invoking the provider.",
									invoker.getProviderName());
						}
					}
				}
			}
			// Exception in converting the request in callParameter
			catch (QPRequestValidateException rve) {
				res = new QPOneBoxResults();
				this.log.error("Error in validating input parameter.", rve);
				res.setFailure(QPResultCode.securityFailure,
						"QPRequestValidateException: " + rve.getLocalizedMessage(),
						"Provider not initialized.");
			}	
		
			// Defines response MIME type
			response.setContentType("text/xml");
			// Write response back as XML string
			PrintWriter out = response.getWriter();
			String resString = res.toXMLString();
			out.print(resString);
			this.log.info("Servlet 4: " + resString);
			out.close();
		}
		catch (Exception exc ) {
			this.log.error("Unhandled exception in servlet. ", exc);
		}
	}
}
