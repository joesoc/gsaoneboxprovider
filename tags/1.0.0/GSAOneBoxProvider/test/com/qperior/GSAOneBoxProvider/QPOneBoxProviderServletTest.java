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
package com.qperior.GSAOneBoxProvider;

import static org.junit.Assert.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * Tests for the servlet. Therefore testing through http client calling doGet and doPost.
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPOneBoxProviderServletTest {
	
	private static String URI_SERVLET = "http://localhost:8080/GSAOneBoxProvider/QPOneBoxProviderServlet?";
	private static String URI_AMP = "&";
	
	// Required
	private static String URI_APIMAJ = "apiMaj=1";
	private static String URI_APIMIN = "apiMin=1";
	private static String URI_AUTHTYPE = "authType=none";
	private static String URI_LANG = "lang=en";
	private static String URI_ONEBOXNAME = "oneboxName=test";
	private static String URI_QUERY = "query=test";
	
	// Optional
	private static String URI_DATETIME = "dateTime=2007-04-05T14:30";
	private static String URI_IPADDR = "ipAddr=10.10.220.234";
	private static String URI_PASSWORD = "password=pass";
	private static String URI_USERNAME = "userName=user";
	
	/**
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		
		String uri = "http://localhost:8080/GSAOneBoxProvider/QPOneBoxProviderServlet?apiMaj=1&apiMin=1&authType=none&lang=en&oneboxName=test&query=test";
		
		try {
			QPOneBoxProviderServletTest test = new QPOneBoxProviderServletTest();
			String actual = test.callHttpGet(uri);
		}
		catch (Exception exc) {
			fail("Exception in DoGet: " + exc.getLocalizedMessage());
		}
	}

	/**
	 * 
	 */
	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
				
		String uri = URI_SERVLET + 
				URI_APIMAJ + URI_AMP + 
				URI_APIMIN + URI_AMP +
				URI_AUTHTYPE + URI_AMP +
				URI_LANG + URI_AMP +
				URI_ONEBOXNAME + URI_AMP +
				URI_QUERY;
		
		try {
			String actual = this.callHttpGet(uri);
			assertEquals(null, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OneBoxResults xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"oneboxresults.xsd\"><resultCode>lookupFailure</resultCode><Diagnostics>Empty result.</Diagnostics><provider>Dummy Provider (none)</provider></OneBoxResults>", actual);
		}
		catch (Exception exc) {
			fail("Exception in DoGet: " + exc.getLocalizedMessage());
		}
		
		String uri2 = URI_SERVLET +  
				URI_APIMIN + URI_AMP +
				URI_AUTHTYPE + URI_AMP +
				URI_LANG + URI_AMP +
				URI_ONEBOXNAME + URI_AMP +
				URI_QUERY;
		
		try {
			String actual = this.callHttpGet(uri2);
			assertEquals(null, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OneBoxResults xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"oneboxresults.xsd\"><resultCode>securityFailure</resultCode><Diagnostics>QPRequestValidateException: Request parameter (Integer) 'apiMaj' not send or in wrong format.</Diagnostics><provider>Provider not initialized.</provider></OneBoxResults>", actual);
		}
		catch (Exception exc) {
			fail("Exception in DoGet: " + exc.getLocalizedMessage());
		}
		
		String uri3 = URI_SERVLET + 
				URI_APIMAJ + URI_AMP + 
				URI_AUTHTYPE + URI_AMP +
				URI_LANG + URI_AMP +
				URI_ONEBOXNAME + URI_AMP +
				URI_QUERY;
		
		try {
			String actual = this.callHttpGet(uri3);
			assertEquals(null, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OneBoxResults xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"oneboxresults.xsd\"><resultCode>securityFailure</resultCode><Diagnostics>QPRequestValidateException: Request parameter (Integer) 'apiMin' not send or in wrong format.</Diagnostics><provider>Provider not initialized.</provider></OneBoxResults>", actual);
		}
		catch (Exception exc) {
			fail("Exception in DoGet: " + exc.getLocalizedMessage());
		}
		
		String uri4 = URI_SERVLET + 
				URI_APIMAJ + URI_AMP + 
				URI_APIMIN + URI_AMP +
				URI_LANG + URI_AMP +
				URI_ONEBOXNAME + URI_AMP +
				URI_QUERY;
		
		try {
			String actual = this.callHttpGet(uri4);
			assertEquals(null, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OneBoxResults xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"oneboxresults.xsd\"><resultCode>securityFailure</resultCode><Diagnostics>QPRequestValidateException: Request parameter (QPAuthType) 'authType' not send or in wrong format.</Diagnostics><provider>Provider not initialized.</provider></OneBoxResults>", actual);
		}
		catch (Exception exc) {
			fail("Exception in DoGet: " + exc.getLocalizedMessage());
		}
		
		String uri5 = URI_SERVLET + 
				URI_APIMAJ + URI_AMP + 
				URI_APIMIN + URI_AMP +
				URI_AUTHTYPE + URI_AMP +
				URI_ONEBOXNAME + URI_AMP +
				URI_QUERY;
		
		try {
			String actual = this.callHttpGet(uri5);
			assertEquals(null, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OneBoxResults xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"oneboxresults.xsd\"><resultCode>securityFailure</resultCode><Diagnostics>QPRequestValidateException: Request parameter (String) 'lang' not send or in wrong format.</Diagnostics><provider>Provider not initialized.</provider></OneBoxResults>", actual);
		}
		catch (Exception exc) {
			fail("Exception in DoGet: " + exc.getLocalizedMessage());
		}
		
		String uri6 = URI_SERVLET + 
				URI_APIMAJ + URI_AMP + 
				URI_APIMIN + URI_AMP +
				URI_AUTHTYPE + URI_AMP +
				URI_LANG + URI_AMP +
				URI_QUERY;
		
		try {
			String actual = this.callHttpGet(uri6);
			assertEquals(null, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OneBoxResults xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"oneboxresults.xsd\"><resultCode>securityFailure</resultCode><Diagnostics>QPRequestValidateException: Request parameter (String) 'oneboxName' not send or in wrong format.</Diagnostics><provider>Provider not initialized.</provider></OneBoxResults>", actual);
		}
		catch (Exception exc) {
			fail("Exception in DoGet: " + exc.getLocalizedMessage());
		}
		
		String uri7 = URI_SERVLET + 
				URI_APIMAJ + URI_AMP + 
				URI_APIMIN + URI_AMP +
				URI_AUTHTYPE + URI_AMP +
				URI_LANG + URI_AMP +
				URI_ONEBOXNAME + URI_AMP;
		
		try {
			String actual = this.callHttpGet(uri7);
			assertEquals(null, "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OneBoxResults xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"oneboxresults.xsd\"><resultCode>securityFailure</resultCode><Diagnostics>QPRequestValidateException: Request parameter (String) 'query' not send or in wrong format.</Diagnostics><provider>Provider not initialized.</provider></OneBoxResults>", actual);
		}
		catch (Exception exc) {
			fail("Exception in DoGet: " + exc.getLocalizedMessage());
		}
	}

	/**
	 * 
	 */
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		
		String uri = URI_SERVLET + 
				URI_APIMAJ + URI_AMP + 
				URI_APIMIN + URI_AMP +
				URI_AUTHTYPE + URI_AMP +
				URI_LANG + URI_AMP +
				URI_ONEBOXNAME + URI_AMP +
				URI_QUERY;
		
		try {
			String actual = this.callHttpPost(uri);
			assertEquals(null, "Method 'doPost' is not supported!", actual);
		}
		catch (Exception exc) {
			fail("Exception in DoPost: " + exc.getLocalizedMessage());
		}
	
	}
	
	private String callHttpGet(String uri) throws Exception {
		
		HttpGet httpget = new HttpGet(uri);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(httpget);
		
		String actual = "";
		
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    long len = entity.getContentLength();
		    if (len != -1 && len < 2048) {
		        actual = EntityUtils.toString(entity);
		    } else {
		        // Stream content out
		    }
		}
		
		return actual;		
	}
	
	private String callHttpPost(String uri) throws Exception {
		
		HttpPost httppost = new HttpPost(uri);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(httppost);
		
		String actual = "";
		
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    long len = entity.getContentLength();
		    if (len != -1 && len < 2048) {
		        actual = EntityUtils.toString(entity);
		    } else {
		        // Stream content out
		    }
		}
		
		return actual;		
	}
}
