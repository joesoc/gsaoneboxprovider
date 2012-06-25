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
package com.qperior.gsa.oneboxprovider.results;

/**
 * Failure return codes that a OneBox provider can report.
 * <p>
 * The failure return codes recognized by OneBox clients include
 * {@link #lookupFailure}, {@link #securityFailure}, and
 * {@link #timeout}.
 * <p>
 * In the case of failure during OneBox provider processing, a
 * failure code is set using the
 * {@link QPOneBoxResults#setFailure OneBoxResults.setFailure()}
 * method.
 * 
 * 
 * @see QPOneBoxResults
 */
public final class QPResultCode {
	
	/**
	 * Result code: success - everything worked fine
	 */
	public static final QPResultCode success = new QPResultCode("success");
	/**
	 * Result code: lookupFailure - no results were found
	 */
	public static final QPResultCode lookupFailure = new QPResultCode("lookupFailure");
	/**
	 * Result code: securityFailure - AuthN failed
	 */
    public static final QPResultCode securityFailure = new QPResultCode("securityFailure");
    /**
     * Result code: timeout - timeout in contacting the provider
     */
    public static final QPResultCode timeout = new QPResultCode("timeout");
	
    private String name;
    
    private QPResultCode(String name) { 
    	this.name = name; 
    }
	
    /**
     * Name of the result code.
     * 
     * @return String
     */
    public String getName() {
    	return this.name; 
    }
    
    /**
     * Checks whether is is a success, otherwise it is 
     * lookupFailure, a securityFailure or a timeout.
     * 
     * @return boolean
     */
    public boolean isSuccess() {
    	return this.name.equals(success.getName());
    }
    
    @Override
	public String toString() {

		return "ResultCode: " + this.getName();
	}
}
