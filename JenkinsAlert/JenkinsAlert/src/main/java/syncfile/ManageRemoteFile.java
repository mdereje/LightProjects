package syncfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;
 



import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class ManageRemoteFile {

    static String CONTROLLER_HOST;
    static int    CONTROLLER_PORT;
    static String CONTROLLER_USER;
    static String CONTROLLER_PASS;
    static String CONTROLLER_DIR;
    static Session     session     = null;
    static Channel     channel     = null;
    static ChannelSftp channelSftp = null;
    static JSch jsch;
    
    
    public ManageRemoteFile(String hostip, int port, String username, String password, String directory) throws Exception{
    	CONTROLLER_HOST = hostip;
    	CONTROLLER_PORT = port;
    	CONTROLLER_USER = username;
    	CONTROLLER_PASS = password;
    	CONTROLLER_DIR = directory;
    	jsch = new JSch();
        
    
        	
        	session = jsch.getSession(username,hostip,port);
            session.setPassword(password);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");            
//            System.out.println("Disabling host key checking...");            
            session.setConfig(config);
//            System.out.println("Changing config properties...");
            session.connect();
//            System.out.println("Setting configurations...");            
//            System.out.println(hostip + ", " + port +", "+ username +", "+ password +", "+ directory);            
        	channel = session.openChannel("sftp");        	
//        	System.out.println("Opening channel...");        	
            channel.connect();            
//            System.out.println("Connecting to channel...");            
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(CONTROLLER_DIR);					
              
    }
    
    public void PrintRemoteDirectory() throws SftpException{
    	Vector filelist = channelSftp.ls(CONTROLLER_DIR);        
//    	System.out.println("----------------------------Remote directory information----------------------------");
         for(int i=0; i<filelist.size();i++){
//             System.out.println(filelist.get(i).toString());
         }
    }
    
    public static void UpdateRemoteFile(String local_dir) throws FileNotFoundException, SftpException{
        File f = new File(local_dir);
        channelSftp.put(new FileInputStream(f), f.getName());
    }
}
