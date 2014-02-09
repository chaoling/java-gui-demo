package com.adp.clocks.synergy2416.demo;


import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

/**
 * @author chaol
 * This is the class to catch and report all hardware related events
 * such as keypad press, hid input, finger print event, webcam, etc.
 *
 */
public class ClockEventDispatcher implements KeyEventDispatcher {
	
	public enum CLOCK_STATUS
	{
	    CLOCKSTATUS_FAULT,
	    CLOCKSTATUS_UNINITIALIZED,
	    CLOCKSTATUS_VIDEO,
	    CLOCKSTATUS_WEBCAM,
	    CLOCKSTATUS_LED,
	    CLOCKSTATUS_FINGERPRINT_ENROLL,
	    CLOCKSTATUS_FINGERPRINT_VALIDATE,
	    CLOCKSTATUS_SYSINFO,
	    CLOCKSTATUS_ESCAPE,
	    CLOCKSTATUS_READY
	}
	
	private static CLOCK_STATUS m_prestatus;
	private static CLOCK_STATUS m_status;
	
	List<ClockStatusListener> listeners = new ArrayList<ClockStatusListener>();
	
	public void addListener(ClockStatusListener csl) {
		listeners.add(csl);
	}
	
	public void emit(CLOCK_STATUS cs){
		for (ClockStatusListener cl : listeners) {
			cl.clockStatusChanged(cs);
		}	
	}
	
	private void diffAndEmit(CLOCK_STATUS cs){
		if (m_prestatus != cs) {
  		    m_prestatus = cs;
  		    emit(cs);
  	    }
	}
	
	//Constructor
	public ClockEventDispatcher(){
		m_status = CLOCK_STATUS.CLOCKSTATUS_UNINITIALIZED;
		diffAndEmit (m_status);
	}
	
	public void initialize() {
		//simulate initialization process, TBD
		System.out.println("Changing clock status to ready ...");
		int second = 1000; //milliseconds
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  System.out.println("Changing clock status to ready done.");
		    	  m_status = CLOCK_STATUS.CLOCKSTATUS_READY;
		    	  diffAndEmit(m_status);
		      }
		  };
		  Timer t = new Timer(5*second, taskPerformer);
		  t.setRepeats(false);
		  t.start();	  
	}

	public void handlekeyPressed(KeyEvent e) {
		if(e.getKeyChar()==27){
			System.out.println("need to get focused");
			m_status = CLOCK_STATUS.CLOCKSTATUS_ESCAPE;
			diffAndEmit(m_status);
		}
		switch(e.getKeyCode()){
			case KeyEvent.VK_1:			
				m_status = CLOCK_STATUS.CLOCKSTATUS_VIDEO;
				diffAndEmit(m_status);
				break;
			case KeyEvent.VK_2:
				m_status = CLOCK_STATUS.CLOCKSTATUS_WEBCAM;
				diffAndEmit(m_status);
				break;
			case KeyEvent.VK_3:
				m_status = CLOCK_STATUS.CLOCKSTATUS_LED;
				diffAndEmit(m_status);
//				Process p;
//				try{
//				DemoFPU.redOn();
//				Thread.sleep(1000);
//				DemoFPU.redOff();
//				Thread.sleep(500);
//				DemoFPU.greenOn();
//				Thread.sleep(1000);
//				DemoFPU.greenOff();
//				Thread.sleep(500);
//				//p = Runtime.getRuntime().exec("GPIO-RELAY-OPEN");
//				//Thread.sleep(3000);
//				//p = Runtime.getRuntime().exec("GPIO-RELAY-CLOSE");
//				}
//				catch(Exception e3){
//					e3.printStackTrace();
//				}
				break;
			case KeyEvent.VK_4:
				m_status = CLOCK_STATUS.CLOCKSTATUS_FINGERPRINT_ENROLL;
				diffAndEmit(m_status);
				//DemoFPU.Enroll();
				//MainWindow.btnNewButton.requestFocus();
				break;
			case KeyEvent.VK_5:
				m_status = CLOCK_STATUS.CLOCKSTATUS_FINGERPRINT_VALIDATE;
				diffAndEmit(m_status);
				//DemoFPU.Verify();
				break;
			case KeyEvent.VK_6:
				m_status = CLOCK_STATUS.CLOCKSTATUS_SYSINFO;
				diffAndEmit(m_status);
				//DemoSysInfo.showInfo();
				break;
			case KeyEvent.VK_ESCAPE:
				System.out.println("Escape Pushed");
				m_status = CLOCK_STATUS.CLOCKSTATUS_ESCAPE;
				diffAndEmit(m_status);
//		        try{   	
//		        	Process p2 =Runtime.getRuntime().exec( "killall -9 mplayer" );
//		        	p2.waitFor();
//		            //Runtime.getRuntime().exec( "xrefresh"); 
//		        }
//		        catch(Exception e2){
//		        	e2.printStackTrace();
//		        }
				break;
			case KeyEvent.VK_ENTER:
				//Eat this event for now.
				break;
			default:
				break;
		}
	}
	
	public CLOCK_STATUS clockStatus(){
    	return m_status;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Key Code: "+ e.getKeyChar() +" "+ (int) e.getKeyChar());
	    if (e.getID() == KeyEvent.KEY_PRESSED) {
	    	handlekeyPressed(e);
            System.out.println("key pressed");
        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            System.out.println("key released");
        } else if (e.getID() == KeyEvent.KEY_TYPED) {
            System.out.println("keytyped:"+ e.getKeyChar() +" "+ (int) e.getKeyChar());
        }
		return false;
	}
}
