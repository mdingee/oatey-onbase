package com.oatey.scale.onbase.preprocessor.domain.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.oatey.scale.onbase.preprocessor.domain.Logger;

public class InputObjectFactory {
	
	public InputObject build(File file) {
		if(file == null) {
			Logger.logFailure("<Null>","Unable to create input object.  Input file is null");
			return null;
		}
		
		FileReader fileReader = null;
		BufferedReader br = null;
		
		try {
			fileReader = new FileReader(file);

			br = new BufferedReader(fileReader);

			String line = null;
		 
			if ((line = br.readLine()) != null) {
				return build(line);
			}
		} catch (Exception e) {
			Logger.logException(file.getName(), e);
		} finally {
			try { br.close(); br = null;} catch (Exception e) {}
			try { fileReader.close(); fileReader = null; } catch (Exception e) {}
		}
		
		Logger.logFailure(file.getName(),"Unable to create input object");
		return null;
	}
	
	public InputObject build(String values) {
		Logger.logStep(null, "Building Document based on {" + values + "}");
		
		if(values == null)
			return null;
		
		if(values.isEmpty())
			return null;
		
		String[] originalValues = parse(values);
		String[] cleanValues = new String[originalValues.length];
		
		for(int i = 0; i < originalValues.length; i++) {
			String originalValue = originalValues[i];
			String cleanValue = clean(originalValue);
			cleanValues[i] = cleanValue;
		}
		
		return new InputObject(cleanValues);
	}
	
	private String[] parse(String values) {
		return values.split(",");
	}
	
	private String clean(String string) {
		return string.replaceAll("\"", "");
	}
}
