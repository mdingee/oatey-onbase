package com.oatey.scale.onbase.preprocessor.domain.output;

public class Document_PackingSlip extends Document {
	
	private String purchaseOrderNumber;
	private String shipmentId;
	
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	
	public String getPurchaseOrderNumber() {
		return this.purchaseOrderNumber;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getShipmentId() {
		return this.shipmentId;
	}
	
	@Override
	public String getDocumentType() {
		return Document.DOC_TYPE_PACK_SLIP;
	}

	@Override
	public String toString() {
		return "\"" + getDocumentType() + "\"," +
			   "\"" + getPurchaseOrderNumber() + "\"," +
			   "\"" + getShipmentId() + "\"," +
			   "\"" + getFileName() + "\"";
	}

}
