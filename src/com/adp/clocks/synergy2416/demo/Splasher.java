package com.adp.clocks.synergy2416.demo;

public class Splasher {
    /**
     * Shows the splash screen, launches the application and then disposes
     * the splash screen.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	 //new InitializingPane(m_frame);
	    SplashWindow.splash(MainWindow.class.getResource("/com/adp/clocks/synergy2416/res/Background.png"));
        MainWindow.main(args);
        SplashWindow.disposeSplash();
    }
}