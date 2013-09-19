package upc.tfg.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.*;
import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.media.MediaPlayer;

public class AudioPlayer extends Thread {
	private static AudioPlayer instance = null;
	Player p;
	private static final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
	
	public AudioPlayer() {
		instance = this;
	}
	
	public static AudioPlayer getInstance(){
		if(instance != null)return instance;
		else return new AudioPlayer();
	}
	
	public void run() {
	    try {
			startPlayback();
		} catch (NoPlayerException e) {
			e.printStackTrace();
		} catch (CannotRealizeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startPlayback() throws NoPlayerException, CannotRealizeException, MalformedURLException, IOException {
		
		BufferedInputStream myStream = new BufferedInputStream(getClass().getResourceAsStream(Constants.fileAudioUrl+ "QuickSilver.wav")); 
		AudioInputStream audio2 = null;
		try {
			audio2 = AudioSystem.getAudioInputStream(myStream);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		InputStream stream=new FileInputStream(Constants.fileAudioUrl+ "QuickSilver.wav");
//		 AudioInputStream as = new AudioInputStream(stream, null, 1000);
		 Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			clip.open(audio2);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 clip.start();
//		
		//MediaLocator ml = new MediaLocator(url);
		//p = Manager.createPlayer(ml);
		// Start the music
		//p.start();
	}
	
	public void stopPlayBack(){
		p.stop();
	}

}
