package com.adp.clocks.synergy2416.demo;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class FingerPrintDemoForm extends JPanel implements FingerPrintEnrollmentHandler, Employee {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static String PANEL_ID = "Input_Badge_ID";
    final static String PANEL_FPN = "Input_FingerPrint_Number";
    final static String PANEL_STATUS = "Enrollment_Status";
    
	private JTextField m_txtBadgeNum;
	private JLabel m_lblInputBadgeNum;
	private JLabel m_lblInputFingerNum;
	private JTextField m_txtFingerNum;
	private JLabel m_lblEnrollmentStatus;
    private String m_strBadgeNum;
    private int m_nFingerNum;
    private CardLayout m_cl;
    private String m_strCurrentCardName;
	private JTextArea m_txtEnrollmentResult;
    
    public enum CardNames {
    	Input_Badge_ID,
    	Input_FingerPrint_Number,
    	Enrollment_Status
    }
    
    public FingerPrintDemoForm() {
    	//m_bContinue = true;
    	setSize(320, 200);
    	addComponentToPane();
    	setOpaque(false);
    }
     
    public void addComponentToPane() {
         
        //Create the "cards".
        JPanel card1 = new JPanel();
        m_lblInputBadgeNum = new JLabel("Enter Badge Number:");
        m_txtBadgeNum = new JTextField(12);
        card1.setName(PANEL_ID);
        card1.setSize(this.getSize());
        JPanel textBoxPanel = new JPanel();
        textBoxPanel.add(m_lblInputBadgeNum);
        textBoxPanel.add(m_txtBadgeNum);
        textBoxPanel.setMaximumSize(this.getSize());
        //textBoxPanel.setBounds(90, 150, 300, 40);
        textBoxPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textBoxPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        card1.add(textBoxPanel);
        card1.add(new JButton("Press ENTER to Confirm"));
        card1.setOpaque(false);
         
        JPanel card2 = new JPanel();
        card2.setSize(this.getSize());
        m_lblInputFingerNum = new JLabel("Enter Finger Number:");
        m_txtFingerNum =  new JTextField(5);
        card2.setName(PANEL_FPN);
        JPanel textBoxPanel2 = new JPanel();
        textBoxPanel2.setMaximumSize(this.getSize());
        textBoxPanel2.add(m_lblInputFingerNum);
        textBoxPanel2.add(m_txtFingerNum);
        textBoxPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        textBoxPanel2.setAlignmentY(Component.CENTER_ALIGNMENT);
        card2.add(textBoxPanel2);
        card2.add(new JButton("Press ENTER to Enroll"));
        card2.setOpaque(false);
        
        JPanel card3 = new JPanel();
        card3.setSize(this.getSize());
        m_lblEnrollmentStatus = new JLabel("Enrollment:");
        m_lblEnrollmentStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_lblEnrollmentStatus.setOpaque(false);
        m_txtEnrollmentResult = new JTextArea();
        m_txtEnrollmentResult.setFont(new Font("Times", Font.ROMAN_BASELINE, 12));
        m_txtEnrollmentResult.setLineWrap(true);
        m_txtEnrollmentResult.setWrapStyleWord(true);
        m_txtEnrollmentResult.setEditable(false);
        m_txtEnrollmentResult.setOpaque(false);
        JPanel textBoxPanel3 = new JPanel();
        textBoxPanel3.setSize(320, 200);
        textBoxPanel3.setLayout(new BoxLayout(textBoxPanel3, BoxLayout.Y_AXIS));
        textBoxPanel3.add(Box.createVerticalGlue());
        textBoxPanel3.add(m_lblEnrollmentStatus);
        textBoxPanel3.add(Box.createVerticalStrut(5));
        textBoxPanel3.add(m_txtEnrollmentResult);
		
        card3.setName(PANEL_STATUS);
        textBoxPanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        textBoxPanel3.setAlignmentY(Component.CENTER_ALIGNMENT);
        textBoxPanel3.setOpaque(false);
        card3.add(textBoxPanel3);
        //card3.add(new JButton("Press ENTER to start again"));
        card3.setOpaque(false);
         
        //Create the panel that contains the "cards".
        m_cl = new CardLayout();
        setLayout(m_cl);
        add(card1,PANEL_ID);
        add(card2,PANEL_FPN);
        add(card3,PANEL_STATUS);
    }

	@Override
	public void setBadge(String badge) {
		// TODO Auto-generated method stub
		System.out.println("Badge Identified: "+badge);
		
	}

	@Override
	public void setStepCount(int count) {
		// TODO Auto-generated method stub
		new DemoSound("res/fingerplace.wav").start();
		System.out.println("Start");
		System.out.println("Please place finger "+ count + " times");
		m_txtEnrollmentResult.append("\nPlease place finger "+ count + " times");
		m_cl.next(FingerPrintDemoForm.this);
		
	}

	@Override
	public void onReadyForFinger(int step, boolean repeatOnReaderError) {
		// TODO Auto-generated method stub
		switch(step){
		case 1:
			new DemoSound("res/fingerplace.wav").start();
			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
			m_txtEnrollmentResult.append("\nPlease place finger Step: "+ step);
			m_cl.last(FingerPrintDemoForm.this);
			break;
		case 2:
			new DemoSound("res/fingerPlaceAgain.wav").start();
			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
			m_txtEnrollmentResult.append("\nPlease place finger Step: "+ step );
			m_cl.last(FingerPrintDemoForm.this);
			break;
		case 3:
			new DemoSound("res/fingerPlace.wav").start();
			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
			m_txtEnrollmentResult.append("\nPlease place finger Step: "+ step );
			m_cl.last(FingerPrintDemoForm.this);
			break;
		}
		
	}

	@Override
	public void onFingerPrintRead(int step) {
		// TODO Auto-generated method stub
		this.getToolkit().beep();
		System.out.println("Please Remove Finger Step: "+ step + "  ");
		m_txtEnrollmentResult.append("\nPlease Remove finger Step: "+ step + " ");
		m_cl.last(FingerPrintDemoForm.this);
		
	}
	
	@SuppressWarnings("serial")
	public void goDemo() {
		m_cl.first(this);
		setFocusable(true);
		if (! m_txtBadgeNum.requestFocusInWindow()){
    		m_txtBadgeNum.requestFocus();
    	}
		System.out.println("goDemo!");
		setInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, getInputMap());
	    KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
	    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, "pressed");
	    getActionMap().put("pressed", new AbstractAction(){         

			public void actionPerformed(ActionEvent arg0) {
				m_strCurrentCardName = FingerPrintDemoForm.this.getCurrentCardName();
	            System.out.println("Key Enter pressed, I am in "+m_strCurrentCardName);
	            FingerPrintDemoForm.this.handleAction();
			} 
	    });
	}
	
	private String getCurrentCardName(){
		for (Component comp : this.getComponents()) {
			if (comp.isVisible()) {
				String myName = comp.getName();
				return myName;
			}
		}
		return "Invalid";
	}
	
	private void handleAction(){
		switch(CardNames.valueOf(m_strCurrentCardName)){
        case Input_Badge_ID:
        	m_strBadgeNum = m_txtBadgeNum.getText();
        	if (m_strBadgeNum != null){
        		m_cl.next(FingerPrintDemoForm.this);
        		if (! m_txtFingerNum.requestFocusInWindow()){
            		m_txtFingerNum.requestFocus();
            	}
        	}
        	break;
        case Input_FingerPrint_Number:
        	String strFingerNum = m_txtFingerNum.getText();
        	if (strFingerNum != null){
        		m_nFingerNum = Integer.parseInt(strFingerNum);
        		m_cl.next(FingerPrintDemoForm.this);
        		m_lblEnrollmentStatus.setText("Enroll Employee ID: "+m_strBadgeNum+" Finger: "+m_nFingerNum);
        		if (! m_lblEnrollmentStatus.requestFocusInWindow()){
            		m_lblEnrollmentStatus.requestFocus();
            	}
        		String strResult = FPU.enroll(m_strBadgeNum, m_nFingerNum,this);
        		m_txtEnrollmentResult.setText("\n "+strResult+"\n Press Enter to Restart "+"\n press MENU to return to main menu");
        		
        	}
        	break;
        case Enrollment_Status:
        	clearForm();
        	m_cl.first(FingerPrintDemoForm.this);
        	if (! m_txtBadgeNum.requestFocusInWindow()){
        		m_txtBadgeNum.requestFocus();
        	}
        	break;
        default:
        	System.out.println("I am in default state.");
        	m_cl.first(FingerPrintDemoForm.this);		
		}
	}

	private void clearForm() {
		System.out.println("I am clearing form...");
		m_txtBadgeNum.setText("");
		m_txtFingerNum.setText("");
	}
}
