package upc.tfg.utils;

import java.awt.Color;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class CustomDefaultButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7816279926001126567L;
	public static final int BUTTON_WIDTH = 185;
	public static final int BUTTON_HEIGHT = 62;
	
	public CustomDefaultButton(String text) {
		setDefaultSettings(text);
		setIconBig(BUTTON_WIDTH, BUTTON_HEIGHT);
		setButtonText(text, Color.WHITE);
	}
	
	public CustomDefaultButton(String text, int width, int height, Color color) {
		setDefaultSettings(text);
		setIconBig(width, height);
		setButtonText(text, color);
	}
	
	private void setDefaultSettings(String text){
		setOpaque(false);
		setToolTipText(text);
		setBounds(16, 120, 80, 24);
		setFocusPainted(false); 
		setContentAreaFilled(false); 
		setBorderPainted(false);
	}
	
	private void setButtonText(String text, Color color){
		//Afegim el text al botó
		setText(text);
		setVerticalTextPosition(CENTER);
		setHorizontalTextPosition(CENTER);
		setFont(Constants.fontButton);
		//setFont(Constants.fontKristenSmall);
		setForeground(color);
	}
	
	private void setIconBig(int width, int height){
		//Configurem el fons del butó per els tres possibles estats que té
		URL urlImg = getClass().getResource(Constants.fileUrl+"default_button_background.png");
	    ImageIcon icon = new ImageIcon(urlImg);
	    Image tempImg = icon.getImage() ;  
	    Image newimg = tempImg.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;  
	    icon = new ImageIcon( newimg );
	    
	    URL urlRolloverImg = getClass().getResource(Constants.fileUrl+"default_button_rollover_background.png");
	    ImageIcon rolloverIcon = new ImageIcon(urlRolloverImg);
	    tempImg = rolloverIcon.getImage() ;  
	    newimg = tempImg.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;  
	    rolloverIcon = new ImageIcon( newimg );
	    
	    URL urlPressedImg = getClass().getResource(Constants.fileUrl+"default_button_pressed_background.png");
	    ImageIcon pressedIcon = new ImageIcon(urlPressedImg);
	    tempImg = pressedIcon.getImage() ;  
	    newimg = tempImg.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;  
	    pressedIcon = new ImageIcon( newimg );
	    
	    
		setIcon(icon);
		setRolloverIcon(rolloverIcon);
		setPressedIcon(pressedIcon);
	}

}
