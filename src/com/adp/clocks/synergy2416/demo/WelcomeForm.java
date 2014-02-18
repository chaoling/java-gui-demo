package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.adp.clocks.synergy2416.demo.ClockEventDispatcher.CLOCK_STATUS;


public class WelcomeForm extends JPanel implements FingerPrintEnrollmentHandler, Employee{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 319089484632562510L;
	/**
	 * 
	 */
	private  JLabel m_lblWelcomeLabel;
	private MainWindow m_mw;
	
	/**
	 * Initialize the contents of the frame.
	 * @param mw 
	 */
	public WelcomeForm(MainWindow mw) {
		this.m_mw = mw;
		this.setLayout(new BorderLayout());
		addComponentsToPane();
		this.setOpaque(false);
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Pressed"+" " +e.getKeyCode());
				
				if( KeyEvent.VK_F7 == e.getKeyCode()){
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
	              System.out.println(WelcomeForm.this.toString()+"Focus GAINED:"+e);
	          }
	          public void focusLost(FocusEvent e){
	              System.out.println(WelcomeForm.this.toString()+"Focus LOST:"+e);
	              FPU.Light.GREEN.off();
              	  FPU.Light.RED.off();
	          }
	      });
		
	}
	
	public void updateLabel(){
		m_mw.getM_ec().getM_lblInstruction().setText("<html><font color='yellow'>Press <font color='red'>F1-F4 </font> key to start demo funtions</font> </html>");
		m_lblWelcomeLabel.setText("<html><font color = Yellow><b>Welcome!</b></font></html>");
	}
	private void addComponentsToPane() {
		createWelcomeLabel();
		add(m_lblWelcomeLabel,BorderLayout.CENTER);
		m_lblWelcomeLabel.setText("<html><font color = Yellow><b>Welcome!</b></font></html>");
		
		
		//UpdateClock.initialize(m_lblTimeLabel);
	}

	private void createWelcomeLabel(){
		m_lblWelcomeLabel = new JLabel();
		m_lblWelcomeLabel.setFont(new Font("Times", Font.PLAIN, 25));
		m_lblWelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setOpaque(false);
	}
	
	class IdentifyEmployee extends SwingWorker<String, Object> {
        @Override
        public String doInBackground() {
        	//return "Success";
            return FPU.identifyEmployee();
            //return FPU.enrollCount();
        }

        @Override
        protected void done() {
            try { 
            	String strResult = get();
            	System.out.println(strResult);
            	  if (strResult.compareTo("Succeed!") == 0) {
                  	
                  	FPU.Light.RED.off();
                  	FPU.Light.GREEN.on();
                  	m_lblWelcomeLabel.setText("<html><font color=white size=10>"+strResult+"</font></html>");
                  	m_lblWelcomeLabel.requestFocusInWindow();
                  	MainWindow.getM_ap().playEnrollSound();
                  } else {
                  	
                  	m_lblWelcomeLabel.setText("<html><font color=white size=10>"+strResult+"</font></html>");
                  	FPU.Light.GREEN.off();
                  	FPU.Light.RED.on();
                  	m_lblWelcomeLabel.requestFocusInWindow();
                  	MainWindow.getM_ap().playKeypadSound();
                  }
            } catch (Exception ignore) {
            	
            }
        }
    }
	
	public void IdentifyEmp(){
		(new IdentifyEmployee()).execute();
	}
	
	@Override
	public void setBadge(String badge) {
		// TODO Auto-generated method stub
		System.out.println("Badge Identified: "+badge);
		m_lblWelcomeLabel.setText("Badge Identified: "+ "<html><font Color=red>"+ badge +"</font></html>");
		
	}

	public void setStepCount(int count) {
		// TODO Auto-generated method stub
		m_lblWelcomeLabel.setText("\nPlease place finger "+ count + " times");
		
	}

	public void onReadyForFinger(int step, boolean repeatOnReaderError) {
		// TODO Auto-generated method stub
		switch(step){
		case 1:
			//MainWindow.getM_ap().playPlaceFingerSound();
			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
			m_lblWelcomeLabel.setText("\nPlease place finger Step: "+ step);
			break;
		case 2:
			//MainWindow.getM_ap().playplaceFingerAgainSound();
			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
			m_lblWelcomeLabel.setText("\nPlease place finger Step: "+ step );
			break;
		case 3:
			//MainWindow.getM_ap().playplaceFingerAgainSound();
			System.out.println("Please place finger Step: "+ step + " Error: " + repeatOnReaderError);
			m_lblWelcomeLabel.setText("\nPlease place finger Step: "+ step );
			break;
		}
		
	}

	public void onFingerPrintRead(int step) {
		// TODO Auto-generated method stub
		MainWindow.getM_ap().playSuccessSound();
		System.out.println("Please Remove Finger Step: "+ step + "  ");
		m_lblWelcomeLabel.setText("\nPlease Remove finger Step: "+ step + " ");
		
	}

}
