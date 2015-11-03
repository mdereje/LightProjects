package gui;
//Imports are listed in full to show what's being used 
//could just import javax.swing.* and java.awt.* etc.. 
import javax.swing.JCheckBox;
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.JComboBox; 
import javax.swing.JButton; 
import javax.swing.JLabel; 
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import syncfile.Init;
import syncfile.ManageRemoteFile;

import java.awt.AWTException;
import java.awt.BorderLayout; 
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TrayIcon;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;  
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;

public class AlertGui {  
	
	public JTextArea consoleArea;
	public JTextField searchField;
	public JCheckBox play_sound;
	public JCheckBox show_popup;
	public JCheckBox open_browser;	
	public static JComboBox<String> JOBS_IN_PROGRESS = new JComboBox<String>();
	public static final JFrame guiFrame =  new JFrame();
	
	//Note: Typically the main method will be in a 
	//separate class. As this is a simple one class 
	//example it's all in the one class. 
	
	public AlertGui() 
	

	{ 
		//guiFrame = new JFrame();  
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		guiFrame.setTitle("Jenkins Alert"); 
		guiFrame.setSize(700,400);  		
		
		//This will center the JFrame in the middle of the screen 
		guiFrame.setLocationRelativeTo(null); 
		
		//Options for the JComboBox  
//		String[] current_tests = {"Apple", "Apricot", "Banana" 
//				,"Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"}; 
		
		
		final JPanel comboPanel = new JPanel(new GridLayout(0, 1));
		JLabel theme_label = new JLabel("Current Tests:"); 		
					
		
		comboPanel.add(theme_label); comboPanel.add(JOBS_IN_PROGRESS); 
		
		
		//mdereje create checkbox option selections! 
		play_sound = new JCheckBox("Play Sound");
		play_sound.setMnemonic(KeyEvent.VK_P);
		play_sound.setEnabled(false); //TODO: implement and set true;
		play_sound.setSelected(true);
		
		show_popup = new JCheckBox("Show Popup");
		show_popup.setMnemonic(KeyEvent.VK_S);
		show_popup.setEnabled(false); //TODO: implement and set true;
		show_popup.setSelected(true);
		
		open_browser = new JCheckBox("Open Jenkins");
		open_browser.setMnemonic(KeyEvent.VK_O);
		open_browser.setEnabled(false); //TODO: implement and set true;
		open_browser.setSelected(true);
		
		comboPanel.add(play_sound);
		comboPanel.add(show_popup);
		comboPanel.add(open_browser);		
			
		

		JButton display_console = new JButton( "Diplay Console"); 		
		JButton sync_modified_file = new JButton( "Sync File");
		
		
		//
		final JPanel consolePanal = new JPanel(new GridBagLayout());
		
		searchField = new JTextField(20);
		searchField.setEditable(true);
		searchField.setVisible(false);
		
		consoleArea = new JTextArea(5, 50);	
		consoleArea.setEditable(false);
		consoleArea.setVisible(false);
		JScrollPane scrollPane = new JScrollPane(consoleArea);				                              
        
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        consolePanal.add(searchField, c);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        consolePanal.add(scrollPane, c);
                

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
		
		//The ActionListener class is used to handle the 
		//event that happens when the user clicks the button. 
		//As there is not a lot that needs to happen we can  
		//define an anonymous inner class to make the code simpler. 
		display_console.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent event) { 
				consoleArea.setVisible(!consoleArea.isVisible());
				searchField.setVisible(!searchField.isVisible());				
				comboPanel.setVisible(!comboPanel.isVisible());
			} });  
		//synchronize local file to controller	
		sync_modified_file.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent event) { 
				try {
					Init.InitRemote();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} });  
			//The JFrame uses the BorderLayout layout manager.
			//Put the two JPanels and JButton in different areas. 
			guiFrame.add(comboPanel, BorderLayout.NORTH); 							
			guiFrame.add(consolePanal, BorderLayout.CENTER);			
			guiFrame.add(display_console, BorderLayout.LINE_START);
			guiFrame.add(sync_modified_file, BorderLayout.LINE_END);  
			//make sure the JFrame is visible
			guiFrame.setVisible(true);
			 
			//set to invisible when minimized. (i.e still running in tray)
			guiFrame.addWindowStateListener(new WindowStateListener() {
	            public void windowStateChanged(WindowEvent e) {
	            	//add console to tray if window minimized
	                if(e.getNewState()==1){
						guiFrame.setVisible(false);
						TrayControl.trayIcon.displayMessage("INFO:",
		                		"The program is minimized to tray but not shut down! To exit press the close button.", TrayIcon.MessageType.INFO);
	                }
	                //Not sure what this does, but 7 stands for the current visibility of the console.
			        if(e.getNewState()==7){			                    
								guiFrame.setVisible(false);
		            }			       
	            }
	        });
			 
	} 
	
	public void writeToConsole(String message_to_write){
		consoleArea.append(message_to_write + "\n");
	}
}
