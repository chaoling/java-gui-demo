package com.adp.clocks.synergy2416.demo;

//This program shows a series of input dialog boxes.
//The next dialog is launched on the closing of the current dialog. 
//It provides examples of how to create dialog boxes
//with a text field, combo box and list box.

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.Icon;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

public class DemoInput extends JFrame{
  
  private JTextArea tracker;
  private static int badgeID = 0;
  private static String input;
  
  //Using a standard Java icon
  private Icon optionIcon = UIManager.getIcon("FileView.computerIcon");
  
  //Application start point   
  
  public DemoInput()
  {
     input =  JOptionPane.showInputDialog(this 
              ,"Enter user ID:");
      
      Response(input);
           
  }
  
  //Append the picked choice to the tracker JTextArea
  public void Response(String response)
  {
      //showInputDialog method returns null if the dialog is exited
      //without an option being chosen
      if (response == null)
      {
          tracker.append("\nYou closed the dialog without any input..");
      }
      else
      {
          System.out.println("\nYou picked " + response + "..");
      }
 
  }
  
  public static String getBadgeID(){
	  return input;
  }
 
}