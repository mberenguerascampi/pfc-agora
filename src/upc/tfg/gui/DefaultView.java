package upc.tfg.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;

public class DefaultView extends JPanel{
	/**
	 * Vista per defecte
	 */
	private static final long serialVersionUID = -4952576131782945923L;
	Locale defaultLocale = Locale.getDefault();
	public ResourceBundle bundle = ResourceBundle.getBundle("AgoraBundle", defaultLocale);
	public JLabel skin;
	private Image img = null;
	public int marginX = 0;
	public int marginY = 0;
	public VistaBarraSuperior vbs = null;

	public void paintComponent(Graphics page)
	{
	    super.paintComponent(page);
	    if(img == null){
	    	URL urlImg = getClass().getResource(Constants.fileUrl+"default_background.jpg");
			try {
				img = ImageIO.read(urlImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
//	    ImageIcon icon = new ImageIcon(urlBackgroundImg);
//	    Image img = icon.getImage();
	    page.drawImage(img, 0, 0, this);
	}
	
	/**
	 * Acci� per afegir un background 
	 * @param name Nom de la imatge que utilitzarem de background
	 */
	public void addSkin(String name)
	{
		skin = new JLabel("");
        skin.setLayout(null);
        
        //Agafem la imatge i la posem a la mida que volem
        URL urlBackgroundImg = getClass().getResource(Constants.fileUrl+name);
        ImageIcon icon = new ImageIcon(urlBackgroundImg);
        Image tempImg = icon.getImage();
        Image newimg = tempImg.getScaledInstance( Constants.width, Constants.height-marginY,  java.awt.Image.SCALE_SMOOTH ) ;  
	    icon = new ImageIcon( newimg );
	    
        skin.setBounds(Constants.paddingX, Constants.paddingY+marginY, Constants.width, Constants.height-marginY);
        skin.setIcon(icon);
        add(skin);
	}
	
	/**
	 * Acci� per definir un nom per a la imatge de background
	 * @param backgroundName Nom de la imatge de background
	 */
	public void setBackgroundName(String backgroundName){
		if(backgroundName.contains(".gif")){
			URL urlImg = getClass().getResource(Constants.fileUrl+backgroundName);
			img = Toolkit.getDefaultToolkit().createImage(urlImg);
		}
		else{
			URL urlImg = getClass().getResource(Constants.fileUrl+backgroundName);
			try {
				img = ImageIO.read(urlImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		img = img.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,  java.awt.Image.SCALE_SMOOTH);
	}
	
	/**
	 * Acci� que actualitza l'idioma en que agafem els Strings
	 */
	public void updateBundle(){
		defaultLocale = Locale.getDefault();
		bundle = ResourceBundle.getBundle("AgoraBundle", defaultLocale);
	}
	
	/**
	 * Acci� per afegir la vista VistaBarraSuperior
	 * @param title T�tol que es mostra en la barra
	 * @param listener Listener que s'encarregan d'escoltar les accions que l'usuari faci a la vista
	 */
	public void afegeixBarraSuperior(String title, VistaAmbBotoTornarListener listener){
		vbs = new VistaBarraSuperior(title, listener);
		vbs.setBounds(0, 0, VistaBarraSuperior.BAR_WIDTH, VistaBarraSuperior.BAR_HEIGHT);
		add(vbs);
		marginY = VistaBarraSuperior.BAR_HEIGHT;
		//img = img.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height-marginY,  java.awt.Image.SCALE_SMOOTH);
		repaint();
	}
	
	public void setVisible(boolean aFlag){
		super.setVisible(aFlag);
	}
}
