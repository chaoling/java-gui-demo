package com.adp.clocks.synergy2416.demo;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PlayVideoForm extends JPanel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5743167773429525088L;
	private JLabel m_lblInstruction;
	private MainWindow m_mw;
	private Timer m_timer=new Timer();


	/**
	 * Initialize the contents of the frame.
	 */
	public PlayVideoForm(MainWindow mw) {
		this.m_mw = mw;
		initialize();
		this.setOpaque(true); //content panes must be opaque
		
	}
	
	public void updateLabel(){
		m_mw.getM_ec().getM_lblInstruction().setText("<html><font color='yellow'>Video Demo</font> </html>");
	}
	
	private void createInstructionLabel(){
		m_lblInstruction = new JLabel();
		m_lblInstruction.setFont(new Font("Times", Font.PLAIN, 15));
		m_lblInstruction.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblInstruction.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblInstruction.setOpaque(false);
	}
	
	
	private void initialize() {
		// TODO Auto-generated method stub
		
		this.setLayout(new BorderLayout());
        createInstructionLabel();
        add(m_lblInstruction,BorderLayout.CENTER);
		m_lblInstruction.setText("<html><font color = Yellow>Press ENTER to start<br> ESC to cancel </font></html>");
		
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println("Pressed"+" " +e.getKeyCode());
				if(KeyEvent.VK_ENTER == e.getKeyCode()){
					try {
						System.out.println("Now playing ...");
						PlayVideoForm.this.showVideo();
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
					
					//m_mw.returnToMain();
				}
			}

			

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		 this.addFocusListener(new FocusListener(){
	          public void focusGained(FocusEvent e){
	              System.out.println(PlayVideoForm.this.toString()+"Focus GAINED:"+e.getSource().toString());
	              if (e.getSource().toString().contains("JButton")) {
	            	  PlayVideoForm.this.switchFocus(e);
	              }
	              
	          }
	          public void focusLost(FocusEvent e){
	              System.out.println(PlayVideoForm.this.toString()+"Focus LOST:"+e);

	              // FIX FOR GNOME/XWIN FOCUS BUG
	              System.out.println("Current KFM is "+ KeyboardFocusManager.getCurrentKeyboardFocusManager().toString());
	              //PlayVideoForm.this.requestFocusInWindow();
	          }
	      });
	}
	
    class RemindTask extends TimerTask {
        public void run() {
            System.out.format("Time's up!%n");
            try {
				Runtime.getRuntime().exec( "killall -9 mplayer" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            m_timer.cancel(); //Terminate the timer thread
        }
    }

	public void showVideo() throws IOException{
		System.out.println("Show Video");
	        try{   	
	        	Process p = Runtime.getRuntime().exec( "killall -9 mplayer" );
	        	p.waitFor();
	        	//Process q = Runtime.getRuntime().exec( "ps -o pid,comm | grep java | grep -v grep | awk '{print $1}' | xargs -t renice 2  -p" );
	        	//q.waitFor();
	        	m_timer.schedule(new RemindTask(),1000*45);
	        	Process mp = Runtime.getRuntime().exec( "mplayer -framedrop −really−quiet " + "./res/corn_15w.avi");
	        	mp.waitFor();
	        }
	        
	        catch( Exception ex ){
	            	Runtime.getRuntime().exec( "killall -9 mplayer" );
	                System.out.println( "Error: " + ex );
	        }
            //System.out.println("request focus in window after simulated mouse click.");
            this.requestFocusInWindow();
	        //m_mw.returnToMain();
	}

	public void switchFocus(FocusEvent e) {
		// TODO Auto-generated method stub
        JOptionPane optionPane = new JOptionPane("Focus Lost",
      		  						JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog jd = optionPane.createDialog(this, null);
        jd.setVisible(true);
        MainWindow.simulateMouseClick(e.getComponent());
        //simulate enter key pressed event;
        Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (r != null){
			System.out.println("simulate press enter key!");
			try{Thread.sleep(50);}catch(InterruptedException e1){}
			r.keyPress(KeyEvent.VK_ENTER);
			try{Thread.sleep(50);}catch(InterruptedException e1){}
			r.keyRelease(KeyEvent.VK_ENTER);
		}
        jd.dispose();
	}	
}
