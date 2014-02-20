package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
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
public class EventController implements ClockStatusListener {
	
	private static final String ResPath = "/com/adp/clocks/synergy2416/res/";
	
	private MainWindow m_frame;
	private CLOCK_STATUS m_curStatus = CLOCK_STATUS.CLOCKSTATUS_UNINITIALIZED;
	
	private CardLayout m_cards;
	private JPanel m_cardPanel;
	private WelcomeForm m_welcomeForm;
	private FingerPrintEnrollForm m_fingerPrintEnrollForm;
	private FingerPrintControlForm m_fingerPrintControlForm;
	private VideoForm m_videoDemoForm;
	private ShowWebCamForm m_webCamDemoForm;
	private JLabel m_lblBackground;
	private JLabel m_lblFormName;
	private JPanel m_pMenuBar;
	//private JPanel m_pStatusBar;
	private JPanel m_pFooter;
	private JPanel m_pHeader;
	private ImageIcon m_iconBackground;

	private JLabel lblVideoImage;

	private JLabel lblCameraImage;

	private JLabel lblFpuEnrollImage;

	private JLabel lblFpuControlImage;
	private static final int IconSize=35;
	private static final int LogoSize=30;
	
	public enum ICONS {
		
	}
	
	public EventController(MainWindow mw) {
		this.m_frame = mw;
	}
	
	public JLabel getM_lblInstruction() {
		return m_lblFormName;
	}
	
	public JPanel getM_pMenuBar() {
		return m_pMenuBar;
	}
	
	public JLabel getLblVideoImage() {
		return lblVideoImage;
	}

	public JLabel getLblCameraImage() {
		return lblCameraImage;
	}

	public JLabel getLblFpuEnrollImage() {
		return lblFpuEnrollImage;
	}

	public JLabel getLblFpuControlImage() {
		return lblFpuControlImage;
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
			
			m_welcomeForm = new WelcomeForm(m_frame);
			m_videoDemoForm = new VideoForm(m_frame);
			m_webCamDemoForm = new ShowWebCamForm(m_frame);
			m_fingerPrintEnrollForm = new FingerPrintEnrollForm(m_frame);
			m_fingerPrintControlForm = new FingerPrintControlForm(m_frame);
			
			m_cardPanel.add(m_welcomeForm,"welcome");
			m_cardPanel.add(m_videoDemoForm,"videoDemo");
			m_cardPanel.add(m_webCamDemoForm,"webcamDemo");
			m_cardPanel.add(m_fingerPrintEnrollForm,"fpEnroll");
			m_cardPanel.add(m_fingerPrintControlForm,"fpControl");
			
			m_frame.getContentPane().add(m_pHeader,BorderLayout.NORTH);
			m_frame.getContentPane().add(m_cardPanel,BorderLayout.CENTER);
			m_frame.getContentPane().add(m_pFooter,BorderLayout.SOUTH);
			
			m_frame.pack();
			m_frame.toFront();
			m_frame.setVisible(true);
			
			System.out.println("Done initializing gui...");
			MainWindow.getM_ap().playSuccessSound();
	}
	
	private void createFooter() {
	        m_pMenuBar = new JPanel();
			m_pMenuBar.setBorder(new EmptyBorder(0, 0, 0, 0));
			m_pMenuBar.setLayout(new GridLayout(1, 6));
		
			ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource(ResPath+"video-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
			lblVideoImage = new JLabel();
			GridBagConstraints gbc_lblVideoimage = new GridBagConstraints();
			lblVideoImage.setIcon(icon);
			m_pMenuBar.add(lblVideoImage, gbc_lblVideoimage);
			
			icon = new ImageIcon(new ImageIcon(getClass().getResource(ResPath+"camera-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
			lblCameraImage = new JLabel();
			GridBagConstraints gbc_lblCameraimage = new GridBagConstraints();
			lblCameraImage.setIcon(icon);
			m_pMenuBar.add(lblCameraImage, gbc_lblCameraimage);
			
//			icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/light-icon-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
//			JLabel lblGpiolabel = new JLabel();
//			GridBagConstraints gbc_lblGpiolabel = new GridBagConstraints();
//			lblGpiolabel.setIcon(icon);
//			m_pMenuBar.add(lblGpiolabel, gbc_lblGpiolabel);
			
			icon = new ImageIcon(new ImageIcon(getClass().getResource(ResPath+"finger-icon-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
			lblFpuEnrollImage = new JLabel();
			GridBagConstraints gbc_lblFpuimage = new GridBagConstraints();
			lblFpuEnrollImage.setIcon(icon);
			m_pMenuBar.add(lblFpuEnrollImage, gbc_lblFpuimage);
			
			icon = new ImageIcon(new ImageIcon(getClass().getResource(ResPath+"lock.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
			lblFpuControlImage = new JLabel();
			GridBagConstraints gbc_lblFpuimage_1 = new GridBagConstraints();
			lblFpuControlImage.setIcon(icon);
			m_pMenuBar.add(lblFpuControlImage, gbc_lblFpuimage_1);
			
//			icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/info-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
//			JLabel lblSysimage = new JLabel();
//			GridBagConstraints gbc_lblSysimage = new GridBagConstraints();
//			lblSysimage.setIcon(icon);
//			m_pMenuBar.add(lblSysimage, gbc_lblSysimage);	
			
			m_pFooter = new JPanel();
			m_pFooter.setSize(320, 30);
			m_pFooter.setBorder(BorderFactory.createEmptyBorder(8,20,8,5));
			m_pFooter.setLayout(new BoxLayout(m_pFooter, BoxLayout.X_AXIS));
			m_pFooter.add(m_pMenuBar);
	}
	
	private void createHeader() {
		
			m_lblFormName = new JLabel();
			//m_lblInstruction.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource(ResPath+"synel.png")).getImage().getScaledInstance(LogoSize, LogoSize, java.awt.Image.SCALE_SMOOTH)));
			m_lblFormName.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource(ResPath+"SynelLogoSmall.png")).getImage().getScaledInstance(3*LogoSize, LogoSize, java.awt.Image.SCALE_SMOOTH)));
			m_lblFormName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			m_lblFormName.setVerticalAlignment(javax.swing.SwingConstants.TOP);
			
			//m_pStatusBar = new JPanel();
			JLabel lblTimeLabel = new JLabel();
			lblTimeLabel.setFont(new Font("Times", Font.PLAIN, 16));
			lblTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			lblTimeLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
			lblTimeLabel.setOpaque(false);
			UpdateClock.initialize(lblTimeLabel);
			//m_pStatusBar.add(lblTimeLabel);
			    
			m_pHeader = new JPanel();
			m_pHeader.setSize(320, 30);
			m_pHeader.setLayout(new BoxLayout(m_pHeader, BoxLayout.X_AXIS));
	        m_pHeader.add(m_lblFormName);
	        m_pHeader.add(Box.createHorizontalStrut(5));
	        m_pHeader.add(lblTimeLabel);
		}

	private void createBackgroundLabel(){
		m_iconBackground = new ImageIcon(MainWindow.class.getResource(ResPath+"Background.png"));
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
	    FPU.Light.RED.off();
    	FPU.Light.GREEN.off();
	    m_cards.show(m_cardPanel, "welcome");
	    m_welcomeForm.updateLabel();
	    m_welcomeForm.requestFocusInWindow();
	    //m_welcomeForm.IdentifyEmp();
	}
	
	public void loadPlayVideoForm()
	{
	    System.out.println("Loading Playing Video Form");
	    m_cards.show(m_cardPanel, "videoDemo");
	    m_videoDemoForm.updateLabel();
	    m_videoDemoForm.requestFocusInWindow();
	}
	
	public void loadShowWebCamForm()
	{
	    System.out.println("Loading WebCamForm");
	    m_cards.show(m_cardPanel, "webcamDemo");
	    m_webCamDemoForm.updateLabel();
	    m_webCamDemoForm.requestFocusInWindow();
	}
	
	public void loadFingerPrintEnrollForm()
	{
	    System.out.println("Loading FingerPrint Enrollment Form");
	    m_cards.show(m_cardPanel, "fpEnroll");
	    m_fingerPrintEnrollForm.updateLabel();
	    m_fingerPrintEnrollForm.setFocusable(true);
	    m_fingerPrintEnrollForm.requestFocusInWindow();
	    m_fingerPrintEnrollForm.goDemo();
	    
	}
	public void loadFingerPrintControlForm()
	{
	    System.out.println("Loading FingerPrint Control Form");
	    //new FingerPrintDemoForm(m_frame);
	    m_cards.show(m_cardPanel, "fpControl");
	    m_fingerPrintControlForm.updateLabel();
	    m_fingerPrintControlForm.setFocusable(true);
	    m_fingerPrintControlForm.requestFocusInWindow();
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
			loadPlayVideoForm();
			break;
		case CLOCKSTATUS_WEBCAM:
			System.out.println("LoadShowWebcamForm");
			loadShowWebCamForm();
			break;
		case CLOCKSTATUS_FINGERPRINT_ENROLL:
			System.out.println("LoadFingerPrintEnrollForm");
			loadFingerPrintEnrollForm();
			break;
		case CLOCKSTATUS_FINGERPRINT_CONTROL:
			System.out.println("LoadFingerPrintControlForm");
			loadFingerPrintControlForm();
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
