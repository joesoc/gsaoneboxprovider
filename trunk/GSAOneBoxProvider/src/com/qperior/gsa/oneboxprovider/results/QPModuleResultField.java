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
 * Class representing a unit of metadata for a OneBox provider result.
 * <p>
 * Each Field element has at minimum a name and a value.  It then has
 * other name/value pair attributes as needed. 
 * 
 * @see QPModuleResult
 */
public class QPModuleResultField
{
	private static String XML_ATTRIBUTE_NAME = "name";	
	private static String XML_FIELD = "Field";
	
	private List<String> attrs = new ArrayList<String>();
	private String value;
	
	/**
	 * The constructor requires the name of the Field element and the
	 * value of the element.
	 * 
	 * @param name
	 * @param value
	 */
	public QPModuleResultField(String name, String value) { 
		this.addAttr(XML_ATTRIBUTE_NAME, name); 
		this.value = value;
	}
	
	/**
	 * Optionally, additional attributes can be added to the Field
	 * element as needed for display formatting.
	 *  
	 * @param name
	 * @param value
	 */
	public void addAttr(String name, String value) { 
		attrs.add( name + "=\"" + value + "\""); 
	}
	
	/**
	 * @return the List of attributes for this Field element where each
	 * attribute is a String of form: name="value".  The List includes
	 * the "name" attribute.
	 */
	public List<String> getAttrs() { 
		return attrs;
	}
	/**
	 * @return the value of the Field element
	 */
	public String getValue() { 
		return value;
	}
	
	/**
	 * Converts it to a XML string
	 * 
	 * @return XML String
	 */
	public String toXMLString() {
		
		StringBuffer buf = new StringBuffer();
		buf.append("<" + XML_FIELD);
		for (Iterator<String> iter3 = this.getAttrs().iterator(); iter3.hasNext(); )
		{
			String attr = (String)iter3.next();
			buf.append(" ").append(attr);
		}
		buf.append(">");
		if (this.getValue() != null) {
			buf.append(this.getValue());
		}
		buf.append(QPXMLHelper.buildElementEnd(XML_FIELD));
		
		return buf.toString();
	}
}
