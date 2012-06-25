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
import com.qperior.gsa.oneboxprovider.security.QPISecurityProvider;
import com.qperior.gsa.oneboxprovider.util.QPLogger;
import com.qperior.gsa.oneboxprovider.util.exception.QPProviderException;
import com.qperior.gsa.oneboxprovider.util.exception.QPProviderInvokeException;

/**
 * Invokes the provider.
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPProviderInvoker {

	private QPIProvider provider;
	
	private Log log = QPLogger.getLogger(this.getClass());
	
	private QPProviderInvoker( String providerName, String securityProviderName, QPCallParameter callParameter ) throws QPProviderInvokeException {
		
		this.log.info("Creating and init provider '" + providerName + "'.");
		this.provider = QPAbstractProvider.createAndInitProvider(providerName, securityProviderName, callParameter);
	}

	/**
	 * Creates the provider invoker with the choosen provider type and initializes it.
	 * 
	 * @param providerName full qualified class name to load it through reflection
	 * @param securityProviderName full qualified class name to load it through reflection
	 * @param callParameter parameter of the call
	 * @return QPProviderInvoker
	 * @throws QPProviderInvokeException 
	 */
	public static QPProviderInvoker createProviderInvoker( String providerName, String securityProviderName, QPCallParameter callParameter ) throws QPProviderInvokeException {
		return new QPProviderInvoker(providerName, securityProviderName, callParameter);
	}

	@Override
	public String toString() {
		return this.provider.toString();
	}

	/**
	 * Invokes the choosen provider with the call parameter.
	 * 
	 * @return QPIOneBoxResults
	 * @throws QPProviderException
	 */
	public QPIOneBoxResults invokeProvider( ) throws QPProviderException {
		
		this.log.info("Invoke provider '" + this.getProviderName() + "'.");
		return this.provider.provideOneBoxResults();
	}
	
	/**
	 * Convenience method to get the name of the associated provider. 
	 * 
	 * @return String
	 */
	public String getProviderName() {
		
		return this.provider.getProviderName();
	}
	
	/**
	 * Gets the call parameter send to the provider.
	 * 
	 * @return QPCallParameter
	 */
	public QPCallParameter getCallParameter() {
		
		return this.provider.getCallParameter();
	}
	
	/**
	 * Gets the security provider fitting to the provider.
	 * 
	 * @return QPISecurityProvider
	 */
	public QPISecurityProvider getSecurityProvider() {
		
		return this.provider.getSecurityProvider();
	}
}
