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
package com.qperior.gsa.oneboxprovider.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Properties class for the application
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPProperties {
	
	private static Configuration config;
	
	private static final String NAME_PROPERTIES_FILE = "GSAOneBoxProvider.properties";
	
	private static final String KEY_PROVIDER = "Provider";
	
	private static final String KEY_SECURITY_PROVIDER = "SecurityProvider";
	
	private static final String KEY_VERSION = "Version";
	
	private QPProperties() {
		
	}

	static {
		try {
			config = new PropertiesConfiguration(NAME_PROPERTIES_FILE);
		} catch (ConfigurationException exc) {
			
		}
	}
	
	/**
	 * The currently used provider (full qualified name to load it through reflection).
	 * 
	 * @return String
	 */
	public static String getProvider() {
		return config.getString(KEY_PROVIDER);
	}
	
	/**
	 * The currently used security provider (full qualified name to load it through reflection).
	 * 
	 * @return String
	 */
	public static String getSecurityProvider() {
		return config.getString(KEY_SECURITY_PROVIDER);
	}
	
	/**
	 * The current version of the application.
	 * 
	 * @return String
	 */
	public static String getVersion() {
		return config.getString(KEY_VERSION);
	}
}
