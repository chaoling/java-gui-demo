package com.adp.clocks.synergy2416.demo;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

public class FingerPrintEnrollForm extends JPanel implements FingerPrintEnrollmentHandler, KeyListener{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static String PANEL_ID = "Input_Employee_ID";
    //final static String PANEL_FPN = "Input_FingerPrint_Number";
    final static String PANEL_STATUS = "Enrollment_Status";
    
	private JTextField m_txtEmployeeNum;
	private JLabel m_lblInputEmployeeNum;
	//private JLabel m_lblInputFingerNum; 
	//private JTextField m_txtFingerNum;
	private JLabel m_lblEnrollmentStatus;
    private String m_strEmployeeNum;
    private int m_nFingerNum = 0; //For demo purpose, always finger 0
    private CardLayout m_cl;
    private String m_strCurrentCardName;
	private JTextPane m_txtEnrollmentResult;
	private final int m_width=320;
	private final int m_height = 240;
	private MainWindow m_mw;
    
    public enum CardNames {
    	Input_Employee_ID,
    	//Input_FingerPrint_Number,
    	Enrollment_Status
    }
    
    class FingerPrintEnroll extends SwingWorker<String, Object> {
        @Override
        public String doInBackground() {
            return FPU.enroll(m_strEmployeeNum, m_nFingerNum,FingerPrintEnrollForm.this);
        }

        @Override
        protected void done() {
            try { 
            	String strResult = get();
                m_txtEnrollmentResult.setText("<html><font color=black>"+strResult+"<br>Press Enter to Restart "+"<br>Press MENU to the main menu</font><html>");
                m_lblEnrollmentStatus.setText("Enroll Employee ID: "+m_strEmployeeNum+" Finger: "+m_nFingerNum);
                if (strResult.compareTo("Succeed!") == 0) {
                	
                	FPU.Light.RED.off();
                	FPU.Light.GREEN.on();
                	MainWindow.getM_ap().playEnrollSound();
                } else {
                	
                	FPU.Light.GREEN.off();
                	FPU.Light.RED.on();
                	MainWindow.getM_ap().playBeepSound();
                }
            } catch (Exception ignore) {
            }
        }
    }
    
//    class txtFieldListener implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// TODO Auto-generated method stub
//			System.out.println(e.getSource().toString());
//			if (e.getSource() == m_txtEmployeeNum ){
//				FingerPrintEnrollForm.this.m_strEmployeeNum = m_txtEmployeeNum.getText();
//	        	if (m_strEmployeeNum != null){
//	        		m_cl.next(FingerPrintEnrollForm.this);
//	        		m_lblEnrollmentStatus.setText("Employee ID: "+m_strEmployeeNum+" Finger: "+m_nFingerNum);
//	        		m_lblEnrollmentStatus.requestFocusInWindow();
//	        		(new FingerPrintEnroll()).execute(); //Swing worker class.
//	        	}		
//			}
//       }
//    }
    
    public FingerPrintEnrollForm(MainWindow mw) {
    	this.m_mw = mw;
    	//m_bContinue = true;
    	setSize(m_width, m_height);
    	addComponentToPane();
    	//attachKeyListeners();
    	setOpaque(false);	
    	
    	this.addFocusListener(new FocusListener(){
	          public void focusGained(FocusEvent e){
	              System.out.println(this.toString()+"Focus GAINED:"+e);
	          }
	          public void focusLost(FocusEvent e){
	        	  FingerPrintEnrollForm.this.clearForm();
	              System.out.println(this.toString()+"Focus LOST:"+e);
	          }
	      });
    	
    	m_txtEmployeeNum.addKeyListener(this);
    	m_lblEnrollmentStatus.addKeyListener(this);
    }

    public void updateLabel(){
    	m_mw.getM_ec().getLblVideoImage().setVisible(false);
		m_mw.getM_ec().getLblCameraImage().setVisible(false);
		m_mw.getM_ec().getLblFpuEnrollImage().setVisible(true);
		m_mw.getM_ec().getLblFpuControlImage().setVisible(false);
    }

	public void addComponentToPane() {
        //Create the "cards".
        JPanel card1 = new JPanel();
        m_lblInputEmployeeNum = new JLabel("Enter Employee Number:");
        m_txtEmployeeNum = new JTextField(12);
        card1.setName(PANEL_ID);
        card1.setSize(this.getSize());
        JPanel textBoxPanel = new JPanel();
        textBoxPanel.add(m_lblInputEmployeeNum);
        textBoxPanel.add(m_txtEmployeeNum);
        textBoxPanel.setMaximumSize(this.getSize());
        //textBoxPanel.setBounds(90, 150, 300, 40);
        textBoxPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textBoxPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        card1.add(textBoxPanel);
        card1.add(new JButton("Press ENTER to Confirm"));
        card1.setOpaque(false);
         
//        JPanel card2 = new JPanel();
//        card2.setSize(this.getSize());
//        m_lblInputFingerNum = new JLabel("Enter Finger Number:");
//        m_txtFingerNum =  new JTextField(5);
//        card2.setName(PANEL_FPN);
//        JPanel textBoxPanel2 = new JPanel();
//        textBoxPanel2.setMaximumSize(this.getSize());
//        textBoxPanel2.add(m_lblInputFingerNum);
//        textBoxPanel2.add(m_txtFingerNum);
//        textBoxPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
//        textBoxPanel2.setAlignmentY(Component.CENTER_ALIGNMENT);
//        card2.add(textBoxPanel2);
//        card2.add(new JButton("Press ENTER to Enroll"));
//        card2.setOpaque(false);
        
        JPanel card3 = new JPanel();
        card3.setSize(this.getSize());
        m_lblEnrollmentStatus = new JLabel("Enrollment:");
        m_lblEnrollmentStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_lblEnrollmentStatus.setOpaque(false);
        m_lblEnrollmentStatus.setForeground(Color.red);
        
        m_txtEnrollmentResult = new JTextPane();
        m_txtEnrollmentResult.setContentType("text/html");
        m_txtEnrollmentResult.setFont(new Font("Times", Font.ROMAN_BASELINE, 12));
        m_txtEnrollmentResult.setEditable(false);
        m_txtEnrollmentResult.setOpaque(false);
        m_txtEnrollmentResult.setForeground(Color.white);
        m_txtEnrollmentResult.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel textBoxPanel3 = new JPanel();
        textBoxPanel3.setSize(m_width, m_height);
        textBoxPanel3.setLayout(new BoxLayout(textBoxPanel3, BoxLayout.Y_AXIS));
        textBoxPanel3.add(Box.createVerticalStrut(10));
        textBoxPanel3.add(m_lblEnrollmentStatus);
        textBoxPanel3.add(Box.createVerticalStrut(5));
        textBoxPanel3.add(m_txtEnrollmentResult);
		
        card3.setName(PANEL_STATUS);
        textBoxPanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        textBoxPanel3.setAlignmentY(Component.CENTER_ALIGNMENT);
        textBoxPanel3.setOpaque(false);
        card3.add(textBoxPanel3);
        card3.setOpaque(false);
         
        //Create the panel that contains the "cards".
        m_cl = new CardLayout();
        setLayout(m_cl);
        add(card1,PANEL_ID);
        //add(card2,PANEL_FPN);
        add(card3,PANEL_STATUS);
    }

	@Override
	public void setStepCount(int count) {
		// TODO Auto-generated method stub
		//System.out.println("Start");
		System.out.println("Please place finger "+ count + " times");
		m_lblEnrollmentStatus.setText("\nPlease place finger "+ count + " times");
		
	}

	@Override
	public void onReadyForFinger(int step, boolean repeatOnReaderError) {
		// TODO Auto-generated method stub
		switch(step){
		case 1:
			//MainWindow.placefingersound.start();
			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
			m_lblEnrollmentStatus.setText("\nPlease place finger Step: "+ step);
			break;
		case 2:
			//MainWindow.placefingeragainsound.start();
			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
			m_lblEnrollmentStatus.setText("\nPlease place finger Step: "+ step );
			break;
		case 3:
			//MainWindow.placefingeragainsound.start();
			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
			m_lblEnrollmentStatus.setText("\nPlease place finger Step: "+ step );
			break;
		}
		
	}

	@Override
	public void onFingerPrintRead(int step) {
		// TODO Auto-generated method stub
		//MainWindow.successbuzzersound.start();
		System.out.println("Please Remove Finger Step: "+ step + "  ");
		m_lblEnrollmentStatus.setText("\nPlease Remove finger Step: "+ step + " ");
		
	}
	
	public void goDemo() {
		m_cl.first(this);
		setFocusable(true);
		if (! m_txtEmployeeNum.requestFocusInWindow()){
    		m_txtEmployeeNum.requestFocus();
    	}
		//System.out.println("goEnroll!");
//		setInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, getInputMap());
//	    KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
//	    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, "pressed");
//	    getActionMap().put("pressed", new AbstractAction(){         
//
//			public void actionPerformed(ActionEvent arg0) {
//				m_strCurrentCardName = FingerPrintEnrollForm.this.getCurrentCardName();
//	            //System.out.println("Key Enter pressed, I am in "+m_strCurrentCardName);
//	            FingerPrintEnrollForm.this.handleAction();
//			} 
//	    });
	}
	
	
	
	private String getCurrentCardName(){
		for (Component comp : this.getComponents()) {
			if (comp.isVisible()) {
				String myName = comp.getName();
				return myName;
			}
		}
		return "unknown";
	}
	
	private void handleAction(){
		m_strCurrentCardName = FingerPrintEnrollForm.this.getCurrentCardName();
		switch(CardNames.valueOf(m_strCurrentCardName)){
        case Input_Employee_ID:
        	m_strEmployeeNum = m_txtEmployeeNum.getText();
        	if (m_strEmployeeNum != null){
        		m_cl.next(FingerPrintEnrollForm.this);
        		m_lblEnrollmentStatus.setText("Employee ID: "+m_strEmployeeNum+" Finger: "+m_nFingerNum);
        		if (! m_lblEnrollmentStatus.requestFocusInWindow()){
            		m_lblEnrollmentStatus.requestFocus();
            	}
        		(new FingerPrintEnroll()).execute(); //Swing worker class.
        	}
        	break;
//        case Input_FingerPrint_Number:
//        	String strFingerNum = m_txtFingerNum.getText();
//        	if (strFingerNum != null){
//        		m_nFingerNum = Integer.parseInt(strFingerNum);
//        		m_cl.next(FingerPrintEnrollForm.this);
//        		m_lblEnrollmentStatus.setText("Employee ID: "+m_strEmployeeNum+" Finger: "+m_nFingerNum);
//        		if (! m_lblEnrollmentStatus.requestFocusInWindow()){
//            		m_lblEnrollmentStatus.requestFocus();
//            	}
//        		(new FingerPrintEnroll()).execute(); //Swing worker class.
//        	}
//        	break;
        case Enrollment_Status:
        	clearForm();
        	m_cl.first(FingerPrintEnrollForm.this);
        	if (! m_txtEmployeeNum.requestFocusInWindow()){
        		m_txtEmployeeNum.requestFocus();
        	}
        	break;
        default:
        	System.out.println("I am in default state.");
        	m_cl.first(FingerPrintEnrollForm.this);		
		}
	}

	private void clearForm() {
		System.out.println("I am clearing form...");
		m_txtEmployeeNum.setText("");
		//m_txtFingerNum.setText("");
		m_txtEnrollmentResult.setText("");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(this.toString()+"key typed"+e.getKeyCode());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(this.toString()+"key pressed"+e.getKeyCode());
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println(this.toString()+"key released"+e.getKeyCode());
		if( KeyEvent.VK_F7 == e.getKeyCode()){
			FingerPrintEnrollForm.this.clearForm();
	        m_mw.returnToMain();
		}
		if (KeyEvent.VK_ENTER == e.getKeyCode()){
			handleAction();
		}
		
		else {
			m_mw.getM_cel().handlekeyPressed(e);
		}
		
	}
}
