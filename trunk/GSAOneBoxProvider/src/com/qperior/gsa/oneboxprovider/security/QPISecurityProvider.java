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
package com.qperior.gsa.oneboxprovider.security;

import java.util.List;

import javax.servlet.http.Cookie;

/**
 * Provides security features for non, basic, ldap and sso authentification.
 * Has to be implemented for every concrete provider 
 * (through abstract class {@link QPAbstractSecurityProvider}).
 * 
 * @author Ralf Ovelgoenne
 *
 */
public interface QPISecurityProvider {

	/**
	 * TODO: Define security interface
	 */
	
	/**
	 * Gets the associated, actual AuthType of the security provider for this call
	 * 
	 * @return QPAuthType
	 */
	public QPAuthType getActualAuthType();
	
	/**
	 * Gets a list of supported AuthType by the provider (none, basic, ldap, sso )
	 * 
	 * @return List<QPAuthType>
	 */
	public List<QPAuthType> getSupportedAuthTypes();
	
	/**
	 * Checks that the AuthType is sopported by the provider
	 * 
	 * @param name of AuthType
	 * @return boolean
	 */
	public boolean isGivenAuthTypeSupported( String name );
	
	/**
	 * Checks that the actual AuthType (SecurityProvider was initialized with it)
	 * is supported by the provider
	 * 
	 * @return boolean
	 */
	public boolean isActualAuthTypeSupported( );
	
	/**
	 * Is AuthType "none" supported?
	 * 
	 * @return boolean
	 */
	public boolean isNoneSupported();
	
	/**
	 * Is AuthType "basic" supported?
	 * 
	 * @return boolean
	 */
	public boolean isBasicSupported();
	
	/**
	 * Is AuthType "ldap" supported?
	 * 
	 * @return boolean
	 */
	public boolean isLDAPSupported();
	
	/**
	 * Is AuthType "sso" supported?
	 * 
	 * @return boolean
	 */	
	public boolean isSSOSupported();
	
	/**
	 * Gets the user name for basic AuthN
	 * 
	 * @return user name
	 */
	public String getUserName();

	/**
	 * Gets the password name for basic AuthN
	 * 
	 * @return password
	 */
	public String getPassword();
	
	/**
	 * Gets the user name for LDAP AuthN
	 * 
	 * @return LDAP distinguished name
	 */
	public String getDistinguishedName();

	/**
	 * Gets the cookie for SSO AuthN
	 * 
	 * @return Cookie
	 */
	public Cookie getSsoCookie();

}
