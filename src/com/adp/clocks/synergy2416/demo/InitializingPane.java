package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InitializingPane extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int LogoSize=50;
	private  JLabel lblBackground;
	private JLabel lblInstruction;
	private MainWindow mw;
	
	public MainWindow getMw() {
		return mw;
	}

	public void setMw(MainWindow mw) {
		this.mw = mw;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public InitializingPane(MainWindow mw) {
		setMw(mw);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		System.out.println("Initiazing, please wait ...");
		lblBackground = createBackgroundLabel();
		add(lblBackground);
		lblBackground.setLayout(new BorderLayout());
		lblBackground.add(createTopLabel(),BorderLayout.CENTER);
		System.out.println("Done ...");
	}
	
	private JLabel createTopLabel() {
		
		lblInstruction = new JLabel("<html><font color='yellow'>Initiazing clock, please wait ... </font> .</font> </html>");
		lblInstruction.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource("/com/adp/clocks/synergy2416/res/synel.png")).getImage().getScaledInstance(LogoSize, LogoSize, java.awt.Image.SCALE_SMOOTH)));
		lblInstruction.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		return lblInstruction;
	}
	
	private JLabel createBackgroundLabel(){
		ImageIcon image = new ImageIcon(MainWindow.class.getResource("/com/adp/clocks/synergy2416/res/Background.png"));
		lblBackground = new JLabel(image);
		lblBackground.setOpaque(true);
		return lblBackground;
	}

}
