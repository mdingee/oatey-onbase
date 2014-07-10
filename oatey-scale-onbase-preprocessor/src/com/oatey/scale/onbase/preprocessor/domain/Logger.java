package com.oatey.scale.onbase.preprocessor.domain;

import java.util.Date;

public class Logger {
	public static void logException(String fileName, Exception exception) {
		if(DomainContext.getLogLevel() < 1)
			return;
		
		System.out.println(new Date() + ": EXCEPTION: " + fileName);
		exception.printStackTrace();
	}
	
	public static void logFailure(String fileName, String failureDescription) {
		if(DomainContext.getLogLevel() < 1)
			return;
		
		System.out.println(new Date() + ": FAILURE: " + fileName + ": " + failureDescription);
	}
	
	public static void logSuccess(String fileName) {
		if(DomainContext.getLogLevel() < 2)
			return;
		
		System.out.println(new Date() + ": SUCCESS: " + fileName);
	}
	
	public static void logStep(String fileName, String stepDescription) {
		if(DomainContext.getLogLevel() < 3)
			return;
		
		System.out.println(new Date() + ": STEP: " + (fileName == null ? "" : fileName + ": ") + stepDescription);
	}
}
