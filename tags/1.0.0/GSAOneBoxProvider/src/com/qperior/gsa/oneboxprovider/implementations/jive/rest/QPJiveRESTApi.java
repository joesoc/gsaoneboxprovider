/*
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
package com.qperior.gsa.oneboxprovider.implementations.jive.rest;

/**
 * Abstract Class for the Jive REST API to implement some common tasks.
 * 
 * @author Ralf Ovelgoenne
 *
 */
public abstract class QPJiveRESTApi implements QPIJiveRESTApi {

	/**
	 * Delimiter for the URL string
	 */
	public static final String DELIMITER = "&";
	
	/**
	 * Parameter name of "q"
	 */
	public static final String PAR_Q = "q";
	
	/**
	 * Parameter name of "limit"
	 */
	public static final String PAR_LIMIT = "limit";
	
	/**
	 * Parameter name of "type"
	 */
	public static final String PAR_TYPE = "type";
}
