package com.adp.clocks.synergy2416.demo;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

class FPIdWorker extends SwingWorker<String, Void> {
	
	public static AtomicInteger m_nThread = new AtomicInteger(0);
	private JLabel m_lbl;
	private Timer m_idTimer;
	
	FPIdWorker(JLabel lbl, Timer tm){
		m_lbl = lbl;
		m_idTimer = tm;
	}
    @Override
    public String doInBackground() {
    	m_nThread.getAndIncrement();
        return FPU.identifyEmployee();
    }

    @Override
    protected void done() {
        try { 
        	String strResult = get();
        	System.out.println(strResult);
        	if (strResult.compareTo("Succeed!") == 0) {
              	m_lbl.setText("<html><font color=black>Punch Accepted!</font></html>");
              	m_lbl.repaint();
              	FPU.Light.RED.off();
              	FPU.Light.GREEN.on();
              	MainWindow.getM_ap().playSuccessSound();
              } else {
              	m_lbl.setText("<html><font color=black>"+strResult+"</font></html>");
            	m_lbl.repaint();
              	FPU.Light.GREEN.off();
              	FPU.Light.RED.on();
              	MainWindow.getM_ap().playKeypadSound();
              }
        } catch (InterruptedException e) {
        	System.out.println("after catch");
        	m_nThread.getAndDecrement();
        	return;
            // This is thrown if the thread's interrupted.
        } catch (ExecutionException e) {
        	System.out.println("after catch");
        	m_nThread.getAndDecrement();
        	return;
            // This is thrown if we throw an exception
            // from doInBackground.
        } catch (CancellationException e) {
            //Do your task after cancellation
        	m_nThread.getAndDecrement();
        	System.out.println("at cancellation interruption");
        	return;
        	
        }
        System.out.println("after catch");
        m_nThread.getAndDecrement();
        m_idTimer.start(); 
    }
}
