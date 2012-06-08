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
package com.qperior.gsa.oneboxprovider.implementations.jive.rest;

import java.util.ArrayList;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;

import com.qperior.gsa.oneboxprovider.results.QPIOneBoxResults;
import com.qperior.gsa.oneboxprovider.results.QPModuleResult;
import com.qperior.gsa.oneboxprovider.results.QPModuleResultField;
import com.qperior.gsa.oneboxprovider.results.QPOneBoxResults;
import com.qperior.gsa.oneboxprovider.util.QPLogger;

/**
 * To handle the JSON String delivered by the Jive REST API.<br>
 * It delivers a result like:<br>
 * <pre>
 * {@code
 * <MODULE_RESULT>
 *	<U>http://localhost:50001/oc/v1/posts/1021</U>
 *	<Title>lion nPsi7QTfmGik0epyWR9lRVWnrTcawd</Title>
 *	<Field name="type">post</Field>
 *	<Field name="author">Administrator</Field>
 *	<Field name="summary">6VhaGwWbXKZsq2wByCrWG4f0GS2FMw</Field>
 * </MODULE_RESULT>
 * }
 * </pre>
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPJiveJsonObject {
	
	private static String JSON_DATA = "data";
	private static String JSON_TYPE = "type";
	private static String JSON_SUBJECT = "subject";
	private static String JSON_AUTHOR = "author";
	private static String JSON_NAME = "name";
	private static String JSON_USERNAME = "username";
	private static String JSON_LINKS = "links";
	private static String JSON_ALT = "alt";
	private static String JSON_AVATAR = "avatar";
	private static String JSON_REPLYCOUNT = "replyCount";
	private static String JSON_LIKECOUNT = "likeCount";
	private static String JSON_CONTENTSUMMARY = "contentSummary";   	
	private static String JSON_NEXT = "next";
	private static String JSON_PREVIOUS = "previous";	
	private static String JSON_RESOURCES = "resources";
	private static String JSON_SELF = "self";
	private static String JSON_HTML = "html";
	private static String JSON_REF = "ref";
	private static String JSON_ALLOWED = "allowed"; 
	private static String JSON_MODDATE = "modificationDate";
	
	private Log log = QPLogger.getLogger(this.getClass());
	
	/**
	 * Converts a JSON-String to OneBox result. 
	 * If an error accours it returns null.
	 * 
	 * @param json
	 * @return QPIOneBoxResults or null (if error)
	 */
	public QPIOneBoxResults convertJsonToResult(String json) {
		
		try {
			this.log.info("Converting JSON message: " + json);
			QPIOneBoxResults results = new QPOneBoxResults();
			
			JSONObject jsonObject = JSONObject.fromObject( json );  
			MorphDynaBean bean = (MorphDynaBean) JSONObject.toBean( jsonObject );  
			ArrayList<MorphDynaBean> data = (ArrayList<MorphDynaBean>) PropertyUtils.getProperty( bean, JSON_DATA );
			MorphDynaBean links = this.ensureBeanProperty( bean, JSON_LINKS );
			if ( links != null ) {
				//String next = this.ensureStringProperty(links, JSON_NEXT );
				//String previous = this.ensureStringProperty( links, JSON_PREVIOUS );
			}
			MorphDynaBean dataBean;			
			for (int i = 0; i < data.size(); i++) {
				dataBean = data.get(i);
				if ( dataBean != null ) {
					String type = this.ensureStringProperty( dataBean, JSON_TYPE );
					String subject = this.ensureStringProperty( dataBean, JSON_SUBJECT );
					MorphDynaBean author = this.ensureBeanProperty( dataBean, JSON_AUTHOR );
					String authorName = null;
					if ( author != null ) {
						authorName = this.ensureStringProperty( author, JSON_NAME );
						//String username = this.ensureStringProperty( author, JSON_USERNAME );
						MorphDynaBean authorLinks = this.ensureBeanProperty( author, JSON_LINKS );
						if ( authorLinks != null ) {
							//String alt = this.ensureStringProperty( authorLinks, JSON_ALT );
							//String avatar = this.ensureStringProperty( authorLinks, JSON_AVATAR );
						}					
					}
					//Integer replycount = this.ensureIntegerProperty( dataBean, JSON_REPLYCOUNT );
					//Integer likecount = this.ensureIntegerProperty( dataBean, JSON_LIKECOUNT );
					String contentsummary = this.ensureStringProperty( dataBean, JSON_CONTENTSUMMARY );
					String htmlref = null;
					MorphDynaBean resources = this.ensureBeanProperty( dataBean, JSON_RESOURCES );
					if ( resources != null ) {
						MorphDynaBean self = this.ensureBeanProperty( resources, JSON_SELF );
						if ( self != null ) {
							//String selfref = this.ensureStringProperty( self, JSON_REF );
							//String selfallowed = this.ensureStringProperty( self, JSON_ALLOWED );
						}
						MorphDynaBean html = this.ensureBeanProperty( resources, JSON_HTML );
						if ( html != null ) {
							htmlref = this.ensureStringProperty( html, JSON_REF );
							//String htmlallowed = this.ensureStringProperty( html, JSON_ALLOWED );
						}
					};
					//String moddate = this.ensureStringProperty( dataBean, JSON_MODDATE );
					MorphDynaBean dataLinks = this.ensureBeanProperty( dataBean, JSON_LINKS );
					if ( dataLinks != null ) {
						//String alt = this.ensureStringProperty( dataLinks, JSON_ALT );
					}
					
					QPModuleResult result = new QPModuleResult(subject, htmlref);
					QPModuleResultField field;
					if ( type != null ) {
						field = new QPModuleResultField("type", type);
						result.addField(field);
					}
					if ( authorName != null ) {
						field = new QPModuleResultField("author", authorName);
						result.addField(field);
					}
					if ( contentsummary != null ) {
						// convert e.g. "&nbsp;" to "&#160;"
						// see http://de.selfhtml.org/html/referenz/zeichen.htm#benannte_iso8859_1
						//field = new QPModuleResultField("summary", contentsummary);
						//result.addField(field);
					}
					results.addResult(result);
				}
			}

			this.log.info("Finished converting JSON message.");
			return results;
		} catch (Exception exc ) {
			this.log.error("Exception in converting JSON message: ", exc );
			return null;
		}
	}
	
	private MorphDynaBean ensureBeanProperty( Object bean, String name ) {
		try {
			return (MorphDynaBean) PropertyUtils.getProperty( bean, name );
		} catch ( Exception exc ) {
			this.log.info("Exception in getting JSON property '" + name + "': " + exc.getLocalizedMessage() );
			return null;
		}
	}
	
	private String ensureStringProperty( Object bean, String name ) {
		try {
			return (String) PropertyUtils.getProperty( bean, name );
		} catch ( Exception exc ) {
			this.log.info("Exception in getting JSON property '" + name + "': " + exc.getLocalizedMessage() );
			return null;
		}
	}
	
	private Integer ensureIntegerProperty( Object bean, String name ) {
		try {
			return (Integer) PropertyUtils.getProperty( bean, name );
		} catch ( Exception exc ) {
			this.log.info("Exception in getting JSON property '" + name + "': " + exc.getLocalizedMessage() );
			return null;
		}
	}
}
