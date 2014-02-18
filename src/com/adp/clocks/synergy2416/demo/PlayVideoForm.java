package com.adp.clocks.synergy2416.demo;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
//import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.adp.clocks.synergy2416.demo.ClockEventDispatcher.CLOCK_STATUS;

public class PlayVideoForm extends JPanel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5743167773429525088L;
	//private JLabel m_lblInstructVideo;
	private MainWindow m_mw;
	private int m_instance = 0; //counter to keep track of number of video play event at any moment
	private JDialog m_jd;
	private int m_width = 320;
	private int m_height = 220;


	/**
	 * Initialize the contents of the frame.
	 */
	public PlayVideoForm(MainWindow mw) {
		this.m_mw = mw;
		setSize(m_width, m_height);
		initialize();
		this.setOpaque(true); //content panes must be opaque
		
	}
	
	public void updateLabel(){
		m_mw.getM_ec().getM_lblInstruction().setText("<html><font color=black >Video Demo</font> </html>");
	}
	
//	private void createInstructionLabel(){
//		m_lblInstructVideo = new JLabel("<html><font color = black >Press ENTER to start<br> ESC to cancel </font></html>");
//		m_lblInstructVideo.setPreferredSize(new Dimension(m_width, m_height));
//		m_lblInstructVideo.setMaximumSize(new Dimension(m_width, m_height));
//		m_lblInstructVideo.setSize(new Dimension(m_width, m_height));
//		m_lblInstructVideo.setFont(new Font("Times", Font.PLAIN, 18));
//		m_lblInstructVideo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//		m_lblInstructVideo.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
//		m_lblInstructVideo.setOpaque(false);
//	}
	
	
	private void initialize() {
		// TODO Auto-generated method stub
		
		this.setLayout(new BorderLayout());
        //createInstructionLabel();
        //add(m_lblInstructVideo,BorderLayout.CENTER);
		
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Pressed"+" " +e.getKeyCode());
				//System.out.println("minstance is "+m_instance);
				if(KeyEvent.VK_ENTER == e.getKeyCode() && m_instance == 0){
					try {
						System.out.println("Now playing ...");
						m_instance += 1;
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
	              System.out.println("Focus GAINED:"+e.getSource().toString());
	              
	          }
	          public void focusLost(FocusEvent e){
	              System.out.println("Focus LOST:"+e.getSource().toString());

	              // FIX FOR GNOME/XWIN FOCUS BUG
	              System.out.println("Current KFM is "+ KeyboardFocusManager.getCurrentKeyboardFocusManager().toString());
	              //PlayVideoForm.this.requestFocusInWindow();
	              //showPopUpDialog();
	              checkFocus();
	          }
	      });
	}
	
//    class RemindTask extends TimerTask {
//        public void run() {
//            System.out.format("Time's up!%n");
//            try {
//				Runtime.getRuntime().exec( "killall -9 mplayer" );
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            m_instance = 0;
//        }
//    }

	public void showVideo() throws IOException{
		System.out.println("Show Video");
	        try{   	
	        	if (m_instance > 1){
	        		Process p = Runtime.getRuntime().exec( "killall -9 mplayer" );
	        		p.waitFor();
	        	}
	        	//Process q = Runtime.getRuntime().exec( "ps -o pid,comm | grep java | grep -v grep | awk '{print $1}' | xargs -t renice 2  -p" );
	        	//q.waitFor();
//	        	m_timer = new Timer();
//	        	m_timer.schedule(new RemindTask(),1000*45);
	        	Process mp = Runtime.getRuntime().exec( "mplayer -framedrop −really−quiet " + "./res/corn_15w.avi");
	        	mp.waitFor();
	        	m_instance = 0;
	        }
	        
	        catch( Exception ex ){
	            	Runtime.getRuntime().exec( "killall -9 mplayer" );
	            	m_instance = 0;
	                System.out.println( "Error: " + ex );
	        }
            //System.out.println("request focus in window after simulated mouse click.");
            this.requestFocusInWindow();
	        //m_mw.returnToMain();
	}
	
	private void checkFocus() {
		// TODO Auto-generated method stub
//		String str = MainWindow.getCurrentVisableComponentName((Container)m_mw);
//		System.out.println(str+" is visiable");
		if (ClockEventDispatcher.getM_status() == CLOCK_STATUS.CLOCKSTATUS_VIDEO) {
			//try get back the focus.
			try {
				
				showPopUpDialog();
				alt_tab();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void alt_tab() throws AWTException{
		   
		
		    final Robot robot = new Robot();
		    robot.setAutoDelay(40);
		    robot.setAutoWaitForIdle(true);
		    new Thread(new Runnable() {
		        @Override 
		        public void run() {
		        	System.out.println("run mouse click thread."+m_jd.getComponent(0).getX()+m_jd.getComponent(0).getY());
//		        	robot.mouseMove(m_jd.getComponent(0).getX(),m_jd.getComponent(0).getY());
//		        	robot.delay(50);
//		        	
//		        	//leftClick(robot);
//		        	System.out.println("I am in mouse click.");
//		    	    robot.mousePress(InputEvent.BUTTON1_MASK);
//		    	    robot.delay(200);
//		    	    robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		    	    robot.delay(200);
//		    	    
//		    	    System.out.println("run keytype");
//		    	    robot.delay(50);
//		        	robot.keyPress(KeyEvent.VK_ALT);
//				    //robot.keyPress(KeyEvent.VK_CONTROL);
//				    robot.keyPress(KeyEvent.VK_TAB);
//				    robot.delay(100);
//				    robot.keyRelease(KeyEvent.VK_TAB);
//				    //robot.keyRelease(KeyEvent.VK_CONTROL);
//				    robot.keyRelease(KeyEvent.VK_ALT);
				    System.out.println("thread done.");
		        } 
		    }).start();
		}
	
//	private void leftClick(Robot r)
//	  {
//		System.out.println("I am in mouse click.");
//	    r.mousePress(InputEvent.BUTTON1_MASK);
//	    r.delay(200);
//	    r.mouseRelease(InputEvent.BUTTON1_MASK);
//	    r.delay(200);
//	  }
	  
//	  private void type(Robot r, int i)
//	  {
//	    r.delay(40);
//	    r.keyPress(i);
//	    r.keyRelease(i);
//	  }

//	  private void type(Robot r,String s)
//	  {
//		System.out.println("I am in type");
//	    byte[] bytes = s.getBytes();
//	    for (byte b : bytes)
//	    {
//	      int code = b;
//	      // keycode only handles [A-Z] (which is ASCII decimal [65-90])
//	      if (code > 96 && code < 123) code = code - 32;
//	      r.delay(40);
//	      r.keyPress(code);
//	      r.keyRelease(code);
//	    }
//	  }

	
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