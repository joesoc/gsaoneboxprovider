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

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.qperior.gsa.oneboxprovider.util.QPXMLHelper;
import com.qperior.gsa.oneboxprovider.util.exception.QPOneBoxResultException;


/**
 * Class encapsulating a response result set from a call to a OneBox provider.
 * <p>
 * OneBox clients expect an XML response that adhere's to Google's Schema.  This
 * class, along with {@link QPModuleResult} and {@link QPModuleResultField}, hides those details
 * by providing an API for conveniently building a result set.  The
 * {@link #toXMLString()} method converts the class' current state as a valid
 * OneBox for Enterprise XML response.
 * 
 * @see QPModuleResult
 * @see QPModuleResultField
 */
public class QPOneBoxResults implements QPIOneBoxResults
{
	private static String XML_ONEBOXRESULTS = "OneBoxResults";
	private static String XML_START = "?xml version=\"1.0\" encoding=\"UTF-8\"?";
	private static String XML_XMLNS = "OneBoxResults xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"oneboxresults.xsd\"";
	private static String XML_RESULTCODE = "resultCode";		
	private static String XML_DIAGNOSTICS = "Diagnostics";
	private static String XML_PROVIDER = "provider";
	private static String XML_TITLE = "title";
	private static String XML_URLTEXT = "urlText";
	private static String XML_URLLINK = "urlLink";
	private static String XML_IMAGESOURCE = "IMAGE_SOURCE";
	private static String XML_SEARCHTERM = "searchTerm";
	private static String XML_TOTALRESULTS = "totalResults";
	
	// A return code from the OneBox provider. The value can be one of the following:
	// "success", "lookupFailure", "securityFailure", or "timeout"
	// The value "success" is assumed if no value is returned, and results are
	// processed only on success. Although this element is optional, it is good
	// practice to always return a result code.
	// Optional
	private QPResultCode resultCode = QPResultCode.success;	
	// If lookup fails, the provider uses this element to send diagnostic
	// information and sets the resultCode attribute to a value other than success.
	// Optional
	private String diagnostics = null;
	// The name of the provider.  The name need not match the name provided in
	// the OneBox module definition, and can be more descriptive than that name.
	// Optional
	private String provider = null;
	
	private String searchTerm = null;
	
	// The path to an image that is used as an identifying icon or informational
	// image for the result set.
	// Optional
	private String imageUrl = null;
	// The title of the result, consisting of a line of text and a link to the full
	// result set from the provider. This element is optional, but if one of the
	// following child elements is present, both must be present.
	private String urlText = null;
	private String urlLink = null;
	
	private List<QPModuleResult> results = new ArrayList<QPModuleResult>();
	
	/**
	 * Basic constructor.
	 */
	public QPOneBoxResults() {
		
	}
	
	/**
	 * Constructor with the main attributes.
	 * 
	 * @param provider
	 * @param text
	 * @param url
	 * @param imageUrl
	 */
	public QPOneBoxResults(String provider, String text, String url, String imageUrl) { 
		this.provider = provider; 
		this.urlText = text;
		this.urlLink = url;
		this.imageUrl = imageUrl;
	}
	
	@Override
	public void setFailure(QPResultCode resultCode, String diagnosticMessage, String providerName)
	{
		this.resultCode = resultCode;
		this.diagnostics = diagnosticMessage;
		this.provider = providerName;
	}
	
	@Override
	public QPResultCode getResultCode() {
		return resultCode;
	}

	@Override
	public String getDiagnostics() {
		return diagnostics;
	}

	@Override
	public void setProvider(String provider) { 
		this.provider = provider; 
	}
	
	@Override
	public String getProvider() {
		return provider;
	}

	@Override
	public void setTitle(String text, String url)
	{
		this.urlText = text;
		this.urlLink = url;
	}
	
	@Override
	public String getTitleUrlText() {
		return urlText;
	}

	@Override
	public String getTitleUrlLink() {
		return urlLink;
	}

	@Override
	public void setImageUrl(String imageUrl) { 
		this.imageUrl = imageUrl; 
	}
	
	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public void addResult(QPModuleResult result)
	{
		if (this.results.size() >= 8)
			throw new IndexOutOfBoundsException("Attempt to return too many OneBox results");
		this.results.add(result);
	}

	@Override
	public boolean canAddResult()
	{
		return this.results.size() < 8;
	}
	
	@Override
	public int getNumberOfResults() {

		if ( this.results != null )
		{
			return this.results.size();
		}
		return 0;
	}

	@Override
	public boolean isSuccess() {
		return this.resultCode.isSuccess();
	}
	
	@Override
	public String toString() {

		return "OneBox Results: " + this.resultCode.getName();
	}

	@Override
	public String toXMLString() throws QPOneBoxResultException
	{
		try {
			StringBuffer buf = new StringBuffer();
			buf.append(QPXMLHelper.buildElementBegin(XML_START));
			buf.append(QPXMLHelper.buildElementBegin(XML_XMLNS));
			buf.append(QPXMLHelper.buildElementBegin(XML_RESULTCODE))
				.append(this.resultCode.getName())
				.append(QPXMLHelper.buildElementEnd(XML_RESULTCODE));
			if (this.diagnostics != null) {
				buf.append(QPXMLHelper.buildElementBegin(XML_DIAGNOSTICS))
					.append(this.diagnostics.substring(0,Math.min(this.diagnostics.length(),256)))
					.append(QPXMLHelper.buildElementEnd(XML_DIAGNOSTICS));
			}
			if (this.provider != null) {
				buf.append(QPXMLHelper.buildElementBegin(XML_PROVIDER))
					.append(this.provider.substring(0,Math.min(this.provider.length(),128)))
					.append(QPXMLHelper.buildElementEnd(XML_PROVIDER));
			}
			if (this.searchTerm != null) {
				buf.append(QPXMLHelper.buildElementBegin(XML_SEARCHTERM))
					.append(this.searchTerm.substring(0,Math.min(this.searchTerm.length(),128)))
					.append(QPXMLHelper.buildElementEnd(XML_SEARCHTERM));
			}
			buf.append(QPXMLHelper.buildElementBegin(XML_TOTALRESULTS))
				.append(this.getNumberOfResults())
				.append(QPXMLHelper.buildElementEnd(XML_TOTALRESULTS));
			if ((this.urlText != null) && (this.urlLink != null)) {
				buf.append(QPXMLHelper.buildElementBegin(XML_TITLE));
				buf.append(QPXMLHelper.buildElementBegin(XML_URLTEXT))
					.append(this.urlText.substring(0,Math.min(this.urlText.length(),40)))
					.append(QPXMLHelper.buildElementEnd(XML_URLTEXT));
				buf.append(QPXMLHelper.buildElementBegin(XML_URLLINK))
					.append(this.urlLink)
					.append(QPXMLHelper.buildElementEnd(XML_URLLINK));
				buf.append(QPXMLHelper.buildElementEnd(XML_TITLE));
			}
			if (this.imageUrl != null) {
				buf.append(QPXMLHelper.buildElementBegin(XML_IMAGESOURCE))
					.append(this.imageUrl)
					.append(QPXMLHelper.buildElementEnd(XML_IMAGESOURCE));
			}
			for (Iterator<QPModuleResult> iter = this.results.iterator(); iter.hasNext(); )
			{
				QPModuleResult moduleResult = (QPModuleResult)iter.next();
				buf.append(moduleResult.toXMLString());
			}
			buf.append(QPXMLHelper.buildElementEnd(XML_ONEBOXRESULTS));
			return buf.toString();
			
		} catch ( Exception exc ) {
			throw new QPOneBoxResultException("Error in converting OneBox result to string.", exc);
		}
	}
}
