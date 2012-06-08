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
package com.qperior.gsa.oneboxprovider.implementations.jive;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.qperior.gsa.oneboxprovider.implementations.jive.rest.QPJiveJsonObject;
import com.qperior.gsa.oneboxprovider.implementations.jive.rest.QPJiveRESTSearchContent;
import com.qperior.gsa.oneboxprovider.provider.QPAbstractProvider;
import com.qperior.gsa.oneboxprovider.results.QPIOneBoxResults;
import com.qperior.gsa.oneboxprovider.util.QPLogger;
import com.qperior.gsa.oneboxprovider.util.exception.QPProviderException;

/**
 * Provider for the enterprise portal Jive using the OpenClient REST API, 
 * see <link>http://docs.jivesoftware.com/apireferences/core/reference/v2/index.html</link>. 
 * 
 * @author Ralf Ovelgoenne 
 *
 */
public class QPJiveProvider extends QPAbstractProvider {

	private Log log = QPLogger.getLogger(this.getClass());
	
	private static String RESULT_ERROR = "error";
	private static String RESULT_EMPTY = "empty";
	private static String RESULT_SECURITY = "security";
	private static String RESULT_TIMEOUT = "timeout";

	@Override
	public String toString() {

		return "Provider: " + this.getProviderName();
	}

	@Override
	public String getProviderName() {
		
		return "Jive Social Business Software Provider";
	}

	@Override
	public QPIOneBoxResults provideOneBoxResults() throws QPProviderException {

		QPIOneBoxResults result;
		
		QPJiveRESTSearchContent content = this.fillJiveRESTData();
		String json = this.callJiveRestApi(content);
		if ( json == null || json.equals("") ) {
			//error
			result = this.createErrorResult("JSON is empty.");
			this.log.error("JSON result null or empty-string.");
		}
		else if (json.equals(RESULT_ERROR)) {
			result = this.createErrorResult("Error in calling Jive.");
			this.log.info("JSON result: error.");
		}
		else if (json.equals(RESULT_EMPTY)) {
			result = this.createEmptyResult();
			this.log.info("JSON result: empty.");
		}
		else if (json.equals(RESULT_SECURITY)) {
			result = this.createSecurityResult("Security error in contacting Jive REST API.");
			this.log.info("JSON result: Security.");
		}
		else if (json.equals(RESULT_TIMEOUT)) {
			result = this.createTimeoutResult("Timeout in contacting Jive REST API.");
			this.log.error("JSON result: Timeout.");
			
		} else {
			QPJiveJsonObject jsonConverter = new QPJiveJsonObject();
			result = jsonConverter.convertJsonToResult(json);
			if ( result == null ) {
				//error in converting
				result = this.createErrorResult("Error in converting JSON.");
				this.log.error("Error in converting JSON.");
			}
			else {
				result.setProvider(this.getProviderName());
				this.log.info("JSON string successfully converted to QPJiveJsonObject.");
			}
		}
		
		return result;
	}
	
	private QPJiveRESTSearchContent fillJiveRESTData() {
		
		QPJiveRESTSearchContent content = new QPJiveRESTSearchContent();
		// only q and limit is required
		content.q = this.getCallParameter().getQuery();
		content.limit = QPJiveProperties.getResultLimit();
		
		return content;
	}
	
	/**
	 * Bad style to give back a String result with JSON or error, empty..
	 * This is done to fully encapsulate the Jive call (HttpGet) in this method.
	 * 
	 * @return JSON String: error, empty, security, timeout
	 */
	private String callJiveRestApi(QPJiveRESTSearchContent content) {
		
		if ( QPJiveProperties.isTestmode() ) {
			
			try {
				this.log.info("Calling Jive REST API: Testmode (no real call, reading out a JSON file).");
				URL url = ConfigurationUtils.locate(null, "data/json_test2.txt");
				File file = FileUtils.toFile(url);
				return FileUtils.readFileToString(file);
			} catch (IOException exc) {
				
				this.log.error("IOException: ", exc);
				return null;
			}			
			
		} else { 
			String uri = QPJiveProperties.getJiveURL() + content.getURLString() + content.getURLParameterString();
			this.log.info("Calling Jive REST API: URL '" + uri + "'.");
			String result = "";
			
			try {
				HttpGet httpget = new HttpGet(uri);
				DefaultHttpClient httpclient = new DefaultHttpClient();		
				httpget.addHeader("accept", "application/json");
				/* Basic authorization with user. */
				// TODO: get User from GSA not from Properties > SecurityProvider
				httpget.addHeader("Authorization", "Basic " + QPJiveProperties.getAccessToken());

				HttpResponse response = httpclient.execute(httpget);
				// use status
				// 200: OK
				// 401: security
				// 500: internal server error
				int status = response.getStatusLine().getStatusCode();
				
				if ( status == 200 ) {
					HttpEntity entity = response.getEntity();
					if (entity != null ) {
					    //long len = entity.getContentLength();
					    result = EntityUtils.toString(entity);
					    /*if (len != -1 && len < 2048) {
					        result = EntityUtils.toString(entity);
					    } else {
					        // Stream content out
					    	this.log.error("HttpEntity longer than 2048!");
					    }*/
					    
					    /* Response from Jive always starts with "throw 'allowIllegalResourceCall is false.';", 
					     * with this it is no valid JSON, a JSONObject text must begin with '{'
					     */
					    result = StringUtils.replace(result, QPJiveProperties.getJsonPrefix(), "");
					    
					    if ( result != null && ! result.equals("") ) {
					    	this.log.info("Calling Jive REST API: result string '" + result + "'.");
					    } else {
					    	result = RESULT_EMPTY;
					    	this.log.info("Calling Jive REST API: result string is empty.");
					    }
					}
					else {
						result = RESULT_EMPTY;
						this.log.error("Calling Jive REST API: HttpEntity is null.");
					} 
				} else if (status == 401) {
					result = RESULT_SECURITY;
				} else {
					result = RESULT_ERROR;
				}
			}
			catch ( HttpHostConnectException hexc ) {
				// Timeout
				result = RESULT_TIMEOUT;
				this.log.error("Timeout in contacting Jive REST API.");
			}
			catch (Exception exc) {
				
				result = RESULT_ERROR;
				this.log.error("Exception: ", exc);
			}
			
			return result;
		}
	}
}
