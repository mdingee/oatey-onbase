package com.oatey.scale.onbase.preprocessor.domain.output;

import com.oatey.scale.onbase.preprocessor.domain.input.InputObject;
import com.oatey.scale.onbase.preprocessor.domain.repositories.ScaleRepository;

public class DocumentFactory {
	
	private ScaleRepository scaleRepository;
	
	public Document build(InputObject inputObject) {
		
		if(isBillOfLading(inputObject))
			return buildBillOfLading(inputObject);
		
		if(isPackingSlip(inputObject))
			return buildPackingSlip(inputObject);
		
		return null;
	}
	
	public Document_BillOfLading buildBillOfLading(InputObject inputObject) {
		String purchaseOrderNumber = inputObject.getValue(0);
		String shipmentId = inputObject.getValue(1);
		String billOfLadingNumber = inputObject.getValue(3);
		String customerNumber = getCustomerNumber(shipmentId);
		String fileName = inputObject.getValue(4);
		
		Document_BillOfLading document = new Document_BillOfLading();
		document.setPurchaseOrderNumber(purchaseOrderNumber);
		document.setShipmentId(shipmentId);
		document.setBillOfLadingNumber(billOfLadingNumber);
		document.setCustomerNumber(customerNumber);
		document.setFileName(fileName);
		
		return document;
	}
	
	private String getCustomerNumber(String shipmentId) {
		return getScaleRepository().getCustomerFromShipment(shipmentId);
	}

	public Document_PackingSlip buildPackingSlip(InputObject inputObject) {
		String purchaseOrderNumber = inputObject.getValue(0);
		String shipmentId = inputObject.getValue(1);
		String fileName = inputObject.getValue(3);
		
		Document_PackingSlip doc = new Document_PackingSlip();
		doc.setPurchaseOrderNumber(purchaseOrderNumber);
		doc.setShipmentId(shipmentId);
		doc.setFileName(fileName);
		
		return doc;
	}
	
	public boolean isBillOfLading(InputObject inputObject) {
		String docType = inputObject.getValue(2);
		return docType.matches("(.*)Bill of Lading");
	}
	
	public boolean isPackingSlip(InputObject inputObject) {
		String docType = inputObject.getValue(2);
		return docType.matches("(.*)Packing Slip");
	}

	public ScaleRepository getScaleRepository() {
		return scaleRepository;
	}

	public void setScaleRepository(ScaleRepository scaleRepository) {
		this.scaleRepository = scaleRepository;
	}
}
