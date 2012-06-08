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
package com.qperior.gsa.oneboxprovider.provider;

import org.apache.commons.logging.Log;

import com.qperior.gsa.oneboxprovider.QPCallParameter;
import com.qperior.gsa.oneboxprovider.results.QPIOneBoxResults;
import com.qperior.gsa.oneboxprovider.results.QPOneBoxResults;
import com.qperior.gsa.oneboxprovider.results.QPResultCode;
import com.qperior.gsa.oneboxprovider.security.QPAbstractSecurityProvider;
import com.qperior.gsa.oneboxprovider.security.QPISecurityProvider;
import com.qperior.gsa.oneboxprovider.util.QPLogger;
import com.qperior.gsa.oneboxprovider.util.exception.QPProviderInvokeException;

/**
 * Abstract implementation of {@link QPIProvider} to implement some common tasks for all provider.
 * 
 * @author Ralf Ovelgoenne
 *
 */
public abstract class QPAbstractProvider implements QPIProvider {
	
	private QPCallParameter callParameter;
	private QPISecurityProvider securityProvider;
	private QPOneBoxResults results;
	
	private Log log = QPLogger.getLogger(this.getClass());
	
	/**
	 * Create the provider for the given type through reflection
	 * and inits it with the callParameter. Automatically creates and inits a
	 * security provider for it.
	 * 
	 * @param providerName full qualified class name to load it through reflection
	 * @param securityProviderName full qualified class name to load it through reflection
	 * @param callParameter
	 * @return QPIProvider
	 * @throws QPProviderInvokeException 
	 */
	protected static QPIProvider createAndInitProvider(String providerName, String securityProviderName, QPCallParameter callParameter) throws QPProviderInvokeException {
		
		try {
			QPAbstractProvider provider = (QPAbstractProvider) Class.forName(providerName).newInstance();
			provider.init(callParameter);
			
			QPAbstractSecurityProvider securityProvider = (QPAbstractSecurityProvider) QPAbstractSecurityProvider.createAndInitSecurityProvider(securityProviderName, callParameter);
			provider.securityProvider = securityProvider;
			
			return provider;
			
		} catch ( Exception exc ) {
			throw new QPProviderInvokeException("Exception in creating the provider", exc);
		}
	}
	
	/**
	 * Init provider with the call parameter and the type.
	 * Fills in the callParameter, the securityProvider and inits the results.
	 * 
	 * @param callParameter GSA call parameter
	 */
	private void init(QPCallParameter callParameter) {
		
		this.callParameter = callParameter;
		this.results = new QPOneBoxResults();
	}
	
	@Override
	public QPCallParameter getCallParameter() {
		return callParameter;
	}

	@Override
	public QPOneBoxResults getResults() {
		return results;
	}
	
	@Override
	public QPISecurityProvider getSecurityProvider() {
		return securityProvider;
	}
	
	/**
	 * Creates a empty result with if the provider's data lookup completes, but returns zero results.
	 * Produces the data:
	 * <pre>
	 * {@code
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * <OneBoxResults xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	 * <resultCode>"lookupFailure"</resultCode>
	 * <Diagnostics>Empty result</Diagnostics>
	 * <provider>Provider name</provider>
	 * </OneBoxResults>
	 * </code>
	 * }
	 * </pre>
	 * 
	 * @return QPIOneBoxResults
	 */
	public QPIOneBoxResults createEmptyResult() {
		
		QPOneBoxResults res = new QPOneBoxResults();
		this.log.info("Creating empty result.");
		res.setFailure(QPResultCode.lookupFailure,
				"Empty result.", 
				this.getProviderName());
		
		return res;
	}
	
	/**
	 * Creates a error result (securityFailure).
	 * 
	 * @param message message to set in result
	 * @return QPIOneBoxResults
	 */
	public QPIOneBoxResults createErrorResult( String message) {
		
		QPOneBoxResults res = new QPOneBoxResults();
		this.log.info("Creating error result.");
		res.setFailure(QPResultCode.securityFailure,
				message, 
				this.getProviderName());
		
		return res;
	}
	
	/**
	 * Creates a result because of a security failure.
	 * 
	 * @param message message to set in result
	 * @return QPIOneBoxResults
	 */
	public QPIOneBoxResults createSecurityResult( String message) {
		
		QPOneBoxResults res = new QPOneBoxResults();
		this.log.info("Creating security result.");
		res.setFailure(QPResultCode.securityFailure,
				message, 
				this.getProviderName());
		
		return res;
	}
	
	/**
	 * Creates a result if the provider gets a timeout (service is not available).
	 * 
	 * @param message to set in result
	 * @return QPIOneBoxResults
	 */
	public QPIOneBoxResults createTimeoutResult( String message) {
		
		QPOneBoxResults res = new QPOneBoxResults();
		this.log.info("Creating timeout result.");
		res.setFailure(QPResultCode.timeout,
				message, 
				this.getProviderName());
		
		return res;
	}
}
