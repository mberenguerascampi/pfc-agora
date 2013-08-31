package upc.tfg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import sun.audio.*;

public class AudioPlayer extends Thread {
	private AudioStream as;
	private AudioPlayer p;
	private boolean playback;
	
	public void run() {
	    startPlayback();
	}
	
	private void setRandom() {
	    File[] files = getTracks();
	    try {
	        String f = files[(int) (Math.random() * (files.length - 1))].getAbsolutePath();
	        System.out.println("Now Playing: " + f);
	        as = new AudioStream(new FileInputStream(f));
	    } catch (IOException ex) {
	        
	    }
	}
	
	public void startPlayback() {
	    playback = true;
	    setRandom();
	    p = new AudioPlayer();
	    p.startPlayback();
	    //p.player.start(as);
	    try {
	        do {
	        } while (as.available() > 0 && playback);
	        if (playback) {
	            startPlayback();
	        }
	    } catch (IOException ex) {
	        
	    }
	
	}
	
	public void stopPlayback() {
	    playback = false;
	    p.stopPlayback();
	}
	
	private File[] getTracks() {
	    File dir = new File(System.getProperty("user.dir") + "\\music");
	    File[] a = dir.listFiles();
	    ArrayList<File> list = new ArrayList<File>();
	    for (File f : a) {
	        if (f.getName().substring(f.getName().length() - 3, f.getName().length()).equals("wav")) {
	            list.add(f);
	        }
	    }
	    File[] ret = new File[list.size()];
	    for (int i = 0; i < list.size(); i++) {
	        ret[i] = list.get(i);
	    }
	    return ret;
	}

}
