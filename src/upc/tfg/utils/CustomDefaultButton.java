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
		setOpaque(false);
		setToolTipText(text);
		setBounds(16, 120, 80, 24);
		setFocusPainted(false); 
		setContentAreaFilled(false); 
		setBorderPainted(false);
		
		//Configurem el fons del butó per els tres possibles estats que té
		URL urlImg = getClass().getResource(Constants.fileUrl+"default_button_background2.png");
	    ImageIcon icon = new ImageIcon(urlImg);
	    Image tempImg = icon.getImage() ;  
	    Image newimg = tempImg.getScaledInstance( BUTTON_WIDTH, BUTTON_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
	    icon = new ImageIcon( newimg );
	    
	    URL urlRolloverImg = getClass().getResource(Constants.fileUrl+"default_button_rollover_background2.png");
	    ImageIcon rolloverIcon = new ImageIcon(urlRolloverImg);
	    tempImg = rolloverIcon.getImage() ;  
	    newimg = tempImg.getScaledInstance( BUTTON_WIDTH, BUTTON_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
	    rolloverIcon = new ImageIcon( newimg );
	    
	    URL urlPressedImg = getClass().getResource(Constants.fileUrl+"default_button_pressed_background2.png");
	    ImageIcon pressedIcon = new ImageIcon(urlPressedImg);
	    tempImg = pressedIcon.getImage() ;  
	    newimg = tempImg.getScaledInstance( BUTTON_WIDTH, BUTTON_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
	    pressedIcon = new ImageIcon( newimg );
	    
		setIcon(icon);
		setRolloverIcon(rolloverIcon);
		setPressedIcon(pressedIcon);
		
		//Afegim el text al botó
		setText(text);
		setVerticalTextPosition(CENTER);
		setHorizontalTextPosition(CENTER);
		setFont(Constants.fontButton);
		//setFont(Constants.fontKristenSmall);
		setForeground(Color.WHITE);
	}

}
