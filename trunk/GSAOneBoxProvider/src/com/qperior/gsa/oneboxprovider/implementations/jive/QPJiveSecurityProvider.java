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

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.qperior.gsa.oneboxprovider.security.QPAbstractSecurityProvider;
import com.qperior.gsa.oneboxprovider.security.QPAuthType;

/**
 * Security provider for the enterprise portal Jive using the OpenClient REST API, 
 * see <link>http://docs.jivesoftware.com/apireferences/core/reference/v2/index.html</link>. 
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPJiveSecurityProvider extends QPAbstractSecurityProvider {

	private List<QPAuthType> supportedAuthTypes = Arrays.asList(
			QPAuthType.AUTHTYPE_NONE, QPAuthType.AUTHTYPE_BASIC);

	@Override
	public List<QPAuthType> getSupportedAuthTypes() {

		return supportedAuthTypes;
	}

	/**
	 * Access Token, base64 encoded username:password.
	 * 
	 * @return Basic access token
	 */
	public String getBasicAccessToken() {
		
		String token = this.getUserName() + ":" + this.getPassword();
		
		token = this.base64Encode(token);
		
		return token;
	}
	
	private String base64Encode(String stringToEncode) {  
		
		byte [] stringToEncodeBytes = stringToEncode.getBytes();  
		return Base64.encodeBase64String(stringToEncodeBytes);  
	} 
}
