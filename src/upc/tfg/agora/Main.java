package upc.tfg.agora;

import java.io.IOException;

import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import javax.swing.UIManager;

import upc.tfg.logic.ControladorLogic;
import upc.tfg.logic.GameLoop;
import upc.tfg.utils.AudioPlayer;

public class Main {
	static Agora agora;
	
	public static void main(String[] args) {
		 try {  
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {  
	            public void run() {  
	            	try {
	            		agora = new Agora();
	            		AudioPlayer ap = new AudioPlayer();
	            		ap.startPlayback();
	            		gameLoop();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();}
					catch (NoPlayerException e) {
						e.printStackTrace();
					} catch (CannotRealizeException e) {
						e.printStackTrace();
					}  
	            }  
	        });  
	}
	
	static void gameLoop() throws InterruptedException {
    }
}
