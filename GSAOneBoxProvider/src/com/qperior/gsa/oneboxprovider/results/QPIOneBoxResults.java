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
package com.qperior.gsa.oneboxprovider.results;

import com.qperior.gsa.oneboxprovider.QPOneBoxProviderServlet;
import com.qperior.gsa.oneboxprovider.util.exception.QPOneBoxResultException;

/**
 * Interface that must be implemented by classes encapsulating OneBox
 * for Enterprise result sets.  This interface is used in the
 * {@link QPOneBoxProviderServlet#processRequest QPOneBoxProviderServlet.processRequest()}
 * method to write out an HTTP response to the OneBox request.
 * <p>
 * Your OneBoxProvider can create a custom results set object as long
 * as it implements this interface.
 * 
 * @author Ralf Ovelgoenne
 * 
 * @see QPOneBoxProviderServlet
 * @see QPOneBoxResults
 */
public interface QPIOneBoxResults {
	
	/**
	 * Converts the current state of this OneBoxResults object to an XML string
	 * that conforms to the Google Schema for OneBox for Enterprise provider responses.
	 * 
	 * @return XML String
	 * @throws QPOneBoxResultException 
	 */
	public String toXMLString() throws QPOneBoxResultException;
	
	/**
	 * 
	 * @return Number
	 */
	public int getNumberOfResults();
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isSuccess();
	
	/**
     * 
     * @return QPResultCode
     */
	public QPResultCode getResultCode();

	/**
	 * 
	 * @return String
	 */
	public String getDiagnostics();
	
	/**
	 * 
	 * @return String
	 */
	public String getProvider();
	
	/**
	 * Text identifying the provider that ultimately handled the OneBox request.
	 * 
	 * @param provider
	 */
	public void setProvider(String provider);
	
	/**
	 * 
	 * @return String
	 */
	public String getTitleUrlText();

	/**
	 * 
	 * @return String
	 */
	public String getTitleUrlLink();
	
	/**
	 * Optionally provide a title link for the results set.  The link would be a URL
	 * to a full result set and the text would be a display description for that link.
	 * 
	 * @param urltext
	 * @param urllink
	 */
	public void setTitle(String urltext, String urllink);
	
	/**
	 * 
	 * @return String
	 */
	public String getImageUrl();
	
	/**
	 * Optionally provide an identifying icon for the result set.  This is the URL
	 * path to an image.
	 * 
	 * @param imageUrl
	 */
	public void setImageUrl(String imageUrl);
	
	/**
	 * Add an actual result to the OneBox provider's result set.  Up to eight results
	 * can be returned.
	 * 
	 * @param result
	 * @throws IndexOutOfBoundsException when too many results are added
	 */
	public void addResult(QPModuleResult result);
	
	/**
	 * Determines whether there is room to add another result.  If this returns true
	 * the next call to {@link #addResult(QPModuleResult) addResult()} will succeed.
	 * 
	 * @return boolean
	 */
	public boolean canAddResult();
	
	/**
	 * In the event of provider failure, set the failure code and a diagnostic
	 * message.  If this is called on a results set, the OneBox request will be
	 * deemed unsuccessful and results will not appear in the end user's search
	 * results.
	 * <p>
	 * Sometimes a provider cannot return a result set. For example, a provider 
	 * could fail to return results because the provider's request to its data 
	 * source times out, because authentication fails, or because the provider's data 
	 * lookup completes, but returns zero results. In such a case, a provider should 
	 * send a results message with the following characteristics:
	 * <p>
     * - The value of the <resultCode> element is lookupFailure, securityFailure, or timeout.<p>
     * - There are no instances of the <MODULE_RESULT> element.
     * <p>
	 * Optionally, a provider can use the <Diagnostics> element to return more detailed information.
	 * <p>
	 * When a search appliance receives a response whose <resultCode> element is set to 
	 * an explicit value other than success, the search appliance logs the response. The 
	 * user's search results do not include OneBox results from a provider or any explicit 
	 * indication of a failure.
	 * <p>
	 * An error condition appears as XML code in the <resultCode> element. In the following example, 
	 * the ACME employee directory requires a username and password, and in this example, 
	 * the password passed is incorrect:
	 * <p>
	 * <pre>
	 * {@code
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * <OneBoxResults xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	 * <resultCode>"securityFailure"</resultCode>
	 * <Diagnostics>invalid password</Diagnostics>
	 * <provider>ACME Employee Directory</provider>
	 * </OneBoxResults>
	 * </code>
	 * }
	 * </pre>
	 * <p>
	 * If a OneBox provider becomes unreachable and a search appliance receives an HTTP error code, 
	 * the search appliance logs the error. No error is shown to the user. 
	 * 
	 * @param resultCode the code defining the nature of the failure
	 * @param diagnosticMessage descriptive text explaining the failure 
	 * @param providerName name of the provider for the result
	 */
	public void setFailure(QPResultCode resultCode, String diagnosticMessage, String providerName);
}
