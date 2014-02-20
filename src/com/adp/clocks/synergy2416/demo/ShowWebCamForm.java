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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	private JLabel m_lblInstructwebcam;
	private MainWindow m_mw;
	private boolean m_bStreamVideo=false;
	
	/**
	 * Start a new GetSnapshot UI
	 * @throws V4L4JException if there is a problem capturing from the given device
	 */
	public ShowWebCamForm(MainWindow mw){
		device = (System.getProperty("test.device") != null) ? System.getProperty("test.device") : "/dev/video0"; 
		width = (System.getProperty("test.width")!=null) ? Integer.parseInt(System.getProperty("test.width")) : 320;
		height = (System.getProperty("test.height")!=null) ? Integer.parseInt(System.getProperty("test.height")) : 240;
		std = (System.getProperty("test.standard")!=null) ? Integer.parseInt(System.getProperty("test.standard")) : V4L4JConstants.STANDARD_WEBCAM;
		channel = (System.getProperty("test.channel")!=null) ? Integer.parseInt(System.getProperty("test.channel")) : 0;
		lastVideoFrame = null;
		
		this.m_mw = mw;
		this.setOpaque(false); //content panes must be opaque

		// Initialize the video device and frame grabber
		 initialize();
	}
	
	public void updateLabel(){
		m_mw.getM_ec().getLblVideoImage().setVisible(false);
		m_mw.getM_ec().getLblCameraImage().setVisible(true);
		m_mw.getM_ec().getLblFpuEnrollImage().setVisible(false);
		m_mw.getM_ec().getLblFpuControlImage().setVisible(false);
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
		
		this.setLayout(new BorderLayout());

		m_lblInstructwebcam = new JLabel("<html><font color=black font=18>IN for Picture<br>OUT for Webcam Stream <br>MENU for Main Menu</html>");
		m_lblInstructwebcam.setPreferredSize(new Dimension(width, height));
		m_lblInstructwebcam.setMaximumSize(new Dimension(width, height));
		m_lblInstructwebcam.setSize(new Dimension(width, height));
	    m_lblInstructwebcam.setFont(new Font("Times", Font.PLAIN, 18));
		m_lblInstructwebcam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblInstructwebcam.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblInstructwebcam.setOpaque(false);

		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				m_bStreamVideo = false;
				System.out.println("Webcam form Pressed"+" " +e.getKeyCode());
				if( KeyEvent.VK_F5 == e.getKeyCode()){
					getSnapshot();
					ShowWebCamForm.this.setVisible(true);
				}
				
				if( KeyEvent.VK_F6 == e.getKeyCode()){
					m_bStreamVideo = true;
					ShowWebCamForm.this.setVisible(true);
					}
				
				if( KeyEvent.VK_F7 == e.getKeyCode()){
					System.out.println("Cam Closed");
					ShowWebCamForm.this.setVisible(false);
					//ShowWebCamForm.this.cleanupCapture();
					m_mw.returnToMain();
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

		add(m_lblInstructwebcam,BorderLayout.CENTER);
		
	}

	/**
	 * This method is called when the Get snapshot button is hit and 
	 * it displays the current video frame.
	 */
	private void getSnapshot() {
		VideoFrame lastFrameCopy = null;
		//System.out.println("get one snapshot...");
		
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
			System.out.println("drawing the webcam image...width: "+width +" height :"+height);
			m_lblInstructwebcam.getGraphics().drawImage(lastFrameCopy.getBufferedImage(), 0, 0, width, height, null);
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
		m_lblInstructwebcam.setText(e.toString());
	}

	@Override
	public synchronized void nextFrame(VideoFrame frame) {
		
        if (m_bStreamVideo){
        	//draw the new frame onto the JLabel
        	m_lblInstructwebcam.getGraphics().drawImage(frame.getBufferedImage(), 0, 0, width, height, null);
        	frame.recycle();
        }else {
        	// Recycle the previous frame if there is one
    		if (lastVideoFrame != null)
    			lastVideoFrame.recycle();
        	// Store a pointer to this new frame
    		lastVideoFrame = frame;
        }
		
	}
}