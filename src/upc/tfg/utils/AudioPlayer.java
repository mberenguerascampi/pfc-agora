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
		
		BufferedInputStream myStream = new BufferedInputStream(getClass().getResourceAsStream(Constants.fileAudioUrl+ "Downstream.wav")); 
		AudioInputStream audio2 = null;
		try {
			audio2 = AudioSystem.getAudioInputStream(myStream);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		}
		
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		 try {
			clip.open(audio2);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		clip.start();
	}
	
	public void stopPlayBack(){
		p.stop();
	}

}
