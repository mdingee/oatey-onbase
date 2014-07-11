package com.oatey.scale.onbase.preprocessor.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DomainContext {
	
	private String inputDirectory;
	private String outputDirectory;
	private long sleepInterval;
	private int logLevel;

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

	public static int getLogLevel() {
		return getInstance().logLevel;
	}

	public static void setLogLevel(int logLevel) {
		getInstance().logLevel = logLevel;
	}
}
