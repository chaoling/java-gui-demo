package com.adp.clocks.synergy2416.demo;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;

public class DemoFPU implements FingerPrintEnrollmentHandler, Employee{
	static DemoFPU fpu = new DemoFPU();

	//static DemoDialog badge = new DemoDialog("Please Place Finger");
	private static final String OS = System.getProperty("os.name").toLowerCase();
	static{
		if(OS.contains("linux")){
			System.loadLibrary("synergydemo");
			DemoFPU.FP_OPENDEVICE("/root/fingers/");
		}
	}
	
	private static native void REDON();
	private static native void REDOFF();
	private static native void GREENON();
	private static native void GREENOFF();
	
	/**
	 * Opens The FPU DEVICE
	 * @param templateFolder Templates are stored and loaded from a folder specified. Templates are stored with the following %Badge_%FingerNum.template
	 * @return 0 as a long if successful anything else means the it failed to open
	 */
	
	private static native int FP_OPENDEVICE(String templateFolder);
	
	
	/**
	 * Closes The FPU Device
	 * @return  0 as a long if successful anything else means the it failed to close
	 */
	private static native int FP_CLOSEDEVICE();
	
	/**
	 * Enrolls a new employee.. No visual/audio feed back to the user. In order to enrol an employee the finger must be presented to the reader
	 * and with drawn from the reader 3 times. IE enrollment is called- user is prompted to place finger. finger is placed. finger is withdrawn
	 * for ~1-2 seconds. User is prompted to place finger again. Finger is placed. finger is withdrawn for ~1-2 seconds. finger is placed a third
	 * and last time. Enrollment is complete.
	 * @param badge Badge number of the employee
	 * @param fingerNum is the number of the finger and should not be < 0 or > 9 (0-9)
	 * @param timeOut is the length of time the finger print reader will wait for a finger to be presented
	 * @param gapTime The amount of time until the next onReadyForFinger Call back is made after a successful Read;
	 * @param enrollmentHandler is the Java call back interface.
	 * @return <li><code>-103</code> Bad Finger Read - The Finger print sensor failed to pickup a finger or the image was distorted or unreadable. This can sometimes be caused by a lack of moisture on the finger or a foreign substance that obstructs the read </li>
	 * 		   <li><code>-107</code> Bad Finger Read or No finger Present - The Finger print sensor failed to pickup a finger or the image was distorted or unreadable. This can sometimes be caused by a lack of moisture on the finger or a foreign substance that obstructs the read. Or the sensor has no finger on it </li>
	 * 		   <li><code>-108</code> There are too many templates in the fingerprint reader to begin enrollment. Each fingerprint reader can only support a certain number of templates. Usually 3,000 or 10,000. Consult with the manufacturer if this problem arises and you believe you are under the proper threshold.</li>
	 * 		   <li><code>-109</code> Bad Badge, The badge you entered is not supported.</li>
	 * 		   <li><code>-106</code> The finger you are trying to Enroll already exists in the finger print reader under a different badge. You must first delete this finger under the corresponding badge if you wish to enroll this finger under a new badge. You cannot have one finger associated with 2 different badges.</li>
	 * 		   <li><code>-110</code> Fingerprint template failed to save to file. Be sure fingerprint folder location exists and that you have the proper permission to write to this folder. This method will not create the folder if it does not exists the folder location must exist already.
	 * 		   <li><code>0</code> Enrollment Successful</li>
	 **/
	private static native int FP_ENROLE_EMPLOYEE(String badge, int fingerNum, long timeOut, long gapTime,FingerPrintEnrollmentHandler enrollmentHandler );
	
	/**
	 * Validates whether the employee badge is enrolled in the reader
	 * @param badge Badge Number of Employee
	 * @param fingerNum the finger number of the employee
	 * @return  <li><code>-102</code> Badge Number Supplied not found - The Badge Number was not found in the fingerprint database. The badge entered does not correspond to any badge in the reader</li>
	 *			<li><code>0</code> Badge exists in finger print reader.
	 */
	private static native int FP_GET_BADGE_STATUS(String badge, int fingerNum);
	
	/**
	 * Validates finger print of an employee identified by badge. No visual/audio feed back to the user.
	 * @param badge Badge number of the employee to validate
	 * @param fingerNum Finger to be used for validation
	 * @return  <li><code>-101</code> No Templates in Reader - There are no user templates in the reader the enroll count is 0 and cannot validate the badge</li> 
	 *          <li><code>-102</code> Badge Number Supplied not found - The Badge Number was not found in the fingerprint database. The badge entered does not correspond to any badge in the reader</li>
	 *          <li><code>-103</code> Bad Finger Read - The Finger print sensor failed to pickup a finger or the image was distorted or unreadable. This can sometimes be caused by a lack of moisture on the finger or a foreign substance that obstructs the read </li>
	 *          <li><code>-104</code> Finger did not match - The badge was found but the finger supplied for validation did not match to the template in the finger print sensor</li>
	 *          <li><code>0</code> Badge and Finger Validated
	 */
	private static native int FP_VALIDATE_EMPLOYEE(String badge, int fingerNum, long timeOut);
	
	
	/***
	 * Deletes finger print template(s) of an employee.
	 * @param badge  Badge number of the employee
	 * @param fingerNum Finger number of the employee template. A negative value means all finger prints of the employee
	 * @return <li><code>0</code> successfully removed the requested finger print(s) </li>
	 * 		   <li><code>-101</code> No Templates in Reader - There are no user templates in the reader the enroll count is 0 and cannot delete the badge</li> 
	 * 		   <li><code>-102</code> Badge Number Supplied/finger number not found - The Badge Number/finger number was not found in the fingerprint database. The badge entered does not correspond to any badge in the reader</li>
	 */
	private static native int FP_DELETE_TEMPLATE(String badge, int fingerNum);
	
	/**
	 * Gets all known badges from the finger print reader
	 * @return A <code>java.lang.String</code> array containing all known badges. Can't be null.
	 */
	private static native String[] FP_GET_BADGES();
	/**
	 * Gets current number of templates in the finger print sensor
	 * @return A <code>long</code> Number of templates currently in the finger print sensor
	 */
	
	private static native int FP_GET_ENROLECOUNT();
	/**
	 * Gets a finger print template of an employee
	 * @param badge Badge number of an employee
	 * @param fingerNum Finger number of the employee
	 * @return Returns template if it exists or null if it does not exist
	 */
	private static native String FP_GET_TEMPLATE(String badge, int fingerNum);
	
	/**
	 * Loads a base64 encoded representation of a finger template. Then saves the template to a file in a %badge_%fingerNum.template file in the folder given by the FP_OPEN_DEVICE method
	 * New template will replace if a template already exists.
	 * @param badge Badge number of an employee whose template is loaded
	 * @param fingerNum Finger number of the employee
	 * @param template Finger print template in a base64 encoded string to load
	 * @return Returns the status of the action
	 * 		<li><code>0</code> Template was loaded into memory and the file was created successfully
	 * 		<li><code>-100</code> The data supplied to the function. Badge Number, fingernumber or Template format is incorrect. The template was not enrolled and not saved to a file.
	 * 		<li><code>-110</code> Fingerprint template failed to save to file. Be sure fingerprint folder location exists and that you have the proper permission to write to this folder. This method will not create the folder if it does not exists the folder location must exist already.
	 * 		<li>An error message if failed to load</li>
	 */
	private static native int  FP_SET_TEMPLATE(String badge, int fingerNum, String template);
	/**
	 * Identifies finger print of an employee. User presents finger and finger is mapped to a badge. The badge that corresponds to the finger is returned.  No visual/audio feed back to the user.
	 * @return  <li><code>-101</code> No Templates in Reader - There are no user templates in the reader the enroll count is 0 and cannot validate the badge</li> 
	 *          <li><code>-105</code> Finger did not match - The supplied finger did not match any template in the finger print sensor</li>
	 *          <li><code>-107</code> Bad Finger Read or No finger Present - The Finger print sensor failed to pickup a finger or the image was distorted or unreadable. This can sometimes be caused by a lack of moisture on the finger or a foreign substance that obstructs the read. Or the sensor has no finger on it </li>
	 *          <li><code>long</code> badge Number > 0. Finger Identified corresponds to the badge number returned by this function. Will always be greater than 0. Success.
	 */
	
	private static native int FP_IDENTIFY_EMPLOYEE();
	
       
	public enum Light { 
    	   RED,GREEN;
              public void on(){
            	  if(this.compareTo(RED)==0){
            		  REDON();
            		  System.out.println("REDON");
            	  }
            	  else{
            		  GREENON();
            		  System.out.println("REDON");
            	  }
              }
              public void off(){
            	  if(this.compareTo(RED)==0){
            		 REDOFF();
            		  System.out.println("REDOFF");
            	  }
            	  else{
            		 GREENOFF();
            		  System.out.println("GREENOFF");
            	  }
              }
       }

	public static void Enroll() {
		//DemoEvent.badge.setVisible(true);
		//DemoEvent.badge2.setVisible(true);	
		//DemoEvent.badge3.setVisible(true);	
		//DemoEvent.badge4.setVisible(true);	
		System.out.println("FPU Enroll");
		DemoInput badge=new DemoInput();
		
		   String badgeID = DemoInput.getBadgeID();
		   System.out.println("BadgeID: "+ badgeID);
		   System.out.println("Enroll Count: "+DemoFPU.FP_GET_ENROLECOUNT());
		   System.out.println("Enroll Status: "+DemoFPU.FP_ENROLE_EMPLOYEE(badgeID, 1, (long)15000,(long)1000, fpu));
		   System.out.println("Enroll Count: "+DemoFPU.FP_GET_ENROLECOUNT());
		   //DemoEvent.badge4.setVisible(true);	
		   badge =null;	 	
	}

	public static void Verify() {
		//DemoEvent.badge.setgVisible(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//DemoEvent.badge.setgVisible(true);
				int id = -107;
				while(id==-107 || id==-100){
					id = DemoFPU.FP_IDENTIFY_EMPLOYEE();
					System.out.println("Enroll Status: "+ id);
				}
				//DemoEvent.badge.setVisible(false);
				//DemoEvent.badge4.setVisibleTime(true);
			}
		});
		
		
	}
	public static void redOn(){
		DemoFPU.REDON();
	}
	public static void redOff(){
		DemoFPU.REDOFF();
	}
	public static void greenOn(){
		DemoFPU.GREENON();
	}
	public static void greenOff(){
		DemoFPU.GREENOFF();
	}
	@Override
	public void onReadyForFinger(int step, boolean repeatOnReaderError) {
		System.out.println("Please Place Finger Step: "+ step);
		switch(step){
			case 1:
				//DemoEvent.badge.setVisible2(true);			
			break;
			case 2:
				//DemoEvent.badge2.setVisible2(true);		
			break;
			case 3:
				//DemoEvent.badge3.setVisible2(true);
			default:
				//fall through
			break;
		}
		//DemoDialog badge4 = new DemoDialog("Success!");
		/*
		switch(step){
		case 1:
		System.out.println("Please place finger Step: "+ step+ "Error: " + repeatOnReaderError);
		break;
		case 2:
		System.out.println("Please place finger Step: "+ step+ "Error: " + repeatOnReaderError);
		break;
		case 3:
		System.out.println("Please place finger Step: "+ step+ "Error: " + repeatOnReaderError);
		break;
	}
	*/
		
		
	}
	@Override
	public void onFingerPrintRead(int step) {
		System.out.println("Please Remove Finger Step: "+ step);
		switch(step){
		case 1:
			//DemoEvent.badge.setVisible(false);			
		break;
		case 2:
			//DemoEvent.badge2.setVisible(false);		
		break;
		case 3:
			//DemoEvent.badge3.setVisible(false);
		default:
			//fall through
		
		break;
	}
		
	}
	@Override
	public void setBadge(String badge) {
		System.out.println("Badge Identified: "+badge);
	}
	@Override
	public void setStepCount(int count) {
		// TODO Auto-generated method stub
		System.out.println("Start");
		System.out.println("Please place finger "+ count + " times");
		
	}
	

}
