package com.oatey.scale.onbase.preprocessor.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.oatey.scale.onbase.preprocessor.domain.output.Document_BillOfLading;

public class Document_BillOfLadingUTest {

	private static final String ORIG_BOL_NUM = "00387530003192267";
	private static final String TRUNCATED_BOL_NUM = "0003192267";
	
	@Test
	public void testBillOfLadingNumberTrucation() {
		Document_BillOfLading doc = new Document_BillOfLading();
		doc.setBillOfLadingNumber(ORIG_BOL_NUM);
		
		String newBillOfLadingNumber = doc.getBillOfLadingNumber();
		assertEquals(TRUNCATED_BOL_NUM, newBillOfLadingNumber);
		
		doc.setBillOfLadingNumber(TRUNCATED_BOL_NUM);
		newBillOfLadingNumber = doc.getBillOfLadingNumber();
		assertEquals(TRUNCATED_BOL_NUM, newBillOfLadingNumber);
		
		doc.setBillOfLadingNumber(null);
		newBillOfLadingNumber = doc.getBillOfLadingNumber();
		assertEquals("", newBillOfLadingNumber);
		
	}

}
