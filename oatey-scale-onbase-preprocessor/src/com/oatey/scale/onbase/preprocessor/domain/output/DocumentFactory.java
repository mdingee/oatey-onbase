package com.oatey.scale.onbase.preprocessor.domain.output;

import com.oatey.scale.onbase.preprocessor.domain.Logger;
import com.oatey.scale.onbase.preprocessor.domain.input.InputObject;
import com.oatey.scale.onbase.preprocessor.domain.repositories.ScaleRepository;

public class DocumentFactory {
	
	private ScaleRepository scaleRepository;
	
	public Document build(InputObject inputObject) {
		Document document = null;
		
		if(isBillOfLading(inputObject))
			document = buildBillOfLading(inputObject);
		
		if(isPackingSlip(inputObject))
			document = buildPackingSlip(inputObject);
		
		if(document != null)
			Logger.logStep(null, "Document created = " + document);
		
		return document;
	}
	
	public Document_BillOfLading buildBillOfLading(InputObject inputObject) {
		Logger.logStep(null,"Creating Bill of Lading");
		
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
		Logger.logStep(null, "Fetching customer number from SCALE for ShipmentID " + shipmentId);
		return getScaleRepository().getCustomerFromShipment(shipmentId);
	}

	public Document_PackingList buildPackingSlip(InputObject inputObject) {
		Logger.logStep(null,"Creating Packing List");
		String purchaseOrderNumber = inputObject.getValue(0);
		String shipmentId = inputObject.getValue(1);
		String fileName = inputObject.getValue(3);
		
		Document_PackingList document = new Document_PackingList();
		document.setPurchaseOrderNumber(purchaseOrderNumber);
		document.setShipmentId(shipmentId);
		document.setFileName(fileName);
		
		return document;
	}
	
	public boolean isBillOfLading(InputObject inputObject) {
		String docType = inputObject.getValue(2);
		return docType.matches("(.*)Bill of Lading");
	}
	
	public boolean isPackingSlip(InputObject inputObject) {
		String docType = inputObject.getValue(2);
		return docType.matches("(.*)Packing List");
	}

	public ScaleRepository getScaleRepository() {
		return scaleRepository;
	}

	public void setScaleRepository(ScaleRepository scaleRepository) {
		this.scaleRepository = scaleRepository;
	}
}
