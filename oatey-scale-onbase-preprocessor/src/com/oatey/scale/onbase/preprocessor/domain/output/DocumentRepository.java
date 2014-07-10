package com.oatey.scale.onbase.preprocessor.domain.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.channels.FileChannel;

import com.oatey.scale.onbase.preprocessor.domain.DomainContext;
import com.oatey.scale.onbase.preprocessor.domain.Logger;

public class DocumentRepository {
	public boolean copyToUnprocessedFolder(File sourceFile) throws Exception {
		String outputFolder = DomainContext.getOutputDirectory() + "/unprocessed/";
		
		Logger.logStep(sourceFile.getName(), "Copying file to " + outputFolder + " folder");
		File destFile = new File(outputFolder + sourceFile.getName());
		
		if(destFile.exists()) {
			destFile.delete();
	    } 
	    
		destFile.createNewFile();
	   
	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	        return true;
	    } catch (Exception e) {
	    	Logger.logException(sourceFile.getName(), e);
	    	return false;
	    } finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
	
	public boolean writeDocument(Document document) {
		Logger.logStep(document.getFileName(), "Writing meta data to DIP file");
		String dipFileName = DomainContext.getOutputDirectory() + "/unprocessed/Oatey.txt";
		FileWriter fw = null;
		
		try {
		
			File dipFile = new File(dipFileName);
		
			if(!dipFile.exists()) {
				dipFile.createNewFile();
			}
			
			dipFile = null;
			
			fw = new FileWriter(dipFileName,true); 
		    fw.write(document.toString());
		    fw.close();
		
		} catch (Exception e) {
			
		} 
		
		return false;
	}
}
