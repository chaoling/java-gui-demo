package com.adp.clocks.synergy2416.demo;

import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;

import javax.swing.JFrame;

public class MainWindow extends JFrame {

	/**
	 *  This is the main entrance of the Application follow MVC pattern
	 */
	private static final long serialVersionUID = 8210857356700426265L;
	private static EventController m_ec;
	private static ClockEventDispatcher m_ced;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startMainThread();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EventController getM_ec() {
		return m_ec;
	}

//	public static void setM_ec(EventController m_ec) {
//		MainWindow.m_ec = m_ec;
//	}

	public ClockEventDispatcher getM_cel() {
		return m_ced;
	}
	/**
	 * Create the frame.
	 */
	public MainWindow(String name) {
		super(name);
	}
	
	private static void startMainThread() {
		//Create and set up the main window.
		MainWindow m_frame = new MainWindow("ADPDemoMainWindow");
		m_frame.setBounds(0, 0, 320, 240);
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_ced = new ClockEventDispatcher();
		m_ced.initialize();
		m_ec = new EventController(m_frame);
		m_ec.initialize();
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(m_ced);
	}
}
