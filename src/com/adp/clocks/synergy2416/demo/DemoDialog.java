package com.adp.clocks.synergy2416.demo;


//This program shows a series of input dialog boxes.
//The next dialog is launched on the closing of the current dialog. 
//It provides examples of how to create dialog boxes
//with a text field, combo box and list box.

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.Icon;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.lang.reflect.Field;

public class DemoDialog extends WindowAdapter{
private JFrame frame;
private JLabel label;
private JTextArea tracker;
private static int badgeID = 0;
private static String input;

//Using a standard Java icon
private Icon optionIcon = UIManager.getIcon("FileView.computerIcon");

//Application start point   

public DemoDialog(String dialog)
{
	frame = new JFrame();
	frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.LINE_AXIS));

	label = new JLabel(dialog);
	label.setPreferredSize(new Dimension(200, 100));
	label.setMaximumSize(new Dimension(100, 100));
	label.setSize(new Dimension(100, 100));
	label.setAlignmentX(Component.CENTER_ALIGNMENT);
	label.setAlignmentY(Component.CENTER_ALIGNMENT);

	
	frame.getContentPane().add(label);
	frame.getContentPane().add(Box.createGlue());
	//frame.getContentPane().add(button);


	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.addWindowListener(this);

	frame.pack();
	frame.setVisible(true);
	label.requestFocus();
  //  close();
         
}

public void setVisible (boolean value){
	frame.setVisible(value);
	SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {

			frame.setVisible(false);
			//MainWindow.btnNewButton.requestFocus();
		}
	});

	//frame.pack();
}
public void setVisible2 (boolean value){
	frame.setVisible(value);
	SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {

			frame.setVisible(false);
			//MainWindow.btnNewButton.requestFocus();
		}
	});
}
public void setgVisible (boolean value){
	frame.setVisible(value);
	SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {

			//frame.setVisible(false);
			//MainWindow.btnNewButton.requestFocus();
		}
	});

	//frame.pack();
}
public void setVisibleTime (boolean value){
	frame.setVisible(value);
	SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.setVisible(false);
			//MainWindow.btnNewButton.requestFocus();
		}
	});

	//frame.pack();
}


public static String getBadgeID(){
	  return input;
}

}