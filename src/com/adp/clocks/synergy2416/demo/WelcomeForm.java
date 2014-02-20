package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;


public class WelcomeForm extends JPanel {
	

	private static final long serialVersionUID = 319089484632562510L;
	private  JLabel m_lblWelcomeLabel;
	private JPanel m_pMsg;
	private MainWindow m_mw;
	private AtomicInteger m_nIdThread;
	private boolean m_bRunIdThread;
	private IdentifyEmployeeWorker m_idEmpWorker;
	private static Timer m_idThreadTimer;
		
	/**
	 * Initialize the contents of the frame.
	 * @param mw 
	 */
	public WelcomeForm(MainWindow mw) {
		m_nIdThread = new AtomicInteger(0);
		m_bRunIdThread = true;
		this.m_mw = mw;
		this.setLayout(new BorderLayout());
		addComponentsToPane();
		this.setOpaque(false);
		updateLabel();
		
		m_idThreadTimer = new Timer(8000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            runIdThreadAgain();
	        }
	    });
		
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println("Pressed"+" " +e.getKeyCode());
				if( KeyEvent.VK_F6 == e.getKeyCode()) {
					runIdThreadAgain();
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
	              System.out.println(WelcomeForm.this.toString()+"Focus GAINED:"+e);
	              runIdThreadAgain();
	          }
	          public void focusLost(FocusEvent e){
	              System.out.println(WelcomeForm.this.toString()+"Focus LOST:"+e);
	              m_bRunIdThread = false;
	              if (m_idThreadTimer != null && m_idThreadTimer.isRunning()){
	            	  m_idThreadTimer.stop();
	              }
	              if (m_idEmpWorker != null && ! m_idEmpWorker.isDone()) {
	            	  m_idEmpWorker.cancel(true);
	              }
	          }
	      });
		
	}
	
	public void updateLabel(){
		//m_mw.getM_ec().getM_lblInstruction().setText("<html><font color=black>Press <font color=red>F1-F4 </font> key to start demo funtions</font> </html>");
		for (Component c : m_mw.getM_ec().getM_pMenuBar().getComponents()){
    	        c.setVisible(true);
    	}
		//m_lblStatusLabel.setVisible(false);
		//m_lblWelcomeLabel.setVisible(! m_lblStatusLabel.isVisible());
		m_lblWelcomeLabel.setText("<html><font color = Black size=20><b>Welcome!</b></font></html>");
		//m_lblStatusLabel.setText("");
		FPU.Light.GREEN.off();
     	FPU.Light.RED.off();
	}
	private void addComponentsToPane() {
		createWelcomeLabel();
		m_pMsg = new JPanel();
		m_pMsg.setSize(320, 200);
		m_pMsg.setLayout(new BoxLayout(m_pMsg, BoxLayout.Y_AXIS));
		m_pMsg.add(Box.createVerticalStrut(25));
		m_pMsg.add(m_lblWelcomeLabel);
		m_pMsg.setOpaque(false);
		add(m_pMsg,BorderLayout.CENTER);
	}

	private void createWelcomeLabel(){
		m_lblWelcomeLabel = new JLabel("Welcome");
		m_lblWelcomeLabel.setFont(new Font("Times", Font.PLAIN, 25));
		m_lblWelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setOpaque(false);
	}
	
	class IdentifyEmployeeWorker extends SwingWorker<String, Void> {
        @Override
        public String doInBackground() {
        	m_nIdThread.getAndIncrement();
            return FPU.identifyEmployee();
        }

        @Override
        protected void done() {
            try { 
            	String strResult = get();
            	System.out.println(strResult);
            	if (strResult.compareTo("Succeed!") == 0) {
                  	m_lblWelcomeLabel.setText("<html><font color=black>Punch Accepted!</font></html>");
                  	m_lblWelcomeLabel.repaint();
                  	FPU.Light.RED.off();
                  	FPU.Light.GREEN.on();
                  	MainWindow.getM_ap().playSuccessSound();
                  } else {
                  	m_lblWelcomeLabel.setText("<html><font color=black>"+strResult+"</font></html>");
                	m_lblWelcomeLabel.repaint();
                  	FPU.Light.GREEN.off();
                  	FPU.Light.RED.on();
                  	MainWindow.getM_ap().playKeypadSound();
                  }
            } catch (InterruptedException e) {
            	m_nIdThread.getAndDecrement();
                // This is thrown if the thread's interrupted.
            } catch (ExecutionException e) {
            	m_nIdThread.getAndDecrement();
                // This is thrown if we throw an exception
                // from doInBackground.
            } catch (CancellationException e) {
                // Do your task after cancellation
            	m_nIdThread.getAndDecrement();
            }
            m_nIdThread.getAndDecrement();
            m_idThreadTimer.start(); 
        }
    }
	
	protected void runIdThreadAgain() {
		if (m_bRunIdThread && m_nIdThread.get()==0) {
			updateLabel();
			IdentifyEmp();
		}
	}
	
	private void IdentifyEmp(){
		m_idEmpWorker = new IdentifyEmployeeWorker();
		m_idEmpWorker.execute();
	}
}
