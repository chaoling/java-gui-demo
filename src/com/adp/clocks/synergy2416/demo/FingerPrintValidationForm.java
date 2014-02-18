package com.adp.clocks.synergy2416.demo;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;

import com.adp.clocks.synergy2416.demo.ClockEventDispatcher.CLOCK_STATUS;

public class FingerPrintValidationForm extends JPanel implements Employee {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static String DEMO_ID = "Demo_Employee_ID";
    //final static String DEMO_FPN = "Demo_FingerPrint_Number";
    final static String DEMO_STATUS = "Demo_Status";
    
	private JTextField m_txtEmployeeNum;
	private JLabel m_lblInputEmployeeNum;
	//private JLabel m_lblInputFingerNum;
	//private JTextField m_txtFingerNum;
	private JLabel m_lblStatus;
    private String m_strEmployeeNum;
    private int m_nFingerNum = 0; //For demo, always asume finger 0
    private CardLayout m_cl;
    private String m_strCurrentCardName;
	private JTextPane m_txtResult;
	private final int m_width=320;
	private final int m_height=240;
	private MainWindow m_mw;
	
	public enum CardNames {
    	Demo_Employee_ID,
    	//Demo_FingerPrint_Number,
    	Demo_Status
    }
    
    class FingerPrintValidation extends SwingWorker<String, Object> {
        @Override
        public String doInBackground() {
            return FPU.validateEmployee(m_strEmployeeNum, m_nFingerNum);
        }

        @Override
        protected void done() {
            try { 
            	String strResult = get();
                m_lblStatus.setText("Employee ID: "+m_strEmployeeNum+" Finger: "+m_nFingerNum);
                m_txtResult.setText("<html><font color=black>"+strResult+"<br> Press Enter to Restart "+"<br> Press MENU to the main menu</font><html>");
                
                if (strResult.compareTo("Succeed!") == 0) {
                	FPU.Light.RED.off();
                	FPU.Light.GREEN.on();
                } else {
                	FPU.Light.GREEN.off();
                	FPU.Light.RED.on();
                }
            } catch (Exception ignore) {
            	
            }
        }
    }
    
    class FingerPrintEmployeeStatus extends SwingWorker<String, Object> {
        @Override
        public String doInBackground() {
            return FPU.badgeStatus(m_strEmployeeNum, m_nFingerNum);
        }

        @Override
        protected void done() {
            try { 
            	String strResult = get();
                m_lblStatus.setText("Employee ID: "+m_strEmployeeNum+" Finger: "+m_nFingerNum);
                if (strResult.compareTo("Succeed!") == 0) {
                	 m_txtResult.setText("<html><font color=black>Press Finger on The FP reader to Verify </font><html>");
                	FPU.Light.RED.off();
                	FPU.Light.GREEN.on();
                	(new FingerPrintValidation()).execute(); //only run the validation thread when there is valid badge number.
                	//MainWindow.enrollsound.start();
                } else {
                	m_txtResult.setText("<html><font color=black>"+strResult+"<br> Press Enter to Restart "+"<br> Press MENU to the main menu</font><html>");
                	FPU.Light.GREEN.off();
                	FPU.Light.RED.on();
                	//MainWindow.beepsound.start();
                }
            } catch (Exception ignore) {
            	
            }
        }
    }
    
    public FingerPrintValidationForm(MainWindow mw) {
    	this.m_mw = mw;
    	setSize(m_width, m_height);
    	addComponentToPane();
    	setOpaque(false);
    	this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Pressed"+" " +e.getKeyCode());
				
				if( KeyEvent.VK_F7 == e.getKeyCode()){
					FingerPrintValidationForm.this.clearForm();
			        m_mw.getM_cel().emit(CLOCK_STATUS.CLOCKSTATUS_MENU);
			        
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
    	
    	this.addFocusListener(new FocusListener(){
	          public void focusGained(FocusEvent e){
	              System.out.println(this.toString()+"Focus GAINED:"+e);
	          }
	          public void focusLost(FocusEvent e){
	        	  FingerPrintValidationForm.this.clearForm();
	              System.out.println(this.toString()+"Focus LOST:"+e);
	              
	          }
	      });
    }
    
    public void updateLabel()
    {
    	m_mw.getM_ec().getM_lblInstruction().setText("<html><font color=black>Finger Print Verification</font> </html>");
    }

	public void addComponentToPane() {
         
        //Create the "cards".
        JPanel card1 = new JPanel();
        m_lblInputEmployeeNum = new JLabel("Enter Employee Number:");
        m_txtEmployeeNum = new JTextField(12);
        card1.setName(DEMO_ID);
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
//        card2.setName(DEMO_FPN);
//        JPanel textBoxPanel2 = new JPanel();
//        textBoxPanel2.setMaximumSize(this.getSize());
//        textBoxPanel2.add(m_lblInputFingerNum);
//        textBoxPanel2.add(m_txtFingerNum);
//        textBoxPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
//        textBoxPanel2.setAlignmentY(Component.CENTER_ALIGNMENT);
//        card2.add(textBoxPanel2);
//        card2.add(new JButton("Press ENTER to Confirm"));
//        card2.setOpaque(false);
        
        JPanel card3 = new JPanel();
        card3.setSize(this.getSize());
        m_lblStatus = new JLabel("Status:");
        m_lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        m_lblStatus.setForeground(Color.red);
        m_lblStatus.setOpaque(false);
        m_txtResult = new JTextPane();
        m_txtResult.setContentType("text/html"); 
        m_txtResult.setFont(new Font("Times", Font.ROMAN_BASELINE, 12));
        m_txtResult.setForeground(Color.black);
//        m_txtResult.setLineWrap(true);
//        m_txtResult.setWrapStyleWord(true);
        m_txtResult.setEditable(false);
        m_txtResult.setOpaque(false);
        m_txtResult.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel textBoxPanel3 = new JPanel();
        textBoxPanel3.setSize(m_width, m_height);
        textBoxPanel3.setLayout(new BoxLayout(textBoxPanel3, BoxLayout.Y_AXIS));
        textBoxPanel3.add(Box.createGlue());
        textBoxPanel3.add(m_lblStatus);
        textBoxPanel3.add(Box.createVerticalStrut(5));
        textBoxPanel3.add(m_txtResult);
		
        card3.setName(DEMO_STATUS);
        textBoxPanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        textBoxPanel3.setAlignmentY(Component.CENTER_ALIGNMENT);
        textBoxPanel3.setOpaque(false);
        card3.add(textBoxPanel3);
        card3.setOpaque(false);
         
        //Create the panel that contains the "cards".
        m_cl = new CardLayout();
        setLayout(m_cl);
        add(card1,DEMO_ID);
        //add(card2,DEMO_FPN);
        add(card3,DEMO_STATUS);
    }

	@Override
	public void setBadge(String badge) {
		// TODO Auto-generated method stub
		System.out.println("Employee Identified: "+badge);
		m_lblStatus.setText("Employee Identified: "+ "<html><font Color=red>"+ badge +"</font></html>");
		
	}

//	public void setStepCount(int count) {
//		// TODO Auto-generated method stub
//		System.out.println("Start");
//		System.out.println("Please place finger "+ count + " times");
//		m_lblStatus.setText("\nPlease place finger "+ count + " times");
//		
//	}
//
//	public void onReadyForFinger(int step, boolean repeatOnReaderError) {
//		// TODO Auto-generated method stub
//		switch(step){
//		case 1:
//			//MainWindow.placefingersound.start();
//			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
//			m_lblStatus.setText("\nPlease place finger Step: "+ step);
//			break;
//		case 2:
//			//MainWindow.placefingeragainsound.start();
//			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
//			m_lblStatus.setText("\nPlease place finger Step: "+ step );
//			break;
//		case 3:
//			//MainWindow.placefingeragainsound.start();
//			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
//			m_lblStatus.setText("\nPlease place finger Step: "+ step );
//			break;
//		}
//		
//	}
//
//	public void onFingerPrintRead(int step) {
//		// TODO Auto-generated method stub
//		//MainWindow.successbuzzersound.start();
//		System.out.println("Please Remove Finger Step: "+ step + "  ");
//		m_lblStatus.setText("\nPlease Remove finger Step: "+ step + " ");
//		
//	}
	
	@SuppressWarnings("serial")
	public void goDemo() {
		m_cl.first(this);
		setFocusable(true);
		if (! m_txtEmployeeNum.requestFocusInWindow()){
    		m_txtEmployeeNum.requestFocus();
    	}
		System.out.println("goDemo!");
		setInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, getInputMap());
	    KeyStroke key_enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
	    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key_enter, "enter_pressed");
	    getActionMap().put("enter_pressed", new AbstractAction(){         

			public void actionPerformed(ActionEvent arg0) {
				m_strCurrentCardName = FingerPrintValidationForm.this.getCurrentCardName();
	            System.out.println("Key Enter pressed, I am in "+m_strCurrentCardName);
	            FingerPrintValidationForm.this.handleAction();
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
		return "unknown";
	}
	
	private void handleAction(){
		switch(CardNames.valueOf(m_strCurrentCardName)){
        case Demo_Employee_ID:
        	m_strEmployeeNum = m_txtEmployeeNum.getText();
        	if (m_strEmployeeNum != null){
        		m_cl.next(FingerPrintValidationForm.this);
        		m_lblStatus.setText("Employee ID: "+m_strEmployeeNum+" Finger: "+m_nFingerNum);
        		if (! m_lblStatus.requestFocusInWindow()){
            		m_lblStatus.requestFocus();
            	}
        		(new FingerPrintEmployeeStatus()).execute(); //Swing worker class.
        	}
        	break;
//        case Demo_FingerPrint_Number:
//        	String strFingerNum = m_txtFingerNum.getText();
//        	if (strFingerNum != null){
//        		m_nFingerNum = Integer.parseInt(strFingerNum);
//        		m_cl.next(FingerPrintValidationForm.this);
//        		m_lblStatus.setText("Employee ID: "+m_strEmployeeNum+" Finger: "+m_nFingerNum);
//        		if (! m_lblStatus.requestFocusInWindow()){
//            		m_lblStatus.requestFocus();
//            	}
//        		
//        		(new FingerPrintEmployeeStatus()).execute(); //Swing worker class.
//        	}
//        	break;
        case Demo_Status:
        	clearForm();
        	m_cl.first(FingerPrintValidationForm.this);
        	if (! m_txtEmployeeNum.requestFocusInWindow()){
        		m_txtEmployeeNum.requestFocus();
        	}
        	break;
        default:
        	System.out.println("I am in default state.");
        	m_cl.first(FingerPrintValidationForm.this);		
		}
	}

	private void clearForm() {
		System.out.println("I am clearing form...");
		m_txtEmployeeNum.setText("");
		//m_txtFingerNum.setText("");
		m_txtResult.setText("");
	}
}

