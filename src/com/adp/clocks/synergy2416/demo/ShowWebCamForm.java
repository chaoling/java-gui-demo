package com.adp.clocks.synergy2416.demo;

/*
* Copyright (C) 2011 Gilles Gigan (gilles.gigan@gmail.com)
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public  License as published by the
* Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
* or FITNESS FOR A PARTICULAR PURPOSE.  
* See the GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
*/
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import au.edu.jcu.v4l4j.CaptureCallback;
import au.edu.jcu.v4l4j.FrameGrabber;
import au.edu.jcu.v4l4j.V4L4JConstants;
import au.edu.jcu.v4l4j.VideoDevice;
import au.edu.jcu.v4l4j.VideoFrame;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;

/**
 * This class creates a user interface to capture and display the current image
 * from a video device.
 * @author gilles
 *
 */
public class ShowWebCamForm  extends JPanel implements CaptureCallback{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9200460419460064251L;
	private static int		width, height, std, channel;
	private static String	device;

	private VideoDevice		videoDevice;
	private FrameGrabber	frameGrabber;
	private VideoFrame		lastVideoFrame;
	private JLabel lblInstruction;
	private MainWindow m_mw;

	/**
	 * Start a new GetSnapshot UI
	 * @throws V4L4JException if there is a problem capturing from the given device
	 */
	public ShowWebCamForm(MainWindow mw){
		this.m_mw = mw;
		device = (System.getProperty("test.device") != null) ? System.getProperty("test.device") : "/dev/video0"; 
		width = (System.getProperty("test.width")!=null) ? Integer.parseInt(System.getProperty("test.width")) : 320;
		height = (System.getProperty("test.height")!=null) ? Integer.parseInt(System.getProperty("test.height")) : 240;
		std = (System.getProperty("test.standard")!=null) ? Integer.parseInt(System.getProperty("test.standard")) : V4L4JConstants.STANDARD_WEBCAM;
		channel = (System.getProperty("test.channel")!=null) ? Integer.parseInt(System.getProperty("test.channel")) : 0;
		lastVideoFrame = null;
		
		 this.setOpaque(true); //content panes must be opaque
//	     m_mw.getContentPane().removeAll();
//	     m_mw.getContentPane().add(this);
//		 ((JPanel)m_mw.getContentPane()).revalidate();
//		 m_mw.repaint();

		// initialise the video device and frame grabber

	}
	public void initialize(){
		try {
			initFrameGrabber();
		} catch (V4L4JException e) {
			System.err.println("Error setting up capture");
			e.printStackTrace();
			
			// cleanup and exit
			cleanupCapture();
			return;
		}
		// Create and initialize UI
		initGUI();
		
		// start capture
		try {
			frameGrabber.startCapture();
		} catch (V4L4JException e) {
			System.err.println("Error starting the capture");
			e.printStackTrace();
		}
	}


	/**
	 * This method creates the VideoDevice and frame grabber.
	 * It enables push mode and starts the capture
	 * @throws V4L4JException
	 */
	private void initFrameGrabber() throws V4L4JException{
		videoDevice = new VideoDevice(device);
		frameGrabber = videoDevice.getJPEGFrameGrabber(width, height, channel, std, 80);
		width = frameGrabber.getWidth();
		height = frameGrabber.getHeight();
		frameGrabber.setCaptureCallback(this);
	}

	/**
	 * This method builds the UI
	 */
	private void initGUI() {
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));

		lblInstruction = new JLabel("<html>ENTER for Picture<br>ESC for Main Menu</html>");
		lblInstruction.setPreferredSize(new Dimension(width, height));
		lblInstruction.setMaximumSize(new Dimension(width, height));
		lblInstruction.setSize(new Dimension(width, height));
		lblInstruction.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblInstruction.setAlignmentY(Component.CENTER_ALIGNMENT);

		lblInstruction.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Pressed"+KeyEvent.VK_ESCAPE +" " +e.getKeyCode());
				getSnapshot();
				
				if(KeyEvent.VK_ESCAPE == e.getKeyCode()){
					System.out.println("Cam Closed");
					//this.setVisible(false);
					//MainWindow.btnNewButton.requestFocus();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		add(lblInstruction);
		add(Box.createGlue());
		//frame.getContentPane().add(button);
		lblInstruction.requestFocus();
		//MainDemo.btnNewButton.requestFocus();
	}

	/**
	 * This method is called when the Get snapshot button is hit and 
	 * it displays the current video frame.
	 */
	private void getSnapshot() {
		VideoFrame lastFrameCopy = null;
		
		synchronized (this) {
			// If there is a current frame ...
			if (lastVideoFrame != null) {
				// Make a local copy
				lastFrameCopy = lastVideoFrame;
				
				// and set the class member to null so it does not get recycled
				// in nextFrame(), since we are going to recycle it
				// ourselves once drawn in the label.
				lastVideoFrame = null;
			}
		}
		
		// Draw the frame and recycle it
		if (lastFrameCopy != null) {
			lblInstruction.getGraphics().drawImage(lastFrameCopy.getBufferedImage(), 0, 0, width, height, null);
			lastFrameCopy.recycle();
		}
	}
	
	/**
	 * This method stop the capture and releases the frame grabber and video device
	 */
	private void cleanupCapture() {
		// stop capture
		try {
			frameGrabber.stopCapture();
		} catch (Exception ex) {
			// frame grabber may be already stopped, so ignore this
		}
		
		// release frame grabber and video device
		try {
			videoDevice.releaseFrameGrabber();
			videoDevice.release();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	@Override
	public void exceptionReceived(V4L4JException e) {
		lblInstruction.setText(e.toString());
	}

	@Override
	public synchronized void nextFrame(VideoFrame frame) {
		// Recycle the previous frame if there is one
		if (lastVideoFrame != null)
			lastVideoFrame.recycle();

		// Store a pointer to this new frame
		lastVideoFrame = frame;
	}
}