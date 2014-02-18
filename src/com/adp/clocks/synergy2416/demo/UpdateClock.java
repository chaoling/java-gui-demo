package com.adp.clocks.synergy2416.demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

public class UpdateClock {
	 private static SimpleDateFormat mFormatTime =  new SimpleDateFormat("hh:mm:ss");
	 private static SimpleDateFormat mFormatDate =  new SimpleDateFormat("EEE MMM dd, yyyy");

	/**
	 * 
	 */
	public static void initialize(final JLabel timePanel){
		//FontMetrics metrics = timePanel.getFontMetrics(timePanel.getFont());
		//System.out.println(metrics.stringWidth(mFormat.format(new Date())));
		Date d = new Date();
		timePanel.setText("<html><center><font color=Black><b>"+mFormatDate.format(d)+"<br/>"+mFormatTime.format(d)+"</b></font></center></html>");
		
	     ActionListener taskPerformer = new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	Date d = new Date();
	                timePanel.setText("<html><center><font color=Black><b>"+mFormatDate.format(d)+"<br/>"+mFormatTime.format(d)+"</b></font></center></html>");
	            }
	        };
	        Timer t = new Timer(1000, taskPerformer);
	        t.start();
	}

}