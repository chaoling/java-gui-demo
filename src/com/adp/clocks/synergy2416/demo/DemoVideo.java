package com.adp.clocks.synergy2416.demo;

import java.io.File;
import java.io.IOException;

public class DemoVideo {
	
	private static JMPlayer jmplayer;
	
	public static void showVideo(){
	 JMPlayer jmPlayer = new JMPlayer();
     // open a video file
     try {
		jmPlayer.open(new File("./res/corn_15w.avi"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     // set volume to 90%
     jmPlayer.setVolume(90); 
	}
	
	public static void closeVideo() {
		if (jmplayer != null){
			if (jmplayer.isPlaying()) {
				jmplayer.close();
			}
		}
	}
}
//	public static void showVideo(){
//		System.out.println("Show Video");
//	        //mControlShell.setMinimized(true);
//	        try{   	
//	        	Process p =Runtime.getRuntime().exec( "killall -9 mplayer" );
//	        	p.waitFor();
//	        	//Process q = Runtime.getRuntime().exec( "ps -o pid,comm | grep java | grep -v grep | awk '{print $1}' | xargs -t renice 2  -p" );
//	        	//q.waitFor();
//	Process mplayerProcess = Runtime.getRuntime().exec("/path/to/mplayer -slave -quiet -idle file/to/play.avi");
//	            Runtime.getRuntime().exec( "mplayer -framedrop −really−quiet " + "./res/corn_15w.avi");
//	           //
//	            System.out.println("Movie");
//	            
//	             
//	        }
//	        
//	            catch( Exception ex ){
//	                System.out.println( "Error: " + ex );
//	            }
//	        //mControlShell.setMaximize
//	}
	
//	Canvas comp = new Canvas();
//
//	   Class cl = Class.forName("sun.awt.X11ComponentPeer");
//	        java.lang.reflect.Method m = cl.getMethod("getContentWindow", null);
//	        Object obj = m.invoke(comp.getPeer());
//	        long windowId = Long.parseLong(obj.toString());
//
//
//	String[] filePath = new String[]{"c:\\a.avi"};
//
//	m_handler.startPlayer(String.valueOf(windowId), filePath);
//
//
//
//
//	  public void startPlayer(String windowId ,String[] movie) {
//	    
//	    
//	    String[] runCmd = new String[movie.length + 5];
//	    runCmd[0] = "mplayer";
//	    runCmd[1] = "-wid";
//	    runCmd[2] = String.valueOf(windowId);
//	    runCmd[3] = "-slave";
//	    runCmd[4] = "-fs";
//	    
//	    
//	    for (int i = 0; i < movie.length; i++) {
//	      runCmd[i+5] = movie;
//
//	}
//
//
//
//
//
//	try{
//
//	m_playerPrc = Runtime.getRuntime().exec(runCmd);
//
//
//
//	final StreamGobbler inGlbbler = new StreamGobbler(m_playerPrc.getInputStream() ,"IN");
//
//	StreamGobbler errGlbbler = new StreamGobbler(m_playerPrc.getErrorStream() ,"ERR");
//
//
//
//	inGlbbler.start();
//
//	errGlbbler.start();
//
//
//
//	m_out = new PrintStream(m_playerPrc.getOutputStream());
//
//
//
//	//FIRE EVENT STARTED 
//
//	fireMplayerStarted();
//
//
//
//	new Thread(){
//
//	public void run(){
//
//	try {
//
//	inGlbbler.join();
//
//	} catch (InterruptedException e) {
//
//	e.printStackTrace();
//
//	}
//
//
//
//	stopPlayer();
//
//
//
//	//FIRE EVENT STOPED
//
//	fireMplayerStoped();
//
//	}
//
//	}.start();
//
//	}catch (IOException e) {
//
//	e.printStackTrace();
//
//
//
//	//FIRE EVENT ERROR 
//
//	fireMplayerError();
//
//	}
//
//	}
//
//}
