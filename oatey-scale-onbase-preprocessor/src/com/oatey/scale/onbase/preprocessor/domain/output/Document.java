package com.oatey.scale.onbase.preprocessor.domain.output;

public abstract class Document {
	public static final String DOC_TYPE_BOL = "DIST - Bill of Lading";
	public static final String DOC_TYPE_PACK_SLIP = "DIST - Packing Slip";
	
	private String fileName;
	
	public void setFileName(String fileName) {
		this.fileName = cleanFileName(fileName);
	}
	
	public String getFileName() {
		return fileName;
	}
	
	protected String cleanFileName(String dirtyFileName) {
		String[] pieces = dirtyFileName.split("\\");
		return pieces[(pieces.length - 1)];
	}
	
	public abstract String getDocumentType();
}
