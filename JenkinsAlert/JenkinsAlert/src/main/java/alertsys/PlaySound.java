package alertsys;

import javax.media.*;

import java.io.*;

public class PlaySound {
		
	public static void play(String file_loc) throws Exception{
		
		//path of the audio file to play
		File f=new File(file_loc);
	
		// Create a Player object that realizes the audio
		final Player p=Manager.createRealizedPlayer(f.toURI().toURL());

		// plays the sound waits 10 seconds and then restarts the program loop
		
		p.start();
	
		Thread.sleep(InitApp.INTERVAL_CHECK_FOR_JOBS);		  		

		ReadJenkins.findCurrentJob();
	}
	
}
