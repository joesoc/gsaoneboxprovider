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

import java.util.Set;

/**
 * Implementation of the method "Search places" of the Jive REST API.
 * @see <a href="http://docs.jivesoftware.com/apireferences/core/reference/v2/index.html">Jive REST API</a>
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPJiveRESTSearchPlaces extends QPJiveRESTApi {
	
	/**
	 * Type: String	<br>
	 * The query string	<br>
	 * Default value: ""
	 */
	public String q;
	
	/**
	 * Type: Set	<br>
	 * Set of container types to include in the search.
	 * When not specified all container types available to open client will be searched.	
	 *  
	 */
	public Set<String> type;
	
	/**
	 * Type: int	<br>
	 * The maximum number of results to return. If there are fewer results available, 
	 * then fewer results than the limit will be returned.	<br>
	 * Default value: "25"
	 */
	public int limit;	
	
	/**
	 * Type: int	<br>
	 * The number of results which should be skipped in the returned array. For instance, 
	 * if the first 25 results have already been retrieved then results after the 25th result 
	 * can be retrieved by specifying an offset of 25. The minimum value for the offset is 0, 
	 * specifying anything less than 0 for the offset will result in an exception.	<br>
	 * Default value: "0"
	 */
	public int offset;	
	
	/**
	 * Type: String	<br>
	 * Older items in terms of modification time (day granularity) will be excluded from the search results.<br>	
	 * Default value: ""
	 */
	public String from;	
	
	/**
	 * Type: String	<br>
	 * Newer items in terms of modification time (day granularity) will be excluded from the search results.<br>	
	 * Default value: ""
	 */
	public String to;	
	
	/**
	 * Type: String	<br>
	 * The field to sort on ("relevance", "likes", "subject", "modificationDate"). 
	 * The default if sort is not provided is "relevance"	<br>
	 * Default value: "relevance"
	 */
	public String sort;	
	
	/**
	 * Type: String	<br>
	 * The desired sort order ("descending", "ascending"). The default order if none is provided is "descending".<br>	
	 * Default value: "descending"
	 */
	public String sortOrder;

	@Override
	public String getURLString() {
		
		return "/api/core/v2/search/places?";
	}

	@Override
	public String getURLParameterString() {
		
		return PAR_Q + "=" + this.q;
	}	
}
