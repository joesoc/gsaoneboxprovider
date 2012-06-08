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
package com.qperior.gsa.oneboxprovider.util.exception;

/**
 * Exception for all exceptions related to read in and validate the request parameter.
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPRequestValidateException extends Exception {

	private static final long serialVersionUID = -4553806919504218316L;

	/**
	 * 
	 * @param message
	 */
	public QPRequestValidateException(String message) {
		super(message);
	}
	
}
