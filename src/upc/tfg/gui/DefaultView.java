package upc.tfg.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.utils.Constants;

public class DefaultView extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4952576131782945923L;
	public ResourceBundle bundle = ResourceBundle.getBundle("AgoraBundle");

	public void paintComponent(Graphics page)
	{
	    super.paintComponent(page);
	    URL urlBackgroundImg = getClass().getResource(Constants.fileUrl+"default_background.jpg");
	    ImageIcon icon = new ImageIcon(urlBackgroundImg);
	    Image img = icon.getImage();
	    page.drawImage(img, 0, 0, null);
	}
	
	public void addSkin(String path)
	{
		JLabel skin = new JLabel("");
        skin.setLayout(null);
        
        //Agafem la imatge i la posem a la mida que volem
        URL urlBackgroundImg = getClass().getResource(path);
        ImageIcon icon = new ImageIcon(urlBackgroundImg);
        Image tempImg = icon.getImage();
        Image newimg = tempImg.getScaledInstance( Constants.width, Constants.height,  java.awt.Image.SCALE_SMOOTH ) ;  
	    icon = new ImageIcon( newimg );
	    
        skin.setBounds(Constants.paddingX, Constants.paddingY, Constants.width, Constants.height);
        skin.setSize(Constants.width, Constants.height);
        skin.setIcon(icon);
        add(skin);
	}
}
