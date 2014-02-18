package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.adp.clocks.synergy2416.demo.ClockEventDispatcher.CLOCK_STATUS;

public class VideoForm extends javax.swing.JPanel {
	

	private static final long serialVersionUID = 4573547614726754239L;
	private MainWindow m_mw;
	private int m_instance = 0; //counter to keep track of number of video play event at any moment
	private JDialog m_jd;
	private int m_width = 320;
	private int m_height = 220;
	private JLabel m_lblInstructVideo;
	
	public VideoForm(MainWindow mw) {
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
				//MainWindow.getM_ap().playBeepSound();
				//System.out.println("Pressed"+" " +e.getKeyCode());
				
				if(KeyEvent.VK_ENTER == e.getKeyCode() && m_instance == 0){
					try {
						System.out.println("Now playing ...");
						m_instance += 1;
						VideoForm.this.showVideo();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if(KeyEvent.VK_ESCAPE == e.getKeyCode()){
					System.out.println("Video Closed");
					try {
						Runtime.getRuntime().exec( "killall -9 mplayer" );
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			
				
				if(KeyEvent.VK_F7 == e.getKeyCode()){
					System.out.println("return to main");
					try {
						Runtime.getRuntime().exec( "killall -9 mplayer" );
						m_instance = 0;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					m_mw.returnToMain();
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
	              System.out.println(this.toString()+"Focus LOST:"+e); 
	              checkFocus();
	          }
	      });
    }

    protected void showVideo() throws IOException{
    	System.out.println("Show Video");
        try{   	
        	if (m_instance > 1){
        		Process p = Runtime.getRuntime().exec( "killall -9 mplayer" );
        		p.waitFor();
        	}
        	Process mp = Runtime.getRuntime().exec( "mplayer -framedrop −really−quiet " + "./res/corn_15w.avi");
        	mp.waitFor();
        	m_instance = 0;
        }
        catch( Exception ex ){
            	Runtime.getRuntime().exec( "killall -9 mplayer" );
            	m_instance = 0;
                System.out.println( "Error: " + ex );
        }
        this.requestFocusInWindow();
		
	}

	public void updateLabel(){
    	m_mw.getM_ec().getM_lblInstruction().setText("<html><font color=black>Finger Print Enrollment</font> </html>");
    }

	public void addComponentToPane() {
		this.setLayout(new BorderLayout());
        createInstructionLabel();
        add(m_lblInstructVideo,BorderLayout.CENTER);
	}

	private void createInstructionLabel() {
		// TODO Auto-generated method stub
		m_lblInstructVideo = new JLabel("<html><font color = black >Press ENTER to start<br> ESC to cancel </font></html>");
		m_lblInstructVideo.setPreferredSize(new Dimension(m_width, m_height));
		m_lblInstructVideo.setMaximumSize(new Dimension(m_width, m_height));
		m_lblInstructVideo.setSize(new Dimension(m_width, m_height));
		m_lblInstructVideo.setFont(new Font("Times", Font.PLAIN, 18));
		m_lblInstructVideo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblInstructVideo.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblInstructVideo.setOpaque(false);
		
	}
	
	private void checkFocus() {
		if (ClockEventDispatcher.getM_status() == CLOCK_STATUS.CLOCKSTATUS_VIDEO) {
			showPopUpDialog();
		}
	}
	
	private void showPopUpDialog() {
		// TODO Auto-generated method stub
		JOptionPane optionPane = new JOptionPane("Focus Lost",
						JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
		m_jd = optionPane.createDialog(this, null);
		//m_jd.setVisible(true);
		//m_jd.setVisible(false);
		m_jd.dispose();
		this.grabFocus();
	}
	
	
}
