package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.MemoryImageSource;
import java.awt.peer.KeyboardFocusManagerPeer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
	
	private CardLayout m_cards;
	private JPanel m_cardPanel;
	private WelcomeForm m_welcomeForm;
	private FingerPrintDemoForm m_fingerPrintDemoForm;
	private PlayVideoForm m_videoDemoPanel;
	private ShowWebCamForm m_webCamDemoPanel;
	private JLabel m_lblBackground;
	private JLabel m_lblInstruction;
	private JPanel m_pMenuBar;
	private JPanel m_pStatusBar;
	private JPanel m_pFooter;
	private ImageIcon m_iconBackground;
	private static final int IconSize=35;
	private static final int LogoSize=35;
	
	public EventController(MainWindow mw) {
		this.m_frame = mw;
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
	
	public void initialize()
	{
		// Can do some hardware initialization while displaying the boot screen
		System.out.println("initilizing event controller ...");
		//Adding signal-slot:
		m_frame.getM_cel().addListener(this); //This is similar to the "Connect(SIGNAL ... SLOT) idiom in QT
		//Initialize HW component, such as the finger print reader
		initializeGUI();
	    returnToMain();
	}
	
	private void initializeGUI() {
			System.out.println("Initializing gui...");
			m_frame.setBounds(0, 0, 320, 240);
			m_frame.setSize(320,240);
			m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			m_frame.getToolkit().beep();
			hideCursor();
			createBackgroundLabel();
			createHeader();
			createFooter();
			m_frame.setContentPane(m_lblBackground);
			m_frame.getContentPane().setLayout(new BorderLayout());
			
			m_cards = new CardLayout();
			m_cardPanel = new JPanel();
			m_cardPanel.setLayout(m_cards);
			m_cardPanel.setSize(320, 200);
			m_cardPanel.setOpaque(false);
			m_welcomeForm = new WelcomeForm();
			m_fingerPrintDemoForm = new FingerPrintDemoForm();
			//m_videoDemoPanel = new PlayVideoForm(m_frame);
			//m_webCamDemoPanel = new ShowWebCamForm(m_frame);
			m_cardPanel.add(m_welcomeForm,"welcome");
			//m_cardPanel.add(m_videoDemoPanel,"videoDemo");
			//m_cardPanel.add(m_webCamDemoPanel,"webcamDemo");
			m_cardPanel.add(m_fingerPrintDemoForm,"fpDemo");
			m_frame.getContentPane().add(m_lblInstruction,BorderLayout.NORTH);
			m_frame.getContentPane().add(m_cardPanel,BorderLayout.CENTER);
			m_frame.getContentPane().add(m_pFooter,BorderLayout.SOUTH);
			m_frame.pack();
			m_frame.toFront();
			m_frame.setVisible(true);
			System.out.println("Done initializing gui...");
	}
	
	private void hideCursor() {
		// TODO Auto-generated method stub
		int[] pixels = new int[16 * 16];
		Image image = Toolkit.getDefaultToolkit().createImage(
		        new MemoryImageSource(16, 16, pixels, 0, 16));
		Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "invisibleCursor");
		
	}
	private void createFooter() {
		    m_pStatusBar = new JPanel();
		    JLabel lblTimeLabel = new JLabel();
			lblTimeLabel.setFont(new Font("Times", Font.PLAIN, 16));
			lblTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			lblTimeLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			lblTimeLabel.setOpaque(false);
			UpdateClock.initialize(lblTimeLabel);
		    m_pStatusBar.add(lblTimeLabel);
		    
		    
	        m_pMenuBar = new JPanel();
			m_pMenuBar.setBorder(new EmptyBorder(0, 0, 0, 0));
			m_pMenuBar.setLayout(new GridLayout(1, 6));
		
			ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/video-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
			JLabel lblVideoimage = new JLabel();
			GridBagConstraints gbc_lblVideoimage = new GridBagConstraints();
			lblVideoimage.setIcon(icon);
			m_pMenuBar.add(lblVideoimage, gbc_lblVideoimage);
			
			icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/camera-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
			JLabel lblCameraimage = new JLabel();
			GridBagConstraints gbc_lblCameraimage = new GridBagConstraints();
			lblCameraimage.setIcon(icon);
			m_pMenuBar.add(lblCameraimage, gbc_lblCameraimage);
			
//			icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/light-icon-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
//			JLabel lblGpiolabel = new JLabel();
//			GridBagConstraints gbc_lblGpiolabel = new GridBagConstraints();
//			lblGpiolabel.setIcon(icon);
//			m_pMenuBar.add(lblGpiolabel, gbc_lblGpiolabel);
			
			icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/finger-icon-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
			JLabel lblFpuimage = new JLabel();
			GridBagConstraints gbc_lblFpuimage = new GridBagConstraints();
			lblFpuimage.setIcon(icon);
			m_pMenuBar.add(lblFpuimage, gbc_lblFpuimage);
			
			icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/id-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
			JLabel lblFpuimage_1 = new JLabel();
			GridBagConstraints gbc_lblFpuimage_1 = new GridBagConstraints();
			lblFpuimage_1.setIcon(icon);
			m_pMenuBar.add(lblFpuimage_1, gbc_lblFpuimage_1);
			
//			icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/info-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
//			JLabel lblSysimage = new JLabel();
//			GridBagConstraints gbc_lblSysimage = new GridBagConstraints();
//			lblSysimage.setIcon(icon);
//			m_pMenuBar.add(lblSysimage, gbc_lblSysimage);	
			
			m_pFooter = new JPanel();
			m_pFooter.setSize(320, 20);
			m_pFooter.setLayout(new BoxLayout(m_pFooter, BoxLayout.X_AXIS));
			m_pFooter.add(Box.createHorizontalGlue());
			m_pFooter.add(m_pStatusBar);
			m_pFooter.add(Box.createHorizontalStrut(5));
			m_pFooter.add(m_pMenuBar);
	}
	
	private void createHeader() {
			m_lblInstruction = new JLabel("<html><font color='yellow'>Press <font color='red'>F1-F4 </font> key to start demo funtions.</font> </html>");
			m_lblInstruction.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource("/com/adp/clocks/synergy2416/res/synel.png")).getImage().getScaledInstance(LogoSize, LogoSize, java.awt.Image.SCALE_SMOOTH)));
			m_lblInstruction.setAlignmentY(Component.TOP_ALIGNMENT);
		}

	private void createBackgroundLabel(){
		m_iconBackground = new ImageIcon(MainWindow.class.getResource("/com/adp/clocks/synergy2416/res/Background.png"));
		m_lblBackground = new JLabel(m_iconBackground);
		m_lblBackground.setOpaque(true);
	}
		
	public void loadInitializingForm()
	{
	    System.out.println("Loading InitializingForm");
	}
	
	public void loadWelcomeForm()
	{
	    System.out.println("Loading WelcomeForm");
	    m_cards.show(m_cardPanel, "welcome");
	    new DemoSound("res/hello.wav").start();
	    //m_frame.repaint();
	}
	
	public void loadPlayVideoForm()
	{
	    System.out.println("Loading Playing Video Form");
	    //m_cards.show(m_cardPanel, "videoDemo");
	}
	
	public void loadShowWebCamForm()
	{
	    System.out.println("Loading WebCamForm");
	    //m_cards.show(m_cardPanel, "webcamDemo");
	}
	
	public void loadLEDDemoForm()
	{
	    System.out.println("LED Demo ...");
	    //new ShowWebCamForm(m_frame);
	}
	
	public void loadFingerPrintDemoForm()
	{
	    System.out.println("Loading FingerPrint Demo Form");
	    m_cards.show(m_cardPanel, "fpDemo");
	    m_fingerPrintDemoForm.setFocusable(true);
	    m_fingerPrintDemoForm.goDemo();
	    
	}
	public void loadFingerPrintValidationForm()
	{
	    System.out.println("Loading FingerPrint Validation Form");
	    //new FingerPrintDemoForm(m_frame);
	}
	public void loadSysInforForm()
	{
	    System.out.println("Loading SystemInformationForm");
	    //new FingerPrintDemoForm(m_frame);
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
//			DemoVideo.showVideo();
//			System.out.println("currentFocusOwner is:" + this.getCurrentFocusedWindow().toString());
			loadPlayVideoForm();
			break;
		case CLOCKSTATUS_WEBCAM:
			System.out.println("LoadShowWebcamForm");
			loadShowWebCamForm();
			break;
		case CLOCKSTATUS_LED:
			System.out.println("Show LEDs");
			loadLEDDemoForm();
			break;
		case CLOCKSTATUS_FINGERPRINT_ENROLL:
			System.out.println("LoadFingerPrintDemoForm");
			loadFingerPrintDemoForm();
			break;
		case CLOCKSTATUS_FINGERPRINT_VALIDATE:
			System.out.println("LoadFingerPrintValidationForm");
			loadFingerPrintValidationForm();
			break;
		case CLOCKSTATUS_SYSINFO:
			System.out.println("LoadSysInforForm");
			loadSysInforForm();
			break;
		case CLOCKSTATUS_READY:
		case CLOCKSTATUS_MENU:
		default:
			//DemoVideo.closeVideo();
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
