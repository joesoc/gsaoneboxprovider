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
 * Interface for the implemented Jive REST API
 * 
 * @author Ralf Ovelgoenne
 *
 */
public interface QPIJiveRESTApi {

	/**
	 * The URL string of the REST API endpoint
	 * 
	 * @return String
	 */
	public String getURLString();
	
	/**
	 * The URL parameter of the REST API endpoint
	 * 
	 * @return String
	 */
	public String getURLParameterString();
}
