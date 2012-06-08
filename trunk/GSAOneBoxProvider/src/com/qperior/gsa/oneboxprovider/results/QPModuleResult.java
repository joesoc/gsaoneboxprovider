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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.qperior.gsa.oneboxprovider.util.QPXMLHelper;


/**
 * Class representing a single result from a OneBox provider response.
 * <p>
 * Used in conjunction with the
 * {@link QPOneBoxResults#addResult OneBoxResults.addResult()}
 * method to add a result element to the results set.  Up to
 * eight <code>ModuleResult</code> objects may be added to
 * a <code>OneBoxResults</code> instance.
 *  
 * @see QPOneBoxResults
 */
public class QPModuleResult
{
	private static String XML_MODULE_RESULT = "MODULE_RESULT";
	private static String XML_U = "U";
	private static String XML_TITLE = "Title";
	
	private String title;
	private String u;
	private List<QPModuleResultField> fields = new ArrayList<QPModuleResultField>();
	/**
	 * The only constructor requires both a URL for the result link
	 * and a display text String for the link. 
	 * 
	 * @param title
	 * @param u
	 */
	public QPModuleResult(String title, String u) { 
		this.title = title; 
		this.u = u; 
	}
	/**
	 * Optionally, up to eight {@link QPModuleResultField} elements can be added to
	 * the result to provide additional information for display.
	 * 
	 * @param field
	 */
	public void addField(QPModuleResultField field) { 
		if (this.fields.size() >= 8)
			throw new IndexOutOfBoundsException("Attempt to return too many OneBox results fields");
		this.fields.add(field); 
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean canAddField()
	{
		return this.fields.size() < 8;
	}
	
	/**
	 * Optional
	 * @return the display text for the result link
	 */
	public String getTitle() { 
		return title; 
	}
	/**
	 * Optional
	 * @return the URL for the result link
	 */
	public String getU() { 
		return u; 
	}
	/**
	 * @return a List of the {@link QPModuleResultField} elements added, if any
	 */
	public List<QPModuleResultField> getFields() { 
		return fields; 
	}
	
	/**
	 * Converts it to a XML string
	 * 
	 * @return String
	 */
	public String toXMLString() {
		
		StringBuffer buf = new StringBuffer();
		buf.append(QPXMLHelper.buildElementBegin(XML_MODULE_RESULT));
		String url = this.getU();
		if (url != null) {
			buf.append(QPXMLHelper.buildElementBegin(XML_U))
				.append(url)
				.append(QPXMLHelper.buildElementEnd(XML_U));
		}
		String title = this.getTitle();
		if (title != null) {
			buf.append(QPXMLHelper.buildElementBegin(XML_TITLE))
				.append(title)
				.append(QPXMLHelper.buildElementEnd(XML_TITLE));
		}
		for (Iterator<QPModuleResultField> iter2 = this.getFields().iterator(); iter2.hasNext(); )
		{
			QPModuleResultField field = (QPModuleResultField)iter2.next();
			buf.append(field.toXMLString());
		}
		buf.append(QPXMLHelper.buildElementEnd(XML_MODULE_RESULT));
		
		return buf.toString();
	}
}
