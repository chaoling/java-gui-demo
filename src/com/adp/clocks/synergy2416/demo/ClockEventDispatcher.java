package com.adp.clocks.synergy2416.demo;


import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
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
	    CLOCKSTATUS_MENU,
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
//		int second = 1000; //milliseconds
//		  ActionListener taskPerformer = new ActionListener() {
//		      public void actionPerformed(ActionEvent evt) {
//		    	  System.out.println("Changing clock status to ready done.");
//		    	  m_status = CLOCK_STATUS.CLOCKSTATUS_READY;
//		    	  diffAndEmit(m_status);
//		      }
//		  };
//		  Timer t = new Timer(5*second, taskPerformer);
//		  t.setRepeats(false);
//		  t.start();	  
		while (FPU.openFPU("/root/fingers") !=0 ){
		}
		System.out.println("Changing clock status to ready ...");
  	  	m_status = CLOCK_STATUS.CLOCKSTATUS_READY;
  	  	diffAndEmit(m_status);
  	  	System.out.println("Changing clock status to ready done.");
	}

	public void handlekeyPressed(KeyEvent e) {
		if(e.getKeyChar()==27){
			System.out.println("need to get focused");
			m_status = CLOCK_STATUS.CLOCKSTATUS_MENU;
			diffAndEmit(m_status);
		}
		switch(e.getKeyCode()){
			case KeyEvent.VK_F1:			
				m_status = CLOCK_STATUS.CLOCKSTATUS_VIDEO;
				diffAndEmit(m_status);
				break;
				
			case KeyEvent.VK_F2:
				m_status = CLOCK_STATUS.CLOCKSTATUS_WEBCAM;
				diffAndEmit(m_status);
				break;
				
			case KeyEvent.VK_F3:
				m_status = CLOCK_STATUS.CLOCKSTATUS_FINGERPRINT_ENROLL;
				diffAndEmit(m_status);
				//DemoFPU.Enroll();
				//MainWindow.btnNewButton.requestFocus();
				break;
				
			case KeyEvent.VK_F4:
				
				m_status = CLOCK_STATUS.CLOCKSTATUS_FINGERPRINT_VALIDATE;
				diffAndEmit(m_status);
				//DemoFPU.Verify();
				break;
				
			case KeyEvent.VK_F7:
				//MENU key has keycode 118 which maps to F7
				System.out.println("MENU Pushed");
				m_status = CLOCK_STATUS.CLOCKSTATUS_MENU;
				diffAndEmit(m_status);
				break;
				
			case KeyEvent.VK_F5:
				//IN key sequence starts with F5 (keyCode 116)
				//OUT key sequence starts with F6 (keyCode 117)
				//m_status = CLOCK_STATUS.CLOCKSTATUS_SYSINFO;
				//diffAndEmit(m_status);
				//DemoSysInfo.showInfo();
				break;
			case KeyEvent.VK_F6:
				//OUT key sequence starts with F6 (keyCode 117)
			case KeyEvent.VK_ENTER:
			case KeyEvent.VK_ESCAPE:
				
				//Let the focus widow handle the enter/esc/IN/OUT key event
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
		System.out.println("Key Char: "+ e.getKeyChar() +" Key Code:  "+ (int) e.getKeyCode());
		Component comp = e.getComponent();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().redispatchEvent(comp,e);
	    if (e.getID() == KeyEvent.KEY_PRESSED) {
	    	handlekeyPressed(e);
            System.out.println("key pressed");
        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            System.out.println("key released");
        } else if (e.getID() == KeyEvent.KEY_TYPED) {
           //TODO
        }
		return false;
	}
}
