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
package com.qperior.GSAOneBoxProvider.implementations.dummy;

import static org.junit.Assert.*;

import org.junit.Test;

import com.qperior.gsa.oneboxprovider.implementations.dummy.QPDummyProviderForNone;
import com.qperior.gsa.oneboxprovider.results.QPIOneBoxResults;
import com.qperior.gsa.oneboxprovider.util.exception.QPProviderException;

/**
 * 
 * @author Ralf Ovelgoenne
 *
 */
public class QPDummyProviderForNoneTest {

	/**
	 * 
	 */
	@Test
	public void testProvideOneBoxResults() {
		QPDummyProviderForNone none = new QPDummyProviderForNone();
		try {
			QPIOneBoxResults actual = none.provideOneBoxResults();
			int results = actual.getNumberOfResults();
			assertEquals(0, results);
			assertEquals( "Dummy Provider (none)", actual.getProvider());
			assertEquals("Empty result.", actual.getDiagnostics());
			assertEquals("lookupFailure", actual.getResultCode().getName());
		} catch ( QPProviderException pexc ) {
			fail("Exception: " + pexc.getLocalizedMessage());
		}
	}

	/**
	 * 
	 */
	@Test
	public void testGetProviderName() {
		QPDummyProviderForNone none = new QPDummyProviderForNone();
		String actual = none.getProviderName();
		
		assertEquals("Dummy Provider (none)", actual);
	}

}
