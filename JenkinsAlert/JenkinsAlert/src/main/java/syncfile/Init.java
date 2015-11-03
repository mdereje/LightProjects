package syncfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.jcraft.jsch.SftpException;

/**
 * This program should check for a change periodically and send things automatically to the controller. 
 *
 */
public class Init 
{
	public static String FILE_TO_UPDATE = "";
	
    public static void InitRemote() throws Exception{
    	 System.out.println( "Perodic synchronizer is running!");
         
         String DATA_PATH = "C:\\Users\\michader\\Desktop\\sch-perfeng\\Product_Scripts\\SSO\\SSO_v2\\SSO_multi\\data\\";
         String JMX_PATH = "C:\\Users\\michader\\Desktop\\sch-perfeng\\Product_Scripts\\SSO\\SSO_v2\\SSO_multi\\";        
         String TEMP_FILE_PATH;
         

         File data_file = new File(DATA_PATH);        
         File jmx_file = new File(JMX_PATH);
         
         ArrayList<String> jmx_names = new ArrayList<String>(Arrays.asList(jmx_file.list()));
         ArrayList<String> data_names = new ArrayList<String>(Arrays.asList(data_file.list()));
         ArrayList<String> combined_path_list = new ArrayList<String>();
         
         jmx_names.remove("data");

         for (int i = 0; i < jmx_names.size(); i++ ){
         	TEMP_FILE_PATH = JMX_PATH + jmx_names.get(i);
         	combined_path_list.add(TEMP_FILE_PATH);
         	
         	//look for specified files
         	if(jmx_names.get(i).equals("CHANGELOG.txt")){
         		FILE_TO_UPDATE = JMX_PATH + jmx_names.get(i);        		
         	}
         }    
         
         for (int i = 0; i < data_names.size(); i++ ){
         	TEMP_FILE_PATH = DATA_PATH + data_names.get(i);
         	combined_path_list.add(TEMP_FILE_PATH);
         }
         
         for (int i = 0; i < combined_path_list.size(); i++ ){        	
         	FileInfo temp_file = new FileInfo(combined_path_list.get(i));
         	
         	System.out.println(temp_file.CURRENT_FILE);
         	System.out.println(new Date(temp_file.FILE_LAST_MODIFIED));
         	System.out.println(temp_file.FILE_LENGTH);
         }  
//         ManageRemoteFile sso_multi = new ManageRemoteFile("172.17.35.245", 22, "root", "Welcome1", "/root/apache-jmeter-2.12/workspace/SSO_multi");        
// 		 sso_multi.PrintRemoteDirectory();
//         System.out.println(FILE_TO_UPDATE);
//         ManageRemoteFile.UpdateRemoteFile(FILE_TO_UPDATE);  
    }
}
