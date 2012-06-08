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

import com.qperior.gsa.oneboxprovider.QPCallParameter;
import com.qperior.gsa.oneboxprovider.results.QPIOneBoxResults;
import com.qperior.gsa.oneboxprovider.results.QPOneBoxResults;
import com.qperior.gsa.oneboxprovider.security.QPISecurityProvider;
import com.qperior.gsa.oneboxprovider.util.exception.QPProviderException;

/**
 * Provides the functionality of the provider.
 * Has to be implemented for every concrete provider 
 * (through abstract class {@link QPAbstractProvider}).
 * 
 * @author Ralf Ovelgoenne
 *
 */
public interface QPIProvider {
	
	/**
	 * Method to handle OneBox requests with the user authentication required.
	 * It has to give back a valid OneBoxResult, null is not allowed!
	 * 
	 * @return IOneBoxResults
	 * @throws QPProviderException
	 */
	public QPIOneBoxResults provideOneBoxResults() throws QPProviderException;

	/**
	 * The provider name shown in the results.
	 * 
	 * @return String
	 */
	public String getProviderName();
	
	/**
	 * Gets the call parameter send to the provider.
	 * 
	 * @return QPCallParameter
	 */
	public QPCallParameter getCallParameter();

	/**
	 * Gets the result if filled.
	 * 
	 * @return QPOneBoxResults or null
	 */
	public QPOneBoxResults getResults();
	
	/**
	 * Gets the security provider fitting to the provider.
	 * If a special feature is implemented in a concrete SecurityProvider 
	 * the interface has to be casted to the concrete one.
	 * 
	 * @return QPISecurityProvider
	 */
	public QPISecurityProvider getSecurityProvider();
}
