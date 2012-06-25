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

/**
 * Defines the available auth types: none, basic, LDAP and SSO.
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPAuthType {

	private static final String AUTHTYPE_NONE_NAME = "none";
	/**
	 * AuthType None
	 */
	public static final QPAuthType AUTHTYPE_NONE = createAuthType(AUTHTYPE_NONE_NAME);
	
	private static final String AUTHTYPE_BASIC_NAME = "basic";
	/**
	 * AuthType Basic
	 */
	public static final QPAuthType AUTHTYPE_BASIC = createAuthType(AUTHTYPE_BASIC_NAME);
	
	private static final String AUTHTYPE_LDAP_NAME = "ldap";
	/**
	 * AuthType LDAP
	 */
	public static final QPAuthType AUTHTYPE_LDAP = createAuthType(AUTHTYPE_LDAP_NAME);
	
	private static final String AUTHTYPE_SSO_NAME = "sso";
	/**
	 * AuthType SSO
	 */
	public static final QPAuthType AUTHTYPE_SSO = createAuthType(AUTHTYPE_SSO_NAME);
	
	
	private String name;
	
	private QPAuthType( String name ) {
		
		this.name = name;
	}

	private static QPAuthType createAuthType( String name ) {
		return new QPAuthType(name);
	}
	
	/**
	 * Gets the AuthType (none, basic, ldap, sso) for the name 
	 * or null if it is not one the names.
	 * 
	 * @param name
	 * @return QPAuthType or null
	 */
	public static QPAuthType getAuthType( String name ) {
		
		if ( name.equals(AUTHTYPE_NONE_NAME)) {
			return AUTHTYPE_NONE;
		}
		else if ( name.equals(AUTHTYPE_BASIC_NAME)) {
			return AUTHTYPE_BASIC;
		}
		else if ( name.equals(AUTHTYPE_LDAP_NAME)) {
			return AUTHTYPE_LDAP;
		}
		else if ( name.equals(AUTHTYPE_SSO_NAME)) {
			return AUTHTYPE_SSO;
		}
		return null;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {		
		return "AuthType: " + name;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isAuthTypeNone() {
		
		if (AUTHTYPE_NONE.getName().equals(name))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isAuthTypeBasic() {
		
		if (AUTHTYPE_BASIC.getName().equals(name))
		{
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isAuthTypeLDAP() {
	
		if (AUTHTYPE_LDAP.getName().equals(name))
		{
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isAuthTypeSSO() {
	
		if (AUTHTYPE_SSO.getName().equals(name))
		{
			return true;
		}
		return false;
	}
}
