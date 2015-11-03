package alertsys;

import gui.AlertGui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
/*
 * Checks the operating system. Initializes directories and file locations!
 * */
public class InitApp {
	
	public static String ERROR_LOG_PATH;
	public static String MEDIA_PATH_LOCATION;
	public static String SYSTEM_OS;
	public static AlertGui VISUAL_DISPLAY;
	public static String JENKINS_BASE_URL = "http://172.17.35.165:8080";
	public static long INTERVAL_CHECK_FOR_JOBS = 10000;
	public static long INTERVAL_CHECK_FOR_JOB_STATUS = 5000;
	
	public static void initGui(){
		VISUAL_DISPLAY = new AlertGui();
	}
	
	public static void writeWelcome() throws UnsupportedEncodingException{

		
		String path = Run.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    	String parentPath = (new File(path)).getParentFile().getPath();
    	String PARENT_JAR_FILE_LOCATION = URLDecoder.decode(parentPath, "UTF-8");
    	
        	
    	initGui();
    	
    	SYSTEM_OS = System.getProperty("os.name");
    	
    	if(SYSTEM_OS.substring(0, 1).equals("W")){
    		MEDIA_PATH_LOCATION = PARENT_JAR_FILE_LOCATION + "\\resources\\";       	
        	ERROR_LOG_PATH = PARENT_JAR_FILE_LOCATION + "\\logs\\errors.txt";
        	File theFile = new File(PARENT_JAR_FILE_LOCATION + "\\logs\\");
        	theFile.mkdirs();
    	}else{
    		MEDIA_PATH_LOCATION = PARENT_JAR_FILE_LOCATION + "/resources/";       	
        	ERROR_LOG_PATH = PARENT_JAR_FILE_LOCATION + "/logs/errors.txt";
        	File theFile = new File(PARENT_JAR_FILE_LOCATION + "/logs/");
        	theFile.mkdirs();
    	}    	    
    	    	
    	VISUAL_DISPLAY.writeToConsole("*********************************************************************************************" );
    	VISUAL_DISPLAY.writeToConsole("System OS: '"+SYSTEM_OS+"'");
    	VISUAL_DISPLAY.writeToConsole("*********************************************************************************************" );    	
    	VISUAL_DISPLAY.writeToConsole("This program only reads sound files with a '.wav' extension. "
    			+ "Make sure to properly convert the media type to a .wav sound file.");
    	VISUAL_DISPLAY.writeToConsole("Free online audio conversion tools such as 'http://audio.online-convert.com/convert-to-wav'.");
    	VISUAL_DISPLAY.writeToConsole("Media files are mapped as follows:");
    	VISUAL_DISPLAY.writeToConsole("SUCCESS: win.wav");
    	VISUAL_DISPLAY.writeToConsole("FAILURE: fail.wav");
    	VISUAL_DISPLAY.writeToConsole("ABORT: abort.wav");
    	VISUAL_DISPLAY.writeToConsole("Using file location: '" +MEDIA_PATH_LOCATION+"' as media location" );
    	VISUAL_DISPLAY.writeToConsole("Errors are logged at: '" +ERROR_LOG_PATH+"'" );
    	VISUAL_DISPLAY.writeToConsole("*********************************************************************************************" );
    	
    	//System.getProperties().list(System.out);   
	}
}
