package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class WelcomeForm extends JPanel {
	
	/**
	 * 
	 */
	private  JLabel m_lblWelcomeLabel;
	
	/**
	 * Initialize the contents of the frame.
	 * @param mw 
	 */
	public WelcomeForm() {
		this.setLayout(new BorderLayout());
		addComponentsToPane();
		this.setOpaque(false);
		
	}
	
	private void addComponentsToPane() {
		createWelcomeLabel();
		add(m_lblWelcomeLabel,BorderLayout.CENTER);
		m_lblWelcomeLabel.setText("<html><font color = Yellow><b>Welcome!</b></font></html>");
		//UpdateClock.initialize(m_lblTimeLabel);
	}

	private void createWelcomeLabel(){
		m_lblWelcomeLabel = new JLabel();
		m_lblWelcomeLabel.setFont(new Font("Times", Font.PLAIN, 25));
		m_lblWelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		m_lblWelcomeLabel.setOpaque(false);
	}

}
