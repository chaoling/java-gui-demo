package com.adp.clocks.synergy2416.demo;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

//public static DemoSound greetingsound = new DemoSound("res/hello.wav");
//public static DemoSound enrollsound = new DemoSound("res/Enroll.wav");
//public static DemoSound beepsound = new DemoSound("res/beep.wav");
//public static DemoSound placefingersound = new DemoSound("res/fingerPlace.wav");
//public static DemoSound placefingeragainsound = new DemoSound("res/fingerPlaceAgain.wav");
//public static DemoSound keypadsound = new DemoSound("res/KeyAccept.wav");
//public static DemoSound successbuzzersound = new DemoSound("res/SuccessBuzzer.wav");        

public class AudioPool {
	
private static final String ResPath = "/com/adp/clocks/synergy2416/res/";

//private Runnable greetingsound;
private Runnable enrollsound;
//private Runnable beepsound;
//private Runnable placefingersound;
//private Runnable placefingeragainsound;
private Runnable keypadsound;
private Runnable successbuzzersound;

ExecutorService pool = Executors.newCachedThreadPool();

/**
 * Call this after initialization and after every change in your config at runtime.
 */
public void reloadAudioSounds() {
    
    //this.greetingsound = new WavePlayer(this.getClass().getResource(ResPath+"hello.wav"));
    this.enrollsound = new WavePlayer(this.getClass().getResource(ResPath+"Enroll.wav"));
    //this.beepsound = new WavePlayer(this.getClass().getResource(ResPath+"beep.wav"));
    //this.placefingersound = new WavePlayer(this.getClass().getResource(ResPath+"fingerPlace.wav"));
    //this.placefingeragainsound = new WavePlayer(this.getClass().getResource(ResPath+"fingerPlaceAgain.wav"));
    this.keypadsound = new WavePlayer(this.getClass().getResource(ResPath+"0.wav"));
    this.successbuzzersound = new WavePlayer(this.getClass().getResource(ResPath+"SuccessBuzzer.wav"));
}

public void playGreetingSound() {
    //this.pool.execute(this.greetingsound);
}

public void playEnrollSound() {
    this.pool.execute(this.enrollsound);
}

public void playBeepSound() {
    //this.pool.execute(this.beepsound);
}

public void playPlaceFingerSound() {
    //this.pool.execute(this.placefingersound);
}

public void playplaceFingerAgainSound() {
    //this.pool.execute(this.placefingeragainsound);
}

public void playKeypadSound() {
    this.pool.execute(this.keypadsound);
}

public void playSuccessSound() {
    this.pool.execute(this.successbuzzersound);
}


/**
 * Call this to savely shutdown the thread pool.
 */
public void shutdown() {
    this.pool.shutdown();
}

private class WavePlayer implements Runnable {

    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 

    private URL soundFile;

    public WavePlayer(URL soundFile) {
        this.soundFile = soundFile;
    }

    @Override
    public void run() {

        try {
            // check if the URL is still accessible!
            this.soundFile.openConnection().connect();
            this.soundFile.openStream().close();
        } catch (Exception e) {
            return;
        }

        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem
                    .getAudioInputStream(this.soundFile);
        } catch (UnsupportedAudioFileException e) {
            return;
        } catch (IOException e) {
            return;
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) {
            return;
        } catch (Exception e) {
            return;
        }

        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[this.EXTERNAL_BUFFER_SIZE];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream
                        .read(abData, 0, abData.length);
                if (nBytesRead >= 0) {
                    auline.write(abData, 0, nBytesRead);
                }
            }
        } catch (IOException e) {
            return;
        } finally {
            auline.drain();
            auline.close();
        }
    }
}

}
	