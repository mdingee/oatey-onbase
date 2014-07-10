package com.oatey.scale.onbase.preprocessor;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MainHelperUTest {

	private static final String PARAM_INPUT = "-input";
	private static final String PARAM_OUTPUT = "-output";
	private static final String PARAM_SLEEP = "-sleep";
	private static final String PARAM_EMAIL = "-email";
	private static final String PARAM_LOG_LEVEL = "-logLevel";
	private static final String PARAM_LOG_FILE = "-logFile";
	
	private static final String PARAM_INPUT_VALUE = "INPUT_VALUE";
	private static final String PARAM_OUTPUT_VALUE = "OUTPUT_VALUE";
	
	private static final long PARAM_SLEEP_VALID_VALUE = 1L;
	private static final long PARAM_SLEEP_INVALID_VALUE_NEG = -100L;
	private static final String PARAM_SLEEP_INVALID_VALUE_NAN = "JUNK";
	
	private static final String PARAM_EMAIL_VALUE = "email@email.com";
	
	private static final int PARAM_LOG_LEVEL_VALID_VALUE    = 0;
	private static final int PARAM_LOG_LEVEL_VALID_VALUE1   = 1;
	private static final int PARAM_LOG_LEVEL_VALID_VALUE2   = 2;
	private static final int PARAM_LOG_LEVEL_VALID_VALUE3   = 3;
	private static final int PARAM_LOG_LEVEL_INVALID_VALUE4 = 4;
	private static final String PARAM_LOG_LEVEL_INVALID_VALUE_NAN = "JUNK";
	
	private static final String PARAM_LOG_FILE_VALUE = "LOGFILE_VALUE";
	
	private MainHelper mainHelper;
	
	@Before
	public void setup() {
		mainHelper = new MainHelper();
	}
	
	@Test
	public void testArgumentsAreValue() {
		String[] validArguments = getValidArguments();
		
		try {
			Map <String, String> map = mainHelper.parseArgs(validArguments);
			mainHelper.validate(map);
			
			String inputDirectory = mainHelper.getInputDirectory(map);
			String outputDirectory = mainHelper.getOutputDirectory(map);
			String adminEmailAddress = mainHelper.getAdminEmailAddress(map);
			String logFile = mainHelper.getLogFile(map);
			long sleep = mainHelper.getSleepInterval(map);
			int logLevel = mainHelper.getLogLevel(map);
			
			assertEquals(PARAM_INPUT_VALUE, inputDirectory);
			assertEquals(PARAM_OUTPUT_VALUE, outputDirectory);
			assertEquals(PARAM_EMAIL_VALUE, adminEmailAddress);
			assertEquals(PARAM_LOG_FILE_VALUE, logFile);
			assertEquals(PARAM_SLEEP_VALID_VALUE, sleep);
			assertEquals(PARAM_LOG_LEVEL_VALID_VALUE, logLevel);
		
		} catch (Exception e) {
			fail("These parameters should have parsed correctly");
		}
	}
	
	@Test
	public void testLogLevelsBetween0And3() {
		String[] arguments;
		Map <String, String> map;
		
		try {
			arguments = getValidArguments_LogLevel0();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
			
			arguments = getValidArguments_LogLevel1();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
			
			arguments = getValidArguments_LogLevel2();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
			
			arguments = getValidArguments_LogLevel3();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
		
		} catch (Exception e) {
			fail("These parameters should have parsed correctly");
		}
		
		try {
			arguments = getInvalidArguments_LogLevel4();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
			
			fail("Log level = 4 should have thrown an Exception");
		} catch (Exception e) {
			
		}
		
		try {
			arguments = getInvalidArguments_LogLevel_NaN();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
			
			fail("Non-numeric Log level should have thrown an Exception");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testSleepInterval_PositiveLongValue() {
		String[] arguments;
		Map <String, String> map;
		
		try {
			arguments = getValidArguments_SleepInterval_GT0();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
		} catch (Exception e) {
			fail("Positive sleep interval should not have thrown an exception");
		}
		
		try {
			arguments = getInvalidArguments_SleepInterval_LT0();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
			
			fail("Negative sleep interval value should have thrown an exception");
			
			arguments = getInvalidArguments_SleepInterval_NaN();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
			
			fail("Non-numeric sleep interval value should have thrown an exception");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testAllParametersAreRequired() {
		String[] arguments;
		Map <String, String> map;
		
		try {
			arguments = getInvalidArguments_MissingRequiredField();
			map = mainHelper.parseArgs(arguments);
			mainHelper.validate(map);
			
			fail("Missing required field should have thrown an exception");
		} catch (Exception e) {
			
		}
	}
	
	
	private String[] getValidArguments() {
		return new String[] {PARAM_INPUT + "=" + PARAM_INPUT_VALUE
				            ,PARAM_OUTPUT + "=" + PARAM_OUTPUT_VALUE
				            ,PARAM_SLEEP + "=" + PARAM_SLEEP_VALID_VALUE
				            ,PARAM_EMAIL + "=" + PARAM_EMAIL_VALUE
				            ,PARAM_LOG_LEVEL + "=" + PARAM_LOG_LEVEL_VALID_VALUE
				            ,PARAM_LOG_FILE + "=" + PARAM_LOG_FILE_VALUE};
		
	}
	
	private String[] getValidArguments_LogLevel0() {
		return getValidArguments();
		
	}
	
	private String[] getValidArguments_LogLevel1() {
		return new String[] {PARAM_INPUT + "=" + PARAM_INPUT_VALUE
				            ,PARAM_OUTPUT + "=" + PARAM_OUTPUT_VALUE
				            ,PARAM_SLEEP + "=" + PARAM_SLEEP_VALID_VALUE
				            ,PARAM_EMAIL + "=" + PARAM_EMAIL_VALUE
				            ,PARAM_LOG_LEVEL + "=" + PARAM_LOG_LEVEL_VALID_VALUE1
				            ,PARAM_LOG_FILE + "=" + PARAM_LOG_FILE_VALUE};
		
	}
	
	private String[] getValidArguments_LogLevel2() {
		return new String[] {PARAM_INPUT + "=" + PARAM_INPUT_VALUE
				            ,PARAM_OUTPUT + "=" + PARAM_OUTPUT_VALUE
				            ,PARAM_SLEEP + "=" + PARAM_SLEEP_VALID_VALUE
				            ,PARAM_EMAIL + "=" + PARAM_EMAIL_VALUE
				            ,PARAM_LOG_LEVEL + "=" + PARAM_LOG_LEVEL_VALID_VALUE2
				            ,PARAM_LOG_FILE + "=" + PARAM_LOG_FILE_VALUE};
		
	}
	private String[] getValidArguments_LogLevel3() {
		return new String[] {PARAM_INPUT + "=" + PARAM_INPUT_VALUE
				            ,PARAM_OUTPUT + "=" + PARAM_OUTPUT_VALUE
				            ,PARAM_SLEEP + "=" + PARAM_SLEEP_VALID_VALUE
				            ,PARAM_EMAIL + "=" + PARAM_EMAIL_VALUE
				            ,PARAM_LOG_LEVEL + "=" + PARAM_LOG_LEVEL_VALID_VALUE3
				            ,PARAM_LOG_FILE + "=" + PARAM_LOG_FILE_VALUE};
		
	}
	private String[] getInvalidArguments_LogLevel4() {
		return new String[] {PARAM_INPUT + "=" + PARAM_INPUT_VALUE
				            ,PARAM_OUTPUT + "=" + PARAM_OUTPUT_VALUE
				            ,PARAM_SLEEP + "=" + PARAM_SLEEP_VALID_VALUE
				            ,PARAM_EMAIL + "=" + PARAM_EMAIL_VALUE
				            ,PARAM_LOG_LEVEL + "=" + PARAM_LOG_LEVEL_INVALID_VALUE4
				            ,PARAM_LOG_FILE + "=" + PARAM_LOG_FILE_VALUE};
		
	}
	
	private String[] getInvalidArguments_LogLevel_NaN() {
		return new String[] {PARAM_INPUT + "=" + PARAM_INPUT_VALUE
				            ,PARAM_OUTPUT + "=" + PARAM_OUTPUT_VALUE
				            ,PARAM_SLEEP + "=" + PARAM_SLEEP_VALID_VALUE
				            ,PARAM_EMAIL + "=" + PARAM_EMAIL_VALUE
				            ,PARAM_LOG_LEVEL + "=" + PARAM_LOG_LEVEL_INVALID_VALUE_NAN
				            ,PARAM_LOG_FILE + "=" + PARAM_LOG_FILE_VALUE};
		
	}
	
	private String[] getValidArguments_SleepInterval_GT0() {
		return getValidArguments();
		
	}
	
	private String[] getInvalidArguments_SleepInterval_LT0() {
		return new String[] {PARAM_INPUT + "=" + PARAM_INPUT_VALUE
	            ,PARAM_OUTPUT + "=" + PARAM_OUTPUT_VALUE
	            ,PARAM_SLEEP + "=" + PARAM_SLEEP_INVALID_VALUE_NEG
	            ,PARAM_EMAIL + "=" + PARAM_EMAIL_VALUE
	            ,PARAM_LOG_LEVEL + "=" + PARAM_LOG_LEVEL_VALID_VALUE
	            ,PARAM_LOG_FILE + "=" + PARAM_LOG_FILE_VALUE};
	}
	
	private String[] getInvalidArguments_SleepInterval_NaN() {
		return new String[] {PARAM_INPUT + "=" + PARAM_INPUT_VALUE
	            ,PARAM_OUTPUT + "=" + PARAM_OUTPUT_VALUE
	            ,PARAM_SLEEP + "=" + PARAM_SLEEP_INVALID_VALUE_NAN
	            ,PARAM_EMAIL + "=" + PARAM_EMAIL_VALUE
	            ,PARAM_LOG_LEVEL + "=" + PARAM_LOG_LEVEL_VALID_VALUE
	            ,PARAM_LOG_FILE + "=" + PARAM_LOG_FILE_VALUE};
	}
	
	private String[] getInvalidArguments_MissingRequiredField() {
		return new String[] {PARAM_INPUT + "=" + PARAM_INPUT_VALUE
				          //  ,PARAM_OUTPUT + "=" + PARAM_OUTPUT_VALUE
				            ,PARAM_SLEEP + "=" + PARAM_SLEEP_VALID_VALUE
				            ,PARAM_EMAIL + "=" + PARAM_EMAIL_VALUE
				            ,PARAM_LOG_LEVEL + "=" + PARAM_LOG_LEVEL_VALID_VALUE
				            ,PARAM_LOG_FILE + "=" + PARAM_LOG_FILE_VALUE};
		
	}

}
