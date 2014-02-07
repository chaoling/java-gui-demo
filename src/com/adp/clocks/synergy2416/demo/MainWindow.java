package com.adp.clocks.synergy2416.demo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Component;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8210857356700426265L;
	private static final int IconSize=40;
	private static final int LogoSize=35;
	protected static JLabel lblBackground;
	protected static JLabel lblTimeLabel;
	protected static JLabel lblInstructionLabel;
	protected static DemoCamera camera;
	private static JPanel contentPaneTop;
	private static DemoEvent mainEvent = new DemoEvent();;
	//private DemoSound sound = new DemoSound();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow(String name) {
		super(name);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private static void createAndShowGUI() {
		System.out.println("Initialising main window frame ...");
		//Create and set up the main window.
		MainWindow frame = new MainWindow("ADPDemoMainWindow");
		frame.setBounds(0, 0, 320, 240);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set up the content pane.
		frame.addComponentsToPane();
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	private void addComponentsToPane() {
		lblInstructionLabel = new JLabel("<html><font color='yellow'>Press <font color='red'>1-6</font> key to start demo funtions.</font> </html>");
		lblInstructionLabel.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource("/com/adp/clocks/synergy2416/res/synel.png")).getImage().getScaledInstance(LogoSize, LogoSize, java.awt.Image.SCALE_SMOOTH)));
		lblInstructionLabel.setAlignmentY(Component.TOP_ALIGNMENT);

		 
		System.out.println("Initialising clock Label ...");
		lblTimeLabel = new JLabel();
		lblTimeLabel.setFont(new Font("Times", Font.PLAIN, 25));
		lblTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblTimeLabel.setFocusable(true);
		lblTimeLabel.addKeyListener(mainEvent);
		
		lblBackground = new JLabel(new ImageIcon(MainWindow.class.getResource("/com/adp/clocks/synergy2416/res/Background.png")));
		setContentPane(lblBackground);
		setLayout(new BorderLayout());
		contentPaneTop = new JPanel();
		contentPaneTop.setBorder(new EmptyBorder(5, 0, 5, 0));
		contentPaneTop.setLayout(new GridLayout(1, 6));
	
		
		ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/video-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblVideoimage = new JLabel();
		GridBagConstraints gbc_lblVideoimage = new GridBagConstraints();
		lblVideoimage.setIcon(icon);
		contentPaneTop.add(lblVideoimage, gbc_lblVideoimage);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/camera-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblCameraimage = new JLabel();
		GridBagConstraints gbc_lblCameraimage = new GridBagConstraints();
		lblCameraimage.setIcon(icon);
		contentPaneTop.add(lblCameraimage, gbc_lblCameraimage);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/light-icon-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblGpiolabel = new JLabel();
		GridBagConstraints gbc_lblGpiolabel = new GridBagConstraints();
		lblGpiolabel.setIcon(icon);
		contentPaneTop.add(lblGpiolabel, gbc_lblGpiolabel);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/finger-icon-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblFpuimage = new JLabel();
		GridBagConstraints gbc_lblFpuimage = new GridBagConstraints();
		lblFpuimage.setIcon(icon);
		contentPaneTop.add(lblFpuimage, gbc_lblFpuimage);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/id-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblFpuimage_1 = new JLabel();
		GridBagConstraints gbc_lblFpuimage_1 = new GridBagConstraints();
		lblFpuimage_1.setIcon(icon);
		contentPaneTop.add(lblFpuimage_1, gbc_lblFpuimage_1);
		
		icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/adp/clocks/synergy2416/res/info-75.png")).getImage().getScaledInstance(IconSize, IconSize, java.awt.Image.SCALE_SMOOTH)); 
		JLabel lblSysimage = new JLabel();
		GridBagConstraints gbc_lblSysimage = new GridBagConstraints();
		lblSysimage.setIcon(icon);
		contentPaneTop.add(lblSysimage, gbc_lblSysimage);
		
		getContentPane().add(lblInstructionLabel,BorderLayout.NORTH);
		getContentPane().add(lblTimeLabel, BorderLayout.CENTER);
		getContentPane().add(contentPaneTop, BorderLayout.SOUTH);
		
		DemoClock.initialize(lblTimeLabel);
		
		System.out.println("Done ...");
		lblInstructionLabel.requestFocus();
		
	}

}
