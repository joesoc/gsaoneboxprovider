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
package com.qperior.gsa.oneboxprovider;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.qperior.gsa.oneboxprovider.security.QPAuthType;
import com.qperior.gsa.oneboxprovider.util.exception.QPRequestValidateException;

/**
 * A GET request from a search appliance is sent to the external provider as specified in the 
 * OneBox module definition.
 * 
 * Certain parameter can be suppressed if the appropriate attributes are set on the <onebox> element. 
 * Access control settings are only passed if the OneBox module is configured to include user-level 
 * access control. If the provider is configured to use basic authentication between the search appliance 
 * and the provider (<GSA_username> and <GSA_password> elements are defined) then the GET request will 
 * include these parameter in the HTTP header.
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPCallParameter {
	
	private int apiMaj; 			//required
	private int apiMin; 			//required
	private QPAuthType authType; 	//required
	private String dateTime; 	
	private String ipAddr; 	  
	private String lang; 			//required
	private String oneboxName; 		//required
	private String password; 
	private String[] matchGroups;	//required
	private String query; 			//required
	private String userName;
	private Cookie ssoCookie;

	/**
	 * Put the parameter from the request in the instance.
	 * 
	 * @param request
	 * @throws QPRequestValidateException 
	 */
	public QPCallParameter(HttpServletRequest request) throws QPRequestValidateException {
		 
		this.apiMaj = this.validateInteger("apiMaj", request); 
		this.apiMin = this.validateInteger("apiMin", request);  
		
		this.authType = this.validateAuthType("authType", request);
		this.dateTime = request.getParameter("dateTime");
		this.ipAddr = request.getParameter("ipAddr");
		this.lang = this.validateString("lang", request);
		this.oneboxName = this.validateString("oneboxName", request);
		
		this.password = request.getParameter("password");
		this.query = this.validateString("query", request);
		this.userName = request.getParameter("userName");
		
		int count = 0;
		ArrayList<String> _matchGroups = new ArrayList<String>();
		String match = request.getParameter("p"+(count++));
		while (match != null) {
			_matchGroups.add(match);
			match = request.getParameter("p"+(count++));
		}
		this.matchGroups = (String[])_matchGroups.toArray(new String[_matchGroups.size()]);
		
		Cookie ssoCookie = null;
		String cookieName = request.getParameter("userName");
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (int i=0; i<cookies.length; i++ )
			{
				Cookie c = cookies[i];
				if (c.getName().equals(cookieName))
				{
					ssoCookie = c;
					break;
				}
			}
		}
		this.ssoCookie = ssoCookie;
	}
	
	private QPAuthType validateAuthType(String requestParamName, HttpServletRequest request) throws QPRequestValidateException {
		
		if ( requestParamName != null && ! requestParamName.equals("")) {
			String requestParam = request.getParameter(requestParamName);
			if ( requestParam != null && ! requestParam.equals("")) {
				QPAuthType type = QPAuthType.getAuthType(requestParam); 
				if ( type != null) {
					return type;
				}
			}
		}
		
		throw new QPRequestValidateException("Request parameter (QPAuthType) '" + requestParamName + "' not send or in wrong format.");
	}
	
	private String validateString(String requestParamName, HttpServletRequest request) throws QPRequestValidateException {
		
		if ( requestParamName != null && ! requestParamName.equals("")) {
			String requestParam = request.getParameter(requestParamName);
			if ( requestParam != null && ! requestParam.equals("")) {
				String value = new String(requestParam); 
				if ( value != null && ! value.equals("")) {
					return value;
				}
			}
		}
		
		throw new QPRequestValidateException("Request parameter (String) '" + requestParamName + "' not send or in wrong format.");
	}
	
	private int validateInteger(String requestParamName, HttpServletRequest request) throws QPRequestValidateException {
		
		if ( requestParamName != null && ! requestParamName.equals("")) {
			String requestParam = request.getParameter(requestParamName);
			if ( requestParam != null && ! requestParam.equals("")) {
				return new Integer(requestParam); 
			}
		}
		
		throw new QPRequestValidateException("Request parameter (Integer) '" + requestParamName + "' not send or in wrong format.");
	}
	
	/**
	 * required<p>
	 * API major version number. Changes in this value may break compatibility.
	 * 
	 * @return Integer (1, 2, 3...)
	 */
	public int getApiMaj() {
		return apiMaj;
	}

	/**
	 * required <p>	
	 * API minor version number. Changes in this value do not break compatibility.
	 * 
	 * @return Integer (0, 1, 2, 3...)
	 */
	public int getApiMin() {
		return apiMin;
	}

	/**
	 * required <p>	
	 * The authentication mechanism used to provide user-specific information. 	
	 * 
	 * @return One of the following values: none, basic, ldap, or sso
	 */
	public QPAuthType getAuthType() {
		return authType;
	}

	/**
	 * optional <p>
	 * Date and time of the query on the calling search appliance. 	
	 * 
	 * @return Text, UTC format
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * optional <p>
	 * The IP address of the originating user. 	
	 * 
	 * @return IP address
	 */
	public String getIpAddr() {
		return ipAddr;
	}

	/**
	 * required <p>
	 * The language of the user's browser where the query originated. 	
	 * 
	 * @return Text, two-character language code, such as EN, JP, or DE
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * required <p>
	 * Name of the OneBox module. Must match the OneBox Module definition specified in the Admin Console. 	
	 * 
	 * @return Text, no spaces
	 */
	public String getOneboxName() {
		return oneboxName;
	}

	/**
	 * optional <p>
	 * The password for the user if HTTP Basic authentication is being used. 	
	 * 
	 * @return Text
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *  	
	 * required <p>
	 * P0, P1, P2, ..., Pn.
	 * Match groups from the regular expression evaluation (if applicable) 	
	 * 
	 * @return Text
	 */
	public String[] getMatchGroups() {
		return matchGroups;
	}

	/**
	 * required <p>
	 * The query string from the user's query to a search appliance. The query string does not include the trigger term. 
	 * 
	 * @return UTF-8 encoded and URL-escaped text
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * optional <p>
	 * The user identity for secure or personalized results from a provider:
	 * 
	 * If authType=basic, userName is the username of the search user.
	 * If authType=ldap, userName is the distinguished name (DN) from the LDAP server.
	 * If authType=sso, userName is the user of the cookie used by a provider.
	 * 
	 * @return Text
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * optional <p>
	 * The cookie for SSO.
	 * 
	 * @return Cookie
	 */
	public Cookie getSsoCookie() {
		return ssoCookie;
	}	
	
	@Override
	public String toString() {

		return "CallParameter: Query - " + this.getQuery() + 
				", APIMin - " + this.getApiMin() + 
				", APIMaj - " + this.getApiMaj() +
				", AuthType - " + this.getAuthType().getName() +
				", DateTime - " + this.getDateTime() +
				", IPAddr - " + this.getIpAddr() +
				", Lang - " + this.getLang() +
				", OneBoxName - " + this.getOneboxName() +
				", Password - " + this.getPassword() +
				", UserName - " + this.getUserName();
	}
}
