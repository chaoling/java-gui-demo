package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;


public class WelcomeForm extends JPanel {
	

	private static final long serialVersionUID = 319089484632562510L;
	private  JLabel m_lblWelcomeLabel;
	private JLabel m_lblStatusLabel;
	private JPanel m_pMsg;
	private MainWindow m_mw;
	private AtomicInteger m_idThread;
	private boolean m_bRunIdThread;
	//private Timer m_idThreadTimer;
	
//	class IdTask extends TimerTask {
//		public void run() {
//			m_idThreadTimer.cancel();
//			IdentifyEmp();
//		}
//	}
	/**
	 * Initialize the contents of the frame.
	 * @param mw 
	 */
	public WelcomeForm(MainWindow mw) {
		m_idThread = new AtomicInteger(0);
		m_bRunIdThread = true;
		//m_idThreadTimer = new Timer();
		this.m_mw = mw;
		this.setLayout(new BorderLayout());
		addComponentsToPane();
		this.setOpaque(false);
		updateLabel();
		
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println("Pressed"+" " +e.getKeyCode());
				if( KeyEvent.VK_F6 == e.getKeyCode()) {
					updateLabel();
					IdentifyEmp();
				}
				else {
					m_mw.getM_cel().handlekeyPressed(e);
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		  this.addFocusListener(new FocusListener(){
	          public void focusGained(FocusEvent e){
	        	  m_bRunIdThread = true;
	        	  IdentifyEmp();
	              System.out.println(WelcomeForm.this.toString()+"Focus GAINED:"+e);
	          }
	          public void focusLost(FocusEvent e){
	              System.out.println(WelcomeForm.this.toString()+"Focus LOST:"+e);
	              m_bRunIdThread = false;
	              FPU.Light.GREEN.off();
              	  FPU.Light.RED.off();
	          }
	      });
		
	}
	
	public void updateLabel(){
		//m_mw.getM_ec().getM_lblInstruction().setText("<html><font color=black>Press <font color=red>F1-F4 </font> key to start demo funtions</font> </html>");
		for (Component c : m_mw.getM_ec().getM_pMenuBar().getComponents()){
    	        c.setVisible(true);
    	}
		m_lblStatusLabel.setVisible(false);
		m_lblWelcomeLabel.setVisible(! m_lblStatusLabel.isVisible());
		m_lblWelcomeLabel.setText("<html><font color = Black><b>Welcome!</b></font></html>");
		m_lblStatusLabel.setText("");
		FPU.Light.GREEN.off();
     	FPU.Light.RED.off();
	}
	private void addComponentsToPane() {
		createWelcomeLabel();
		createStatusLabel();
		m_pMsg = new JPanel();
		m_pMsg.setSize(320, 220);
		m_pMsg.setLayout(new BoxLayout(m_pMsg, BoxLayout.Y_AXIS));
		m_pMsg.add(Box.createVerticalStrut(20));
		m_pMsg.add(m_lblWelcomeLabel);
		m_pMsg.add(Box.createVerticalStrut(5));
		m_pMsg.add(m_lblStatusLabel);
		m_pMsg.setOpaque(false);
		add(m_pMsg,BorderLayout.CENTER);
	}

	private void createWelcomeLabel(){
		m_lblWelcomeLabel = new JLabel();
		m_lblWelcomeLabel.setFont(new Font("Times", Font.PLAIN, 25));
		m_lblWelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setOpaque(false);
	}
	private void createStatusLabel(){
		m_lblStatusLabel = new JLabel();
		m_lblStatusLabel.setFont(new Font("Times", Font.PLAIN, 12));
		m_lblStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblStatusLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblStatusLabel.setOpaque(false);
	}
	
	class IdentifyEmployee extends SwingWorker<String, Object> {
        @Override
        public String doInBackground() {
        	//return "Success";
        	m_idThread.getAndIncrement();
            return FPU.identifyEmployee();
            //return FPU.enrollCount();
        }

        @Override
        protected void done() {
            try { 
            	String strResult = get();
            	System.out.println(strResult);
            	m_lblWelcomeLabel.setVisible(false);
            	m_lblStatusLabel.setVisible(true);
            	if (strResult.compareTo("Succeed!") == 0) {
                  	FPU.Light.RED.off();
                  	FPU.Light.GREEN.on();
                  	m_lblStatusLabel.setText("<html><font color=black>Punch Accepted!<br>Press OUT to restart punch</font></html>");
                  	//m_lblStatusLabel.setVisible(true);
                  	MainWindow.getM_ap().playSuccessSound();
                  } else {
                  	
                  	m_lblStatusLabel.setText("<html><font color=black>"+strResult+"</font></html>");
                  	FPU.Light.GREEN.off();
                  	FPU.Light.RED.on();
                  	MainWindow.getM_ap().playKeypadSound();
                  }
            } catch (Exception ignore) {
            	
            }
            
            //m_lblWelcomeLabel.setVisible(true);
            //updateLabel();
            m_idThread.getAndDecrement();
            IdentifyEmp();
        }
    }
	
	public void IdentifyEmp(){
		System.out.println("run IdEmp... m_bRunidThread is"+m_bRunIdThread);
		 try {
				Thread.sleep(1000*5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//while (m_bRunIdThread){
			if ( m_bRunIdThread && m_idThread.get() == 0 ){
				(new IdentifyEmployee()).execute();
			}
		//}
	}
}
