package alertsys;

import gui.AlertGui;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @mdereje
 */
public class ReadJenkins {
	
	public static Boolean BUILD_RUNNING;	
	public static String CONSOLE_URL;
	public static String CONSOLE_OUTPUT;
	
    public static void consoleOutput() throws IOException{
    	
    	WriteFile write_to = new WriteFile(InitApp.ERROR_LOG_PATH, true);
    	java.util.Date date = new java.util.Date();    	
    	
    	try{
    		java.util.Date date1 = new java.util.Date();	
    		/*
    		 * Initialize Jenkins scraping elements!
    		 * */
    		
    		//Moniotrs the selected item from the drop down jobs menu
    		CONSOLE_URL = InitApp.JENKINS_BASE_URL + AlertGui.JOBS_IN_PROGRESS.getSelectedItem();			
    		
    		Document doc = Jsoup.connect(CONSOLE_URL).get();   		
    		Elements anchors = doc.select("pre");  		    		
    		String tempout = anchors.text();    		    	          	        	
    		    		
    		InitApp.VISUAL_DISPLAY.writeToConsole("[Info!] Console page link: '"+ CONSOLE_URL +"'");
    		
    		if(tempout.equals("")){
    			Thread.sleep(InitApp.INTERVAL_CHECK_FOR_JOB_STATUS);
    			InitApp.VISUAL_DISPLAY.writeToConsole("[Info!] Job is in progress - " + new Timestamp(date1.getTime()));     			
    			consoleOutput(); //calls itself if test is not complete
    			
    		}else{
    			
    			CONSOLE_OUTPUT = tempout;
    			
    			AnalyzeOutput.ISFINISHED = true; 
    			AnalyzeOutput.PostBuildOutput(CONSOLE_OUTPUT);
    		}    		    		

    	} catch (IllegalArgumentException ex){
    		//Logger.getLogger(ReadJenkins.class.getName()).log(Level.WARNING, "Job console page refused connection!", ex);    		    		
    		write_to.writeToFile(new Timestamp(date.getTime()) + " - [Warning!] Job console page refused connection!");
    		
    	} catch (InterruptedException e) {			    	
    		write_to.writeToFile(new Timestamp(date.getTime()) + " - [Warning!] InterruptedException has occured in consoleOutput()!");
		} catch (Exception s) {			
			write_to.writeToFile(new Timestamp(date.getTime()) + " - [Warning!] Connection to Job Console has timed out!");
		}    	    	
    	consoleOutput();
    }
    
 // Finds the link of the current build
    public static void findCurrentJob(){
    	
    	String tempLink = "";
    	ArrayList<String> full_list = new ArrayList<String>();
    	
    	try{
    		Document doc = Jsoup.connect(InitApp.JENKINS_BASE_URL).get();
    		Elements consoleLinks = doc.select("table").select("div").select("table");    		
    		    		
    		
    		for(Element consoleLink : consoleLinks){    			    			   			
    			
    			tempLink = consoleLink.attr("href");
    			    			    			
    			if(!tempLink.equalsIgnoreCase("")){
    				   
    				//Output console for selected job.
    				//CONSOLE_URL = InitApp.JENKINS_BASE_URL + AlertGui.JOBS_IN_PROGRESS.getSelectedItem(); 
    				//BUILD_RUNNING = true; 			
    				//consoleOutput();
    				if(full_list.isEmpty()){
    					full_list.add(tempLink);
    				}
    				if(!full_list.contains(tempLink)){
    					full_list.add(tempLink);
    				}    				
    			}    			
    		}
    		System.out.println(AlertGui.JOBS_IN_PROGRESS.getItemCount()+" and "+ full_list.size());
    		for(String single_list_item : full_list){
    			if(AlertGui.JOBS_IN_PROGRESS.getItemCount() != full_list.size()){
    				AlertGui.JOBS_IN_PROGRESS.addItem(single_list_item);     				
    			}    			   		
    		}    		    		
    		    		
    		
    		if(tempLink.equals(full_list.isEmpty())){
    			InitApp.VISUAL_DISPLAY.writeToConsole("[Info!] No jobs running currently, will check again in " + (InitApp.INTERVAL_CHECK_FOR_JOBS / 1000 )+ " seconds!");    			
    			Thread.sleep(InitApp.INTERVAL_CHECK_FOR_JOBS);    			
    			findCurrentJob();
    		} else {    			
    			BUILD_RUNNING = true; 
    			consoleOutput();
    		}
    		 
    	} catch (IOException ex){
    		InitApp.VISUAL_DISPLAY.writeToConsole("[Warning!] Jenkins Server refused connection with the app, will try again " + (InitApp.INTERVAL_CHECK_FOR_JOBS / 1000 )+ " seconds!");
    	} catch (InterruptedException e) {
    		InitApp.VISUAL_DISPLAY.writeToConsole("[Warning!] Sleep function not working!");
		} catch (Exception s) {	
			InitApp.VISUAL_DISPLAY.writeToConsole("[Warning!] Jenkins Server socket exception!");
		}   	
    	try {    		
			Thread.sleep(InitApp.INTERVAL_CHECK_FOR_JOBS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
    	findCurrentJob();
    }
}