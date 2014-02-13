package com.adp.clocks.synergy2416.demo;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayVideoForm extends JPanel {
	

	private JLabel m_lblInstruction;


	/**
	 * Initialize the contents of the frame.
	 */
	public PlayVideoForm() {
		initialize();
		this.setOpaque(true); //content panes must be opaque
	}
	
	
	private void initialize() {
		// TODO Auto-generated method stub
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));

		m_lblInstruction = new JLabel("<html>ENTER for Video<br>Menu for Main Menu</html>");
		int width=320;
		int height=240;
		m_lblInstruction.setPreferredSize(new Dimension(width, height));
		m_lblInstruction.setMaximumSize(new Dimension(width, height));
		m_lblInstruction.setSize(new Dimension(width, height));
		m_lblInstruction.setAlignmentX(Component.CENTER_ALIGNMENT);
		m_lblInstruction.setAlignmentY(Component.CENTER_ALIGNMENT);
		m_lblInstruction.setOpaque(false);
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Pressed"+" " +e.getKeyCode());
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
					PlayVideoForm.this.setVisible(false);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		add(m_lblInstruction);
		add(Box.createGlue());
		this.setOpaque(false);
	}


	public void showVideo() throws IOException{
		System.out.println("Show Video");
	        //mControlShell.setMinimized(true);
	        try{   	
	        	Process p = Runtime.getRuntime().exec( "killall -9 mplayer" );
	        	p.waitFor();
	        	//Process q = Runtime.getRuntime().exec( "ps -o pid,comm | grep java | grep -v grep | awk '{print $1}' | xargs -t renice 2  -p" );
	        	//q.waitFor();
	//Process mplayerProcess = Runtime.getRuntime().exec("/path/to/mplayer -slave -quiet -idle file/to/play.avi");
	            Process mp = Runtime.getRuntime().exec( "mplayer -framedrop −really−quiet " + "./res/corn_15w.avi");
	            mp.waitFor();
	           //
	            System.out.println("Movie Ended!");  
	        }
	        
	        catch( Exception ex ){
	            	Runtime.getRuntime().exec( "killall -9 mplayer" );
	                System.out.println( "Error: " + ex );
	        }
	        m_lblInstruction.requestFocusInWindow();
	        //mControlShell.setMaximize
	}
	
}
