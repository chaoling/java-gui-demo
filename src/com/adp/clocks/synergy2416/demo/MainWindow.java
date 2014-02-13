package com.adp.clocks.synergy2416.demo;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.SplashScreen;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame {

	/**
	 *  This is the main entrance of the Application follow MVC pattern
	 */
	private static final long serialVersionUID = 8210857356700426265L;
	private static EventController m_ec;
	private static ClockEventDispatcher m_ced;
	//private static FPU m_fpu = FPU.getInstance();

	static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"camera", "fingerprintreader", "keypad"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120,140,200,40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading "+comps[(frame/5)%3]+"...", 120, 150);
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					try {
						startMainThread();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// Ignore: If this exception occurs, we return too early, which
            // makes the splash window go away too early.
            // Nothing to worry about. Maybe we should write a log message.
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			// Error: Startup has failed badly. 
            // We can not continue running our application.
            InternalError error = new InternalError();
            error.initCause(e);
            throw error;
		}
	}

	public EventController getM_ec() {
		return m_ec;
	}

	public ClockEventDispatcher getM_cel() {
		return m_ced;
	}

	/**
	 * Create the frame.
	 */
	public MainWindow(String name) {
		super(name);
		final SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash == null){
			System.out.println("getSplashScreen() returned null");
			return;
		}
		Graphics2D g = splash.createGraphics();
		if (g == null){
			System.out.println("g is null");
			return;
		}
		for(int i=0; i<3; i++) {
			renderSplashFrame(g,i);
			splash.update();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		splash.close();
	}
	
	private static void startMainThread() {
		//Create and set up the main window.
		MainWindow m_frame = new MainWindow("ADPDemoWindow");
		
		m_ced = new ClockEventDispatcher();
		m_ec = new EventController(m_frame);
		m_ec.initialize();
		m_ced.initialize();
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(m_ced);
	}

	
}
