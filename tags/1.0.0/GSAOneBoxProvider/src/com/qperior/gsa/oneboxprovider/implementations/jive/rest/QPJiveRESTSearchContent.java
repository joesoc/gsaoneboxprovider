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

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of the method "Search content" of the Jive REST API.
 * @see <a href="http://docs.jivesoftware.com/apireferences/core/reference/v2/index.html">Jive REST API</a>
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPJiveRESTSearchContent extends QPJiveRESTApi {

	/**
	 * Type: String	<br>
	 * The query string	<br>
	 * Default value: ""
	 */
	public String q;

	/**
	 * Type: Set	<br>
	 * Set of content types to include in the search. 
	 * When not specified all content types available to open client will be searched.
	 * 
	 */
	public Set<String> type;
	
	/**
	 * Helper method to convert the Set to URL Parameter String
	 * Example: type=discussion&type=update&
	 * 
	 * @return String, might be empty
	 */
	public String getTypeString() {
		
		String ret = "";
		
		if ( ! this.type.isEmpty() ) {
			Iterator<String> it = this.type.iterator();
			StringBuffer strbuf = new StringBuffer();
			while (it.hasNext() ) {
				strbuf.append( PAR_TYPE + "=" + it.next() + DELIMITER );
			}
			ret = strbuf.toString();
		}		
		return ret;
	}
	
		 
	/**
	 * Type: String	<br>
	 * Older items in terms of modification time (day granularity) will be excluded from the search results.<br>	
	 * Default value: ""
	 */
	public String from;	
	
	/**
	 * Type: String	<br>
	 * Newer items in terms of modification time (day granularity) will be excluded from the search results.	<br>
	 * Default value: ""
	 */
	public String to;	
	
	/**
	 * Type: int	<br>
	 * ID of the container type to restrict the search to. 
	 * If this is specified, then containerID must also be specified, and containerName must not	 
	 * 
	 */
	public int containerTypeID;	
	
	/**
	 * Type: Long	<br>
	 * ID of the container to restrict the search to. 
	 * If this is specified, then containerTypeID must also be specified, and containerName must not	 
	 * 
	 */
	public long containerID;	
	
	/**
	 * Type: String	<br>
	 * Name of the container to search to restrict the search to. 
	 * If this is specified, then neither containerID nor containerTypeID can be specified	 
	 * 
	 */
	public String container;
	
	/**
	 * Type: String	<br>
	 * Username of the content author to restrict the search to. 
	 * If this is specified, then userID cannot be specified.	
	 *  
	 */
	public String user;	
	
	/**
	 * Type: Long	<br>
	 * User ID of the content author to restrict the search to. 
	 * If this is specified, then username cannot be specified.	 
	 * 
	 */
	public long userID;	
	
	/**
	 * Type: int	<br>
	 * The maximum number of results to return. 
	 * If there are fewer results available, then fewer results than the limit will be returned.<br>	
	 * Default value: "25"
	 */
	public int limit;	
	
	/**
	 * Type: int	<br>
	 * The number of results which should be skipped in the returned array. 
	 * For instance, if the first 25 results have already been retrieved then results after the 25th 
	 * result can be retrieved by specifying an offset of 25. The minimum value for the offset is 0, 
	 * specifying anything less than 0 for the offset will result in an exception.	<br>
	 * Default value: "0"
	 */
	public int offset;	
	
	/**
	 * Type: String	<br>
	 * The field to sort on ("relevance", "likes", "subject", "modificationDate"). 
	 * The default if sort is not provided is "relevance".	<br>
	 * Default value: "relevance"
	 */
	public String sort;	
	
	/**
	 * Type: String<br>	
	 * The desired sort order ("descending", "ascending"). 
	 * The default order if none is provided is "descending". Please note that ascending and descending 
	 * do not necessary produce exact opposites of one another. This is because there are often 
	 * additional criteria that is used to sort items that have matching primary criteria.	<br>
	 * Default value: "descending"
	 */
	public String sortOrder;

	@Override
	public String getURLString() {
		
		return "search/content?";
	}

	@Override
	public String getURLParameterString() {
		
		return PAR_Q + "=" + this.q + DELIMITER + this.getTypeString() + PAR_LIMIT + "=" + this.limit;
	}	
}
