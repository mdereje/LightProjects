package syncfile;
/*
 * This class is constructed using an example from 
 * http://www.codeproject.com/Questions/172608/Getting-file-details-using-java
 *
 * */

import java.io.File;

public class FileInfo {
	
	public File CURRENT_FILE;
	
	public long FILE_LENGTH;
	public long FILE_LAST_MODIFIED;
	
	
	public FileInfo(String file_name){
		CURRENT_FILE = new File(file_name);
		FILE_LENGTH = CURRENT_FILE.length();
		FILE_LAST_MODIFIED = CURRENT_FILE.lastModified();
		
	}
}
