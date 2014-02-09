package com.adp.clocks.synergy2416.demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

public class UpdateClock {
	 private static SimpleDateFormat mFormat =  new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

	/**
	 * 
	 */
	public static void initialize(final JLabel timePanel){
		//FontMetrics metrics = timePanel.getFontMetrics(timePanel.getFont());
		//System.out.println(metrics.stringWidth(mFormat.format(new Date())));
		timePanel.setText("<html><center><font color=yellow>"+mFormat.format(new Date())+"</font></center></html>");
		
	     ActionListener taskPerformer = new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                timePanel.setText("<html><center><font color=yellow>"+mFormat.format(new Date())+"</font></center></html>");
	            }
	        };
	        Timer t = new Timer(1000, taskPerformer);
	        t.start();
	}

}