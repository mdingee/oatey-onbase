package com.oatey.scale.onbase.preprocessor.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DomainContext {
	
	private String inputDirectory;
	private String outputDirectory;
	private long sleepInterval;
	private String adminEmailAddress;
	private int logLevel;
	private String logFile;

	private ApplicationContext context;
	private DomainContext() {
		context= new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext.xml");
	}
	
	private ApplicationContext getContext() {
		return context;
	}
	
	private static DomainContext getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder {
		public static final DomainContext INSTANCE = new DomainContext();
	}
	
	public static Processor getProcessor() {
		return (Processor) getInstance().getContext().getBean("ref_Processor");
	}
	
	public static String getInputDirectory() {
		return getInstance().inputDirectory;
	}

	public static void setInputDirectory(String inputDirectory) {
		getInstance().inputDirectory = inputDirectory;
	}

	public static String getOutputDirectory() {
		return getInstance().outputDirectory;
	}

	public static void setOutputDirectory(String outputDirectory) {
		getInstance().outputDirectory = outputDirectory;
	}

	public static long getSleepInterval() {
		return getInstance().sleepInterval;
	}

	public static void setSleepInterval(long sleepInterval) {
		getInstance().sleepInterval = sleepInterval;
	}

	public static String getAdminEmailAddress() {
		return getInstance().adminEmailAddress;
	}

	public static void setAdminEmailAddress(String adminEmailAddress) {
		getInstance().adminEmailAddress = adminEmailAddress;
	}

	public static int getLogLevel() {
		return getInstance().logLevel;
	}

	public static void setLogLevel(int logLevel) {
		getInstance().logLevel = logLevel;
	}
	
	public static String getLogFile() {
		return getInstance().logFile;
	}

	public static void setLogFile(String logFile) {
		getInstance().logFile = logFile;
	}
}
