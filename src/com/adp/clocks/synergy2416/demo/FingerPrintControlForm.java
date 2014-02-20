package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FingerPrintControlForm extends JPanel {

	
	private static final long serialVersionUID = -5200775456600807554L;
	private static final String ResPath = "/com/adp/clocks/synergy2416/res/";
	private boolean m_bRunIdControlThread;
	private MainWindow m_mw;
	private JLabel m_lblStatusLabel;
	private JPanel m_pMsg;
	private static Timer m_idControlTimer;
	private FPIdWorker m_idCtlWorker;
	private ImageIcon m_icon;
	
	/**
	 * Initialize the contents of the frame.
	 * @param mw 
	 */
	public FingerPrintControlForm(MainWindow mw) {
		this.m_mw = mw;
		this.setLayout(new BorderLayout());
		addComponentsToPane();
		this.setOpaque(false);
		m_idControlTimer = new Timer(8000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            runIdControlThreadAgain();
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
					runIdControlThreadAgain();
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
	        	  m_bRunIdControlThread = true;
	        	  //runIdControlThreadAgain();
	          }
	          public void focusLost(FocusEvent e){
	             
	              m_bRunIdControlThread = false;
	            
	              if (m_idCtlWorker != null && ! m_idCtlWorker.isDone()){
	            	  m_idCtlWorker.cancel(true);
	              }
	              if (m_idControlTimer != null && m_idControlTimer.isRunning()){
	            	  m_idControlTimer.stop();
	              }
	          }
	      });
		
	}
	
	protected void runIdControlThreadAgain() {
		System.out.println("m_nThread is "+FPIdWorker.m_nThread.get());
		if (m_bRunIdControlThread && FPIdWorker.m_nThread.get()==0) {
			updateLabel();
			IdControl();
		}
		
	}

	private void addComponentsToPane() {
		// TODO Auto-generated method stub
		createStatusLabel();
		m_pMsg = new JPanel();
		m_pMsg.setSize(300, 200);
		m_pMsg.setLayout(new BoxLayout(m_pMsg, BoxLayout.Y_AXIS));
		//m_pMsg.add(m_lblWelcomeLabel);
		m_pMsg.add(Box.createVerticalStrut(15));
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
		m_lblStatusLabel.setText("<html><font color=black>Please Place Finger</font></html>");
		FPU.Light.GREEN.off();
     	FPU.Light.RED.off();
	}
	
	
	public void IdControl() {
		m_idCtlWorker = new FPIdWorker(m_lblStatusLabel,m_idControlTimer);	
		m_idCtlWorker.execute();
	}
	
	private void createStatusLabel(){
		m_icon = createImageIcon(ResPath+"gif_door_in.gif",
                "a door open/close gif file");
		m_lblStatusLabel = new JLabel("Press Finger to Open the Door!");
		m_lblStatusLabel.setIcon(m_icon);
		m_lblStatusLabel.setFont(new Font("Times", Font.PLAIN, 20));
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
