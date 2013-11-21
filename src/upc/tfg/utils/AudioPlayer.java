package upc.tfg.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer extends Thread {
	private static AudioPlayer instance = null;
	private Clip clip = null;
	private boolean volumeOn = true;
	
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
		
		BufferedInputStream myStream = new BufferedInputStream(getClass().getResourceAsStream(Constants.fileAudioUrl+ "RelaxingBirds.wav")); 
		AudioInputStream audio2 = null;
		try {
			audio2 = AudioSystem.getAudioInputStream(myStream);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		}
		
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
		int start = (int) (audio2.getFrameLength() / 3);
        clip.setLoopPoints(start, -1);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		volumeOn = true;
	}
	
	public void stopPlayBack(){
		clip.stop();
		volumeOn = false;
	}
	
	public void changeState() throws IOException{
		if(volumeOn)stopPlayBack();
		else startPlayback();
	}

	public boolean isVolumeOn() {
		return volumeOn;
	}

	public void setVolumeOn(boolean volumeOn) {
		this.volumeOn = volumeOn;
	}

}
