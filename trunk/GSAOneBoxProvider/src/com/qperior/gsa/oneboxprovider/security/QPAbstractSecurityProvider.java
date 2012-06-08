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

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;

import com.qperior.gsa.oneboxprovider.QPCallParameter;
import com.qperior.gsa.oneboxprovider.util.exception.QPProviderInvokeException;

/**
 * Abstract implementation of {@link QPISecurityProvider} to implement
 * some common tasks for all security provider.
 * <p>
 * Knows the actual auth type of the call and the supported auth types of the provider.
 * In addition to that it knows the the username and password for basic, the distinguished name 
 * for LDAP and the cookie for SSO authentification.
 * 
 * @author Ralf Ovelgoenne
 *
 */
public abstract class QPAbstractSecurityProvider implements QPISecurityProvider {

	protected QPAuthType actualAuthType;
	
	private String userName;	
	private String password;	
	private String distinguishedName;
	private Cookie ssoCookie;
	
	/**
	 * Create the security provider for the given type through reflection
	 * and inits it with the callParameter.
	 * 
	 * @param securityProviderName full qualified class name to load it through reflection
	 * @param callParameter
	 * @return QPISecurityProvider
	 * @throws QPProviderInvokeException 
	 */
	public static QPISecurityProvider createAndInitSecurityProvider(String securityProviderName, QPCallParameter callParameter) throws QPProviderInvokeException {
		
		try {
			QPAbstractSecurityProvider securityProvider = (QPAbstractSecurityProvider) Class.forName(securityProviderName).newInstance();
			securityProvider.init(callParameter);
			
			return securityProvider;
			
		} catch ( Exception exc ) {
			throw new QPProviderInvokeException("Exception in creating the security provider", exc);
		}
	}
	
	/**
	 * Init the security provider with the call parameter
	 * 
	 * @param callParameter
	 */
	private void init(QPCallParameter callParameter) {
		
		this.actualAuthType = callParameter.getAuthType();
		
		if (this.getActualAuthType().isAuthTypeNone())
		{
			// nothing to do
		}
		else if (this.getActualAuthType().isAuthTypeBasic())
		{
			this.setUserName(callParameter.getUserName());
			this.setPassword(callParameter.getPassword());
		}
		else if (this.getActualAuthType().isAuthTypeLDAP())
		{
			// LDAP distinguished name (DN)
			this.setDistinguishedName(callParameter.getUserName());
		}
		else if (this.getActualAuthType().isAuthTypeSSO())
		{
			this.setSsoCookie(callParameter.getSsoCookie());
		}
	}
	
	@Override
	public boolean isGivenAuthTypeSupported(String name) {
		
		QPAuthType submittedAuthType = QPAuthType.getAuthType(name);
		
		List<QPAuthType> liste = this.getSupportedAuthTypes();
		
		Iterator<QPAuthType> it = liste.iterator();
		while (it.hasNext()) {
			if ( it.next().getClass().equals(submittedAuthType.getName())) {
				return true;
			}
		}
		return false;	
	}
	

	@Override
	public boolean isActualAuthTypeSupported() {
		
		List<QPAuthType> liste = this.getSupportedAuthTypes();
		
		Iterator<QPAuthType> it = liste.iterator();
		while (it.hasNext()) {
			if ( it.next().getName().equals(this.actualAuthType.getName())) {
				return true;
			}
		}
		return false;	
	}

	@Override
	public QPAuthType getActualAuthType() {
		
		return this.actualAuthType;
	}

	@Override
	public String getUserName() {
		
		return this.userName;
	}

	private void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getDistinguishedName() {
		return distinguishedName;
	}

	private void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

	@Override
	public Cookie getSsoCookie() {
		return ssoCookie;
	}

	private void setSsoCookie(Cookie ssoCookie) {
		this.ssoCookie = ssoCookie;
	}
	
	@Override
	public boolean isNoneSupported() {

		List<QPAuthType> liste = this.getSupportedAuthTypes();
		
		Iterator<QPAuthType> it = liste.iterator();
		while (it.hasNext()) {
			if ( it.next().isAuthTypeNone()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isBasicSupported() {

		List<QPAuthType> liste = this.getSupportedAuthTypes();
		
		Iterator<QPAuthType> it = liste.iterator();
		while (it.hasNext()) {
			if ( it.next().isAuthTypeBasic()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isLDAPSupported() {
		List<QPAuthType> liste = this.getSupportedAuthTypes();
		
		Iterator<QPAuthType> it = liste.iterator();
		while (it.hasNext()) {
			if ( it.next().isAuthTypeLDAP()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSSOSupported() {
		List<QPAuthType> liste = this.getSupportedAuthTypes();
		
		Iterator<QPAuthType> it = liste.iterator();
		while (it.hasNext()) {
			if ( it.next().isAuthTypeSSO()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {

		return "SecurityProvider: " + this.getActualAuthType().getName();
	}
}
