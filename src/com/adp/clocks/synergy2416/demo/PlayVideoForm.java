package com.adp.clocks.synergy2416.demo;

import javax.swing.JPanel;

public class PlayVideoForm extends JPanel {
	
	private MainWindow m_mw;
	/**
	 * Initialize the contents of the frame.
	 */
	public PlayVideoForm(MainWindow mw) {
		this.m_mw = mw;
		addComponentsToPane();
		this.setOpaque(true); //content panes must be opaque
//	     m_mw.getContentPane().removeAll();
//	     m_mw.getContentPane().add(this);
//		 ((JPanel)m_mw.getContentPane()).revalidate();
//		 m_mw.repaint();
		//playMedia();
	}
	
	private void addComponentsToPane(){
		//TODO
		
	}
	
//	public void playMedia(URL mediaURL) throws IOException {
//		Manager.setHint( Manager.LIGHTWEIGHT_RENDERER, true );
//
//        try{
//            //create a player to play the media specified in the URL
//            Player mediaPlayer = Manager.createRealizedPlayer( mediaURL );
//
//            //get the components for the video and the playback controls
//            Component video = mediaPlayer.getVisualComponent();
//            Component controls = mediaPlayer.getControlPanelComponent();
//
//            if ( video != null )
//                add( video, BorderLayout.CENTER ); //add video component
//            if ( controls != null )
//                add( controls, BorderLayout.SOUTH ); //add controls
//
//                mediaPlayer.start(); //start playing the media clip
//        } //end try
//        catch ( NoPlayerException noPlayerException ){
//            JOptionPane.showMessageDialog(null, "No media player found");
//        } //end catch
//        catch ( CannotRealizeException cannotRealizeException ){
//            JOptionPane.showMessageDialog(null, "Could not realize media player.");
//        } //end catch
//        catch ( IOException iOException ){
//            JOptionPane.showMessageDialog(null, "Error reading from the source.");
//        } //end catch
//    }
	
}
