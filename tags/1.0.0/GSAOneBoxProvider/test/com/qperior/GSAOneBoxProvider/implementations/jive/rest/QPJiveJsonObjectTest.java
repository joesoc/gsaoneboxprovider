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
package com.qperior.GSAOneBoxProvider.implementations.jive.rest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.qperior.gsa.oneboxprovider.implementations.jive.rest.QPJiveJsonObject;
import com.qperior.gsa.oneboxprovider.results.QPIOneBoxResults;

/**
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPJiveJsonObjectTest {
	
	private String getJSONString(String fileName) {
		
		String jsonString = "";
		
		try {
		
			URL url = ConfigurationUtils.locate(null, fileName);
			File file = FileUtils.toFile(url);
			jsonString =  FileUtils.readFileToString(file);
			
		} catch (IOException exc) {
			
			//nothing
		}
		
		return jsonString;
	}

	/**
	 * 
	 */
	@Test
	public void testConvertJsonToResult() {
		
		// Case 1
		// normal result
		QPJiveJsonObject jsonObject = new QPJiveJsonObject();
		QPIOneBoxResults results1 = jsonObject.convertJsonToResult(this.getJSONString("data/json_test3.txt"));
		assertNotNull(results1);
		
		// Case 2
		// empty result
		/*try {
			String providerClassName = "com.qperior.GSAOneBoxProvider.implementations.jive.QPJiveProvider";
			QPIProvider provider = (QPIProvider) Class.forName(providerClassName).newInstance();
			String str = "test";
		} catch ( Exception exc ) {
			String str = "test";
		}
		
		// Case 3
		// empty string
		QPIOneBoxResults results3 = jsonObject.convertJsonToResult("");
		assertNull(results3);
		
		// Case 4
		// null 
		QPIOneBoxResults results4 = jsonObject.convertJsonToResult(null);
		assertNull(results4);
		
		String jsonString = "{" +
				  "\"data\" : [ {" +
					    //"\"type\" : \"post\"," +
					    "\"subject\" : \"lion nPsi7QTfmGik0epyWR9lRVWnrTcawd\"," +
					    "\"author\" : {" +
					      "\"name\" : \"Administrator\"," +
					      "\"username\" : \"admin\"," +
					      "\"links\" : {" +
					        "\"alt\" : \"http://localhost:50001/oc/v1/users/1\"," +
					        "\"avatar\" : \"http://localhost:50001/oc/v1/avatars/default\"" +
					      "}" +
					    "}," +
					    "\"replyCount\" : 0," +
					    "\"likeCount\" : 0," +
					    "\"contentSummary\" : \"6VhaGwWbXKZsq2wByCrWG4f0GS2FMw\"," +
					    "\"links\" : {" +
					      "\"alt\" : \"http://localhost:50001/oc/v1/posts/1021\"" +
					    "}" +
					  "} ]," +
					  "\"links\" : {" +
					    "\"next\" : \"http://localhost:50001/oc/v1/search/?type=post&type=discussion&q=lion&limit=6&offset=12\"," +
					    "\"previous\" : \"http://localhost:50001/oc/v1/search/?type=post&type=discussion&q=lion&limit=6\"" +
					  "}" +
					"}";
		QPIOneBoxResults results5 = jsonObject.convertJsonToResult(jsonString);*/
	}

}
