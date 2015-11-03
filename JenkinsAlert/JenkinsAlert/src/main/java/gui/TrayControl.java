package gui;  
/*
 * TrayIconDemo.java
 */
 
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

import alertsys.AnalyzeOutput;
import alertsys.ReadJenkins;
 
public class TrayControl {
 
     public static boolean PLAY_SOUND = true;
     public static final TrayIcon trayIcon = new TrayIcon(createImage("/images/bulb.gif", "tray icon"));
     
    public static void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        
        final SystemTray tray = SystemTray.getSystemTray();
         
        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        
        // User options for job finished notifications!
        Menu testFin = new Menu("On Finished");        
        CheckboxMenuItem playSound = new CheckboxMenuItem("Mute Sound");
        CheckboxMenuItem displayPopup = new CheckboxMenuItem("Display Popup");
        
        Menu displayMenu = new Menu("Display");
        MenuItem currentTest = new MenuItem("Current Test");

        MenuItem exitItem = new MenuItem("Exit");
         
        trayIcon.setImageAutoSize(true);
        //Add components to popup menu
        popup.add(aboutItem);
        
        popup.addSeparator();
        
        popup.add(testFin);                
        testFin.add(playSound);
        testFin.add(displayPopup);
        
        popup.addSeparator();
        
        popup.add(displayMenu);
        displayMenu.add(currentTest);

        popup.add(exitItem);
         
        trayIcon.setPopupMenu(popup);
         
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("TrayIcon could not be added.");
            return;
        }
         
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//TODO: Get it to open console and display log!
            	AlertGui.guiFrame.setVisible(true);            	
            }
        });
         
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This program plays a sound and notifies user when jobs are finished on Jenkins.");
            }
        });
       
        playSound.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                	PLAY_SOUND = false;                	
                } else {
                	PLAY_SOUND = true;                	
                }
            }
        });
        
        displayPopup.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb2Id = e.getStateChange();
                if (cb2Id == ItemEvent.SELECTED && AnalyzeOutput.ISFINISHED == true){
                	trayIcon.displayMessage("Jenkins:",
                    		AnalyzeOutput.FINAL_RESULT, TrayIcon.MessageType.INFO);;
                } else {
                    trayIcon.setToolTip(null);
                }
            }
        });
         
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem)e.getSource();
                //TrayIcon.MessageType type = null;
                //System.out.println(item.getLabel());
                if ("Current Test".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.ERROR;
                	String consoleUrl = ReadJenkins.CONSOLE_URL;
                	
                	if(consoleUrl == null){
                		trayIcon.displayMessage("Currently building:",
                        		"There is nothing running on Jenkins at the moment!", TrayIcon.MessageType.INFO);
                	} else {
                		if(AnalyzeOutput.ISFINISHED == true){
                			trayIcon.displayMessage("Currently building:",
                            		"There is nothing running on Jenkins at the moment!", TrayIcon.MessageType.INFO);
                		}
                		trayIcon.displayMessage("Currently building:",
                        		"JOB Running: "+consoleUrl, TrayIcon.MessageType.INFO);
                	}
                } 
            }
        };
         
        currentTest.addActionListener(listener);
         
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
     
    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = TrayControl.class.getResource(path);
         
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}