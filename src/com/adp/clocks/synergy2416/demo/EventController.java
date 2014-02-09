package com.adp.clocks.synergy2416.demo;

import java.awt.Component;
import java.awt.Window;
import java.awt.peer.KeyboardFocusManagerPeer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.adp.clocks.synergy2416.demo.ClockEventDispatcher.CLOCK_STATUS;

/**
 *  Event controller. The Event controller controls the routing
 *  between input events and the frame/pane displayed, among other
 *  functions such as keyListener, ActionLister and KeyboardFocusManager
 */

/**
 * @author chaol
 *
 */
public class EventController implements KeyboardFocusManagerPeer, ClockStatusListener {
	
	private MainWindow m_frame;
	private CLOCK_STATUS m_curStatus = CLOCK_STATUS.CLOCKSTATUS_UNINITIALIZED;
	public EventController(MainWindow mw) {
		setM_window(mw);
	}
	@Override
	public void clearGlobalFocusOwner(Window arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component getCurrentFocusOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Window getCurrentFocusedWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentFocusOwner(Component arg0) {
		// TODO Auto-generated method stub
		
	}

	public JFrame getM_window() {
		return m_frame;
	}
	public void setM_window(MainWindow mw) {
		this.m_frame = mw;
	}
	
	public void initialize()
	{
		// Can do some hardware initialization while displaying the boot screen
		System.out.println("initilizing event controller ...");
		//Adding signal-slot:
		m_frame.getM_cel().addListener(this); //This is similar to the "Connect(SIGNAL ... SLOT) idiom in QT
	    returnToMain();
	}
	
	public void loadInitializingForm()
	{
	    System.out.println("Loading InitializingForm");
	    JComponent newContentPane = new InitializingPane(m_frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        m_frame.setContentPane(newContentPane);
	    m_frame.pack();
	    m_frame.setVisible(true);
	    this.clearGlobalFocusOwner(m_frame);
	}
	
	public void loadWelcomeForm()
	{
	    System.out.println("Loading WelcomeForm");
	    JComponent newContentPane = new WelcomePane(m_frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        m_frame.getContentPane().removeAll();
        m_frame.setContentPane(newContentPane);
	    ((JPanel)m_frame.getContentPane()).revalidate();
	    m_frame.repaint();
	    this.setCurrentFocusOwner(m_frame);
	}
	
	public void loadShowWebCamForm()
	{
	    System.out.println("Loading WebCamForm");
	    JComponent newContentPane = new ShowWebCamForm(m_frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        m_frame.getContentPane().removeAll();
        m_frame.setContentPane(newContentPane);
	    ((JPanel)m_frame.getContentPane()).revalidate();
	    m_frame.repaint();
	}
	
	/**
	 * This is the big state machine that determines the current GUI that should show on the screen.
	 * Driven by events.
	 * 
	 *
	 */
	
	public void returnToMain() {
		switch (m_curStatus) {
		case CLOCKSTATUS_FAULT:
			//loadClockFaultForm();
			break;
		case CLOCKSTATUS_UNINITIALIZED:
			loadInitializingForm();
			break;	
		case CLOCKSTATUS_VIDEO:
			System.out.println("LoadPlayVideoForm");
			DemoVideo.showVideo();
			System.out.println("currentFocusOwner is:" + this.getCurrentFocusedWindow().toString());
			//loadPlayVideoForm();
			break;
		case CLOCKSTATUS_WEBCAM:
			System.out.println("LoadShowWebcamForm");
			loadShowWebCamForm();
			break;
		case CLOCKSTATUS_LED:
			System.out.println("Show LEDs");
			DemoLights.show();
			break;
		case CLOCKSTATUS_FINGERPRINT_ENROLL:
			System.out.println("LoadFingerPrintEnrollForm");
			//loadFingerPrintEnrollForm();
			break;
		case CLOCKSTATUS_FINGERPRINT_VALIDATE:
			System.out.println("LoadFingerPrintValidationForm");
			//loadFingerPrintValidationForm();
			break;
		case CLOCKSTATUS_SYSINFO:
			System.out.println("LoadSysInforForm");
			//loadSysInfoForm();
			break;
		case CLOCKSTATUS_READY:
		case CLOCKSTATUS_ESCAPE:
		default:
			DemoVideo.closeVideo();
			loadWelcomeForm();		
		}
	}
	@Override
	// This correspond to the "SLOT" part of the QT's signal slot idiom...
	public void clockStatusChanged(CLOCK_STATUS cs) {
		System.out.println("clock status changed to ..."+cs.toString());
  		m_curStatus = cs;
  		returnToMain();
	}
}
