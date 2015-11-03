/**
 * @Michael Dereje
 */
package alertsys;

import gui.TrayControl;

public class AnalyzeOutput {

	public static boolean ISFINISHED;
	public static boolean ISSUCCESS;
	public static boolean ISABORTED;
	
	public static String FINAL_RESULT;
	
	public static void PostBuildOutput(String console){
		
		String temp_file_location = "";
		if(ISFINISHED == true){
			
			String lastLine = console.substring(console.length() - 17, console.length());
			String result = lastLine.substring(lastLine.length() - 7, lastLine.length());
			//Deprecated because, a test result finishing is dectated by the return of data from the console (ReadJenkins.java)
			//String finished = lastLine.substring(lastLine.length() - 17, lastLine.length()- 9); 			
							
			if(result.endsWith("SUCCESS")){
				ISSUCCESS = true;
				temp_file_location = InitApp.MEDIA_PATH_LOCATION + "win.wav"; 
				ISFINISHED = false;
				InitApp.VISUAL_DISPLAY.writeToConsole("[Info!] The build has finished successfully!");
			}
			if(result.endsWith("ABORTED")){
				ISABORTED = true;
				ISFINISHED = false;
				temp_file_location = InitApp.MEDIA_PATH_LOCATION + "abort.wav";				
				InitApp.VISUAL_DISPLAY.writeToConsole("[Info!] The build was aborted!");
			}
			if(result.endsWith("FAILURE")){
				ISSUCCESS = false;
				ISABORTED = false;
				ISFINISHED = false;				
				temp_file_location = InitApp.MEDIA_PATH_LOCATION + "fail.wav";	
				InitApp.VISUAL_DISPLAY.writeToConsole("[Info!] The build has failed!");
			}			
			
			FINAL_RESULT = lastLine;
			InitApp.VISUAL_DISPLAY.writeToConsole("Atempting to play file at location: '"+temp_file_location+ "'");
			
			try {
				if(TrayControl.PLAY_SOUND == true){		//checks if the play sound flag is set to true
					PlaySound.play(temp_file_location);
				}
			} catch (Exception p) {
				// TODO Auto-generated catch block
				InitApp.VISUAL_DISPLAY.writeToConsole("[Warning!] Was not able to find sound file!");
			}					
						
		}
	}		

	public boolean isFinished(){
		return ISFINISHED;
	}
	
	public boolean isSuccess(){
		return ISSUCCESS;
	}
		
}
