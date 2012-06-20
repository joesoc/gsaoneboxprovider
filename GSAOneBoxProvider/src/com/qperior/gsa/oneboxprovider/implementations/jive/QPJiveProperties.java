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
package com.qperior.gsa.oneboxprovider.implementations.jive;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Properties class for the Jive provider
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPJiveProperties {
	
	private static Configuration config;
	
	private static final String NAME_PROPERTIES_FILE = "JiveProvider.properties";
	
	private static final String KEY_JIVEURL = "JiveURL";
	
	private static final String KEY_TESTMODE = "Testmode";
	
	private static final String KEY_JSON_PREFIX = "JsonPrefix";
	
	private static final String KEY_RESULT_LIMIT = "ResultLimit";
	
	private static final String KEY_RESULT_TYPES = "Types";
	
	private QPJiveProperties() {
		
	}

	static {
		try {
			config = new PropertiesConfiguration(NAME_PROPERTIES_FILE);
		} catch (ConfigurationException exc) {
			
		}
	}
	
	/**
	 * The URL to Jive REST API.
	 * 
	 * @return String
	 */
	public static String getJiveURL() {
		return config.getString(KEY_JIVEURL);
	}
	
	/**
	 * Testmode true/false.
	 * 
	 * @return boolean
	 */
	public static boolean isTestmode() {
		return config.getBoolean(KEY_TESTMODE);
	}
	
	/**
	 * Jive JSON prefix to delete from response.
	 * 
	 * @return String
	 */
	public static String getJsonPrefix() {
		return config.getString(KEY_JSON_PREFIX);
	}
	
	/**
	 * The maximum number of results to return.
	 * 
	 * @return int
	 */
	public static int getResultLimit() {
		return config.getInt(KEY_RESULT_LIMIT);
	}
	
	/**
	 * The types of content to search
	 * 
	 * @return Set<String>
	 */
	public static Set<String> getTypeSet() {
		List<String> list = config.getList(KEY_RESULT_TYPES);
		return new HashSet<String>(list);		
	}
}
