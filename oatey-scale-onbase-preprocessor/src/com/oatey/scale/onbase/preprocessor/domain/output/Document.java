package com.oatey.scale.onbase.preprocessor.domain.output;

import java.io.File;

public abstract class Document {
	public static final String DOC_TYPE_BOL = "DIST - Bill of Lading";
	public static final String DOC_TYPE_PACK_LIST = "DIST - Packing List";
	
	private String fileName;
	
	public void setFileName(String fileName) {
		this.fileName = cleanFileName(fileName);
	}
	
	public String getFileName() {
		return fileName;
	}
	
	protected String cleanFileName(String dirtyFileName) {
		File file = new File(dirtyFileName);
		return file.getName();
	}
	
	public abstract String getDocumentType();
}
