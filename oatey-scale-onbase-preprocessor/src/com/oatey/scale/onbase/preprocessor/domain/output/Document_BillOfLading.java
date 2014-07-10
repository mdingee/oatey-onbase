package com.oatey.scale.onbase.preprocessor.domain.output;

import com.oatey.scale.onbase.preprocessor.domain.DomainContext;

public class Document_BillOfLading extends Document {

	private String purchaseOrderNumber;
	private String shipmentId;
	private String billOfLadingNumber;
	private String customerNumber;

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getBillOfLadingNumber() {
		return billOfLadingNumber;
	}

	public void setBillOfLadingNumber(String billOfLadingNumber) {
		this.billOfLadingNumber = trucateBillOfLadingNumber(billOfLadingNumber);
	}
	
	public String getCustomerNumber() {
		return this.customerNumber;
	}
	
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	protected String trucateBillOfLadingNumber(String billOfLadingNumber) {
		if(billOfLadingNumber == null)
			return "";
		
		if(billOfLadingNumber.length() <= 10)
			return billOfLadingNumber;
		
		return billOfLadingNumber.substring(billOfLadingNumber.length() - 10);
	}

	@Override
	public String toString() {
		return "\"" + getDocumentType() + "\"," +
				"\"" + getCustomerNumber() + "\"," +
				"\"" + getPurchaseOrderNumber() + "\"," +
				"\"" + getShipmentId() + "\"," +
				"\"" + getBillOfLadingNumber() + "\"," +
				"\"" + DomainContext.getOutputDirectory() + "/unprocessed/" + getFileName() + "\"";
	}
	
	@Override
	public String getDocumentType() {
		return Document.DOC_TYPE_BOL;
	}

}
