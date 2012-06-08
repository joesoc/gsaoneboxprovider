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

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;

/**
 * Util-Class for Logging
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPLogger {

	/**
	 * Gets a Log4J logger
	 * 
	 * @param clazz
	 * @return Log4JLogger
	 */
	public static Log4JLogger getLogger(@SuppressWarnings("rawtypes") Class clazz) {
		
		return (Log4JLogger) LogFactory.getLog(clazz);
	}
}
