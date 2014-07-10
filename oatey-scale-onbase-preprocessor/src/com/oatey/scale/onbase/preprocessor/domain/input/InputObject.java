package com.oatey.scale.onbase.preprocessor.domain.input;

public class InputObject {
	String[] fields;
	
	public InputObject(String[] fields) {
		this.fields = fields;
	}
	
	public int length() {
		if(fields == null)
			return 0;
		
		return fields.length;
	}
	
	public String getValue(int i) {
		return fields[i];
	}
}
