package upc.tfg.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.CannotRealizeException;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startPlayback() throws IOException {
		
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
		//clip.start();
	}
	
	public void stopPlayBack(){
		p.stop();
	}

}
