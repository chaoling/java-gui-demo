package com.adp.clocks.synergy2416.demo;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class DemoSound {
	
	public static void PlaySound(String soundName){
		if (soundName == null ){
			soundName = "hello.wav";
		}
		AudioInputStream audioIn = null;
		Clip clip = null;
		try {
			audioIn = AudioSystem.getAudioInputStream(DemoSound.class.getResource("/com/adp/clocks/synergy2416/res/"+soundName));
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	  }
}