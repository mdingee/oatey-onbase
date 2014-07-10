package com.oatey.scale.onbase.preprocessor;

import java.util.HashMap;
import java.util.Map;

import com.oatey.scale.onbase.preprocessor.domain.DomainContext;

public class MainHelper {
	
	private static final String PARAM_INPUT = "-input";
	private static final String PARAM_OUTPUT = "-output";
	private static final String PARAM_SLEEP = "-sleep";
	private static final String PARAM_EMAIL = "-email";
	private static final String PARAM_LOG_LEVEL = "-logLevel";
	private static final String PARAM_LOG_FILE = "-logFile";
	
	public Map<String,String> parseArgs(String[] args) throws Exception {
		Map <String,String> map = new HashMap <String, String> ();
		
		for(String arg : args) {
			String[] splitArgs = arg.split("=");
			
			if(splitArgs.length != 2)
				throwException();
			
			String key = splitArgs[0];
			String value = splitArgs[1];
			
			map.put(key, value);
		}
		
		long sleep = getSleepInterval(map);
		if(sleep < 0)
			throwException("-sleep parameter must be greater than or equal to 0");

		int logLevel = getLogLevel(map);
		if(logLevel < 0 || logLevel > 3)
			throwException("-logLevel parameter must be integer between 0 and 3 inclusive");
		
		return map;
	}
	
	public void validate(Map <String,String> map) throws Exception {
		if(map == null)
			throwException();
		
		String[] requiredArgs = {PARAM_INPUT, PARAM_OUTPUT, PARAM_SLEEP, PARAM_EMAIL, PARAM_LOG_LEVEL, PARAM_LOG_FILE};
		
		for(String requiredArg : requiredArgs) {
			if(!map.containsKey(requiredArg))
				throwException();
		}
	}
	
	private void throwException(String string) throws Exception {
		throw new Exception(string);
	}
	
	private void throwException() throws Exception {
		throwException("Invalid parameters");
	}
	
	public void showUsage() {
		System.err.println("Usage:");
		System.err.println("   -input=value - Root directory for input files");
		System.err.println("   -output=value - Root directory for output files");
		System.err.println("   -sleep=value - Time in milliseconds to sleep between iterations");
		System.err.println("   -email=value - Email address for administrator");
		System.err.println("   -logLevel=value");
		System.err.println("        0 = Exceptions only"); 
		System.err.println("        1 = Failures only");
		System.err.println("        2 = Successes and failures");
		System.err.println("        3 = Fully verbose (shows each step)");
		System.err.println("   -logFile=value - Path to output log file");
		System.err.println("Example:");
		System.err.println("   java com.oatey.scale.onbase.preprocessor.Main -input=\"c:/onbase/input\" -output=\"c:/onbase/output\" -sleep=1000 -email=\"test@testemail.com\" -logLevel=1 -logFile=\"c:/temp/onbase/log.txt\"");

	}
	
	public String getInputDirectory(Map <String,String> map) {
		return map.get(PARAM_INPUT);
	}
	
	public String getOutputDirectory(Map <String,String> map) {
		return map.get(PARAM_OUTPUT);
	}
	
	public long getSleepInterval(Map <String,String> map) {
		
		try {
			String value = map.get(PARAM_SLEEP);
			return new Long(value);
		} catch (Exception e) {
			
		}
		return -1L;
	}
	
	public String getAdminEmailAddress(Map <String,String> map) {
		return map.get(PARAM_EMAIL);
	}
	
	public int getLogLevel(Map <String,String> map) {
		try {
			String value = map.get(PARAM_LOG_LEVEL);
			return new Integer(value);
		} catch (Exception e) {
			
		}
		return -1;
	}
	
	public String getLogFile(Map <String,String> map) {
		return map.get(PARAM_LOG_FILE);
	}
	
	public void loadParametersToDomainContext(Map <String,String> map) {
		String inputDirectory = getInputDirectory(map);
		String outputDirectory = getOutputDirectory(map);
		long sleepInterval = getSleepInterval(map);
		String adminEmailAddress = getAdminEmailAddress(map);
		int logLevel = getLogLevel(map);
		String logFile = getLogFile(map);
		
		DomainContext.setInputDirectory(inputDirectory);
		DomainContext.setOutputDirectory(outputDirectory);
		DomainContext.setAdminEmailAddress(adminEmailAddress);
		DomainContext.setLogLevel(logLevel);
		DomainContext.setLogFile(logFile);
		DomainContext.setSleepInterval(sleepInterval);
	}
	
}
