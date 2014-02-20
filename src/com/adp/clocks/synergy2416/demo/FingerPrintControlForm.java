package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class FingerPrintControlForm extends JPanel {

	private static final String ResPath = "/com/adp/clocks/synergy2416/res/";
	private static final long serialVersionUID = -5200775456600807554L;
	private AtomicInteger m_idThread;
	private boolean m_bRunIdThread;
	private MainWindow m_mw;
	private JLabel m_lblStatusLabel;
	private JLabel m_lblWelcomeLabel;
	private JPanel m_pMsg;
	
	/**
	 * Initialize the contents of the frame.
	 * @param mw 
	 */
	public FingerPrintControlForm(MainWindow mw) {
		m_idThread = new AtomicInteger(0);
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
				//System.out.println("Pressed"+" " +e.getKeyCode());
				if( KeyEvent.VK_F6 == e.getKeyCode()) {
					updateLabel();
					goDemo();
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
	        	  goDemo();
	             
	          }
	          public void focusLost(FocusEvent e){
	             
	              m_bRunIdThread = false;
	              FPU.Light.GREEN.off();
              	  FPU.Light.RED.off();
	          }
	      });
		
	}
	
	private void addComponentsToPane() {
		// TODO Auto-generated method stub
		createWelcomeLabel();
		createStatusLabel();
		m_pMsg = new JPanel();
		//m_pMsg.setSize(100, 100);
		m_pMsg.setLayout(new BoxLayout(m_pMsg, BoxLayout.Y_AXIS));
		m_pMsg.add(m_lblWelcomeLabel);
		m_pMsg.add(Box.createVerticalStrut(5));
		m_pMsg.add(m_lblStatusLabel);
		m_pMsg.setOpaque(false);
		add(m_pMsg,BorderLayout.CENTER);
		this.setOpaque(false);
	}

	public void updateLabel(){
		m_mw.getM_ec().getLblVideoImage().setVisible(false);
		m_mw.getM_ec().getLblCameraImage().setVisible(false);
		m_mw.getM_ec().getLblFpuEnrollImage().setVisible(false);
		m_mw.getM_ec().getLblFpuControlImage().setVisible(true);
   
		m_lblStatusLabel.setVisible(false);
		m_lblWelcomeLabel.setVisible(! m_lblStatusLabel.isVisible());
		m_lblWelcomeLabel.setText("<html><font color = Black size = 8>Press Finger to Open the Door!</font></html>");
		m_lblStatusLabel.setText("");
		FPU.Light.GREEN.off();
     	FPU.Light.RED.off();
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
                  	m_lblStatusLabel.setText("<html><font color=black size=12>Punch Accepted!<br>Opening the Door!</font></html>");
                  	MainWindow.getM_ap().playSuccessSound();
                  } else {
                  	m_lblStatusLabel.setText("<html><font color=black size=10>"+strResult+"</font></html>");
                  	FPU.Light.GREEN.off();
                  	FPU.Light.RED.on();
                  	MainWindow.getM_ap().playKeypadSound();
                  }
            } catch (Exception ignore) {
            	
            }
            //updateLabel();
            m_idThread.getAndDecrement();
            goDemo(); //rewire
        }
    }
	
	public void goDemo() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000*5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateLabel();
		if (m_bRunIdThread){
			if ( m_idThread.get() == 0 ){
				(new IdentifyEmployee()).execute();
			}	
		}
	}
	
	private void createWelcomeLabel(){
		m_lblWelcomeLabel = new JLabel("Press Finger to Open the Door");
		m_lblWelcomeLabel.setFont(new Font("Times", Font.PLAIN, 12));
		m_lblWelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setOpaque(false);
	}
	private void createStatusLabel(){
		ImageIcon icon = createImageIcon(ResPath+"gif_door_in.gif",
                "a door open/close gif file");
		m_lblStatusLabel = new JLabel("Image and Text", icon, JLabel.CENTER);
		m_lblStatusLabel.setFont(new Font("Times", Font.PLAIN, 12));
		m_lblStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblStatusLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblStatusLabel.setOpaque(false);
	}
	
	protected ImageIcon createImageIcon(String path, String description) {
				java.net.URL imgURL = getClass().getResource(path);
				if (imgURL != null) {
					return new ImageIcon(imgURL, description);
				} else {
					System.err.println("Couldn't find file: " + path);
					return null;
				}
	}
}
