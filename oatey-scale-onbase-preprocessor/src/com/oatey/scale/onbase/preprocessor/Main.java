package com.oatey.scale.onbase.preprocessor;

import java.util.Map;

import com.oatey.scale.onbase.preprocessor.domain.DomainContext;
import com.oatey.scale.onbase.preprocessor.domain.Processor;

public class Main {
	
	private MainHelper helper;
	private MainHelper getHelper() {
		if(helper == null)
			helper = new MainHelper();
		
		return helper;
	}
	
	private void run() {
		Processor processor = DomainContext.getProcessor();
		while(true) {
			
			try {
				processor.run();
			} catch (Exception e) {
				
			}
			sleep();
		}
	}
	
	private void sleep() {
		try { Thread.sleep(DomainContext.getSleepInterval()); } catch (Exception e) {};
	}
	
	private void loadParameters(String[] args) throws Exception {
		Map <String,String> map = getHelper().parseArgs(args);
		
		getHelper().validate(map);
		
		getHelper().loadParametersToDomainContext(map);
	}
	
	private void showUsage() {
		getHelper().showUsage();
	}

	public static void main(String[] args) {
		Main main = new Main();
		
		try {
			main.loadParameters(args);
			main.run();
		} catch (Exception e) {
			main.showUsage();
		}

	}

}
