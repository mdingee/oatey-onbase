package com.oatey.scale.onbase.preprocessor.domain.input;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oatey.scale.onbase.preprocessor.domain.DomainContext;
import com.oatey.scale.onbase.preprocessor.domain.Logger;
import com.oatey.scale.onbase.preprocessor.domain.util.MD5Checksum;

public class InputRepository {
	private Map <String,String> map;
	
	public boolean hasFileChanged(File file) {
		boolean result = false;
		String fileName = file.getName();
		
		if(map == null)
			map = new HashMap<String,String>();
		
		try {
			String currentChecksum = MD5Checksum.getMD5Checksum(file);
			String previousChecksum = map.get(fileName);
			
			result = previousChecksum == null || !previousChecksum.equals(currentChecksum);
			
			if(result) {
				map.remove(fileName);
				map.put(fileName, currentChecksum);
			}
		} catch (Exception e) {
			Logger.logException(fileName, e);
		}
		
		return result;
	}
	
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
		Logger.logStep(null, "Unprocessed files = " + listToString(listOfFileNames));
		
		return listOfFileNames;
	}
	
	private String listToString(String[] list) {
		if(list == null || list.length == 0)
			return "[]";
		
		StringBuilder s = null;
		
		for(String string : list) {
			if(s == null)
				s = new StringBuilder();
			else
				s.append(",");
			
			s.append(string);
		}
		
		return "[" + s.toString() + "]";
		
	}
	
	public File getUnprocessedFile(String fileName) {
		Logger.logStep(fileName, "Retrieving file");
		return new File(DomainContext.getInputDirectory() + "/unprocessed/" + fileName);
	}
	
	public boolean moveToProcessedFolder(File file) {
		Logger.logStep(file.getName(), "Moving file to processed folder");
		
		try {
			String newFileName = DomainContext.getInputDirectory() + "/processed/" + file.getName();
			File newFile = new File(newFileName);
			if(newFile.exists())
				newFile.delete();
			file.renameTo(newFile);
			map.remove(file.getName());
			return true;
		} catch (Exception e) {
			Logger.logException(file.getName(), e);
			return false;
		}
	}
}

