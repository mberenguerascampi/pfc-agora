package upc.tfg.agora;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.UIManager;

import upc.tfg.utils.*;

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
	            		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	            		BufferedInputStream myStream = new BufferedInputStream(getClass().getResourceAsStream(Constants.fileFontUrl+"Anna.ttf")); 
	            		//ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/src/"+Constants.fileFontUrl+"Anna.ttf")));
	            		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
	            		DefaultDataBase.createDataBase();
	            		DefaultDataBase.createPartidaDataBase();
	            		Locale catLocale = new Locale("ca", "CAT");
	            		Locale.setDefault(catLocale);
	            		agora = new Agora();
	            		AudioPlayer ap = new AudioPlayer();
	            		ap.startPlayback();
	            		gameLoop();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();} 
	            	catch (FontFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }  
	        });  
	}
	
	static void gameLoop() throws InterruptedException {
    }
}
