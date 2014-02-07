package com.adp.clocks.synergy2416.demo;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
/*
 * This is the Event Class that handles all events and establishes an interface for those
 * events to be handed too for processing. As long as you implement the punchInterface you can
 * take over the handle of events changing the way events are handled but not the UI
 */
import javax.swing.SwingUtilities;

public class DemoEvent implements KeyListener, ActionListener {
	
	//public static DemoFPU fpu;
	//public static DemoDialog badge; //= new DemoDialog("Please Place Finger");	
	//public static DemoDialog badge2; //= new DemoDialog("Please Place Finger Again ");	
	//public static DemoDialog badge3; //= new DemoDialog("Please Place Finger Last Time");	
	//public static DemoDialog badge4;// = new DemoDialog("Success");

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Key Code: "+ e.getKeyChar() +" "+ (int) e.getKeyChar());

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key Press: "+ e.getKeyChar() +" "+ (int) e.getKeyChar());
		if(e.getKeyChar()==27){
			System.out.println("button focused");
			MainWindow.lblTimeLabel.requestFocus();
		}
		switch(e.getKeyCode()){
			case KeyEvent.VK_1:			
				DemoVideo.showVideo();
				break;
			case KeyEvent.VK_2:
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						MainWindow.camera.setVisible(true);
					}
				});

				break;
			case KeyEvent.VK_3:
				Process p;
				try{
				DemoFPU.redOn();
				Thread.sleep(1000);
				DemoFPU.redOff();
				Thread.sleep(500);
				DemoFPU.greenOn();
				Thread.sleep(1000);
				DemoFPU.greenOff();
				Thread.sleep(500);
				//p = Runtime.getRuntime().exec("GPIO-RELAY-OPEN");
				//Thread.sleep(3000);
				//p = Runtime.getRuntime().exec("GPIO-RELAY-CLOSE");
				}
				catch(Exception e3){
					e3.printStackTrace();
				}
				break;
			case KeyEvent.VK_4:
				DemoFPU.Enroll();
				//MainWindow.btnNewButton.requestFocus();
				break;
			case KeyEvent.VK_5:
				DemoFPU.Verify();
				break;
			case KeyEvent.VK_6:
				DemoSysInfo.showInfo();
				break;
			case KeyEvent.VK_ESCAPE:
				System.out.println("Escape Pushed");
		        try{   	
		        	Process p2 =Runtime.getRuntime().exec( "killall -9 mplayer" );
		        	p2.waitFor();
		            //Runtime.getRuntime().exec( "xrefresh"); 
		        }
		        catch(Exception e2){
		        	e2.printStackTrace();
		        }
				break;
			case KeyEvent.VK_ENTER:

				break;
			default:
				
				break;
		}
				
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	System.out.println("Button Pressed");
		
	}
	
}
