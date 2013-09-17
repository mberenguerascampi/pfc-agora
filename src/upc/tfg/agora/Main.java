package upc.tfg.agora;

import java.io.IOException;

import javax.swing.UIManager;

import upc.tfg.logic.ControladorLogic;
import upc.tfg.logic.GameLoop;

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
	            		gameLoop();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}  
	            }  
	        });  
	}
	
	static void gameLoop() throws InterruptedException {
    }
}
