package upc.tfg.agora;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import upc.tfg.utils.Constants;

public class TaulerGui extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image imgBackground;
	
	public TaulerGui() {
		URL urlBackgroundImg = getClass().getResource(Constants.fileUrl+"prova2.jpg");
		this.imgBackground =  new ImageIcon(urlBackgroundImg).getImage();
	}
	
	@Override
	public void paintComponent(Graphics g){
		URL urlBackgroundImg = getClass().getResource(Constants.fileUrl+"prova2.jpg");
		this.imgBackground =  new ImageIcon(urlBackgroundImg).getImage();
		g.drawImage(this.imgBackground, 0, 0, null);
    }
}
