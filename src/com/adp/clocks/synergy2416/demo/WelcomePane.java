package com.adp.clocks.synergy2416.demo;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import java.awt.Component;


public class WelcomePane extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int IconSize=40;
	private static final int LogoSize=35;
	private  JLabel lblBackground;
	protected  JLabel lblTimeLabel;
	private  JLabel lblInstructionLabel;
	private MainWindow m_frame;
	
	/**
	 * Initialize the contents of the frame.
	 */
	public WelcomePane(MainWindow mw) {
		addComponentsToPane();
	}
	
	private void addComponentsToPane() {
		lblBackground = createBackgroundLabel();
		add(lblBackground);
		lblBackground.setLayout(new BorderLayout());
		lblBackground.add(createTopLabel(),BorderLayout.NORTH);
		lblBackground.add(createTimeLabel(),BorderLayout.CENTER);
		lblBackground.add(createMenuBarPanel(), BorderLayout.SOUTH);
		UpdateClock.initialize(lblTimeLabel);
		System.out.println("Done ...");
	}
	
	private JLabel createTopLabel() {
		
		lblInstructionLabel = new JLabel("<html><font color='yellow'>Press <font color='red'>1-6</font> key to start demo funtions.</font> </html>");
		lblInstructionLabel.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource("/com/adp/clocks/synergy2416/res/synel.png")).getImage().getScaledInstance(LogoSize, LogoSize, java.awt.Image.SCALE_SMOOTH)));
		lblInstructionLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		return lblInstructionLabel;
	}
	
	private JLabel createTimeLabel(){
		System.out.println("Initialising clock Label ...");
		lblTimeLabel = new JLabel();
		lblTimeLabel.setFont(new Font("Times", Font.PLAIN, 25));
		lblTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		return lblTimeLabel;
	}
	
	private JLabel createBackgroundLabel(){
		ImageIcon image = new ImageIcon(MainWindow.class.getResource("/com/adp/clocks/synergy2416/res/Background.png"));
		lblBackground = new JLabel(image);
		lblBackground.setOpaque(true);
		return lblBackground;
	}
	
	  //Create the content pane for the part of the frame.
    private JPanel createMenuBarPanel() {
        JPanel menuBar = new JPanel();
        menuBar = new JPanel();
		menuBar.setBorder(new EmptyBorder(5, 0, 5, 0));
		menuBar.setLayout(new GridLayout(1, 6));
	
		ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/video-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblVideoimage = new JLabel();
		GridBagConstraints gbc_lblVideoimage = new GridBagConstraints();
		lblVideoimage.setIcon(icon);
		menuBar.add(lblVideoimage, gbc_lblVideoimage);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/camera-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblCameraimage = new JLabel();
		GridBagConstraints gbc_lblCameraimage = new GridBagConstraints();
		lblCameraimage.setIcon(icon);
		menuBar.add(lblCameraimage, gbc_lblCameraimage);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/light-icon-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblGpiolabel = new JLabel();
		GridBagConstraints gbc_lblGpiolabel = new GridBagConstraints();
		lblGpiolabel.setIcon(icon);
		menuBar.add(lblGpiolabel, gbc_lblGpiolabel);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/finger-icon-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblFpuimage = new JLabel();
		GridBagConstraints gbc_lblFpuimage = new GridBagConstraints();
		lblFpuimage.setIcon(icon);
		menuBar.add(lblFpuimage, gbc_lblFpuimage);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/id-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblFpuimage_1 = new JLabel();
		GridBagConstraints gbc_lblFpuimage_1 = new GridBagConstraints();
		lblFpuimage_1.setIcon(icon);
		menuBar.add(lblFpuimage_1, gbc_lblFpuimage_1);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/info-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblSysimage = new JLabel();
		GridBagConstraints gbc_lblSysimage = new GridBagConstraints();
		lblSysimage.setIcon(icon);
		menuBar.add(lblSysimage, gbc_lblSysimage);
		
        return menuBar;
    }

	public MainWindow getM_window() {
		return m_frame;
	}

	public void setM_window(MainWindow m_frame) {
		this.m_frame = m_frame;
	}
}
