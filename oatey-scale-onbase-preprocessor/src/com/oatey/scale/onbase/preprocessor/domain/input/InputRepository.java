package com.oatey.scale.onbase.preprocessor.domain.input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.oatey.scale.onbase.preprocessor.domain.DomainContext;
import com.oatey.scale.onbase.preprocessor.domain.Logger;

public class InputRepository {
	public String[] getInputFiles() {
		Logger.logStep(null, "Compiling list of files to process");
		
		File folder = new File(DomainContext.getInputDirectory() + "/unprocessed");
		File[] listOfFiles = folder.listFiles();
		
		List<String> fileNames = new ArrayList <String> ();
		for(File file : listOfFiles) {
			if(!file.isFile())
				continue;
			
			String fileName = file.getName();
			
			if(!fileName.matches("(.*).csv"))
				continue;
			
			fileNames.add(fileName);
		}
		
		String[] listOfFileNames = fileNames.toArray(new String[0]);
		Logger.logStep(null, "Unprocessed files = " + listOfFileNames.toString());
		
		return listOfFileNames;
	}
	
	public File getUnprocessedFile(String fileName) {
		Logger.logStep(null, "Retrieving file " + fileName);
		return new File(DomainContext.getInputDirectory() + "/unprocessed/" + fileName);
	}
	
	public boolean moveToProcessedFolder(File file) {
		Logger.logStep(null, "Moving file " + file.getName() + " to processed folder");
		
		try {
			file.renameTo(new File(DomainContext.getInputDirectory() + "/processed/" + file.getName()));
			return true;
		} catch (Exception e) {
			Logger.logException(file.getName(), e);
			return false;
		}
	}
}

