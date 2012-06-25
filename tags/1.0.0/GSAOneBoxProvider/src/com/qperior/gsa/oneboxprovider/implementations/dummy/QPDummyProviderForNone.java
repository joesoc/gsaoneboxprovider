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
package com.qperior.gsa.oneboxprovider.implementations.dummy;

import com.qperior.gsa.oneboxprovider.provider.QPAbstractProvider;
import com.qperior.gsa.oneboxprovider.results.QPIOneBoxResults;
import com.qperior.gsa.oneboxprovider.util.exception.QPProviderException;

/**
 * Just a dummy implementation for the {@link QPAbstractProvider}. 
 * It just gives back an empty result (see {@link QPAbstractProvider#createEmptyResult()}).
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPDummyProviderForNone extends QPAbstractProvider {
	
	//private Log log = QPLogger.getLogger(this.getClass());
	
	@Override
	public QPIOneBoxResults provideOneBoxResults() throws QPProviderException {
		
		return this.createEmptyResult();
	}

	@Override
	public String getProviderName() {
		
		return "Dummy Provider (none)";
	}
	
	@Override
	public String toString() {
		return "Provider: " + this.getProviderName();
	}
}
