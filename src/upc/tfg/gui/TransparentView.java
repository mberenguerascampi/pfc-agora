package upc.tfg.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import upc.tfg.utils.Constants;

public class TransparentView extends JPanel {

	public void paintComponent(Graphics g)
	{
		//  Dispaly image at full size
		Image img = null;
	    URL urlImg = getClass().getResource(Constants.fileUrl+"transparent_background.png");
		try {
			img = ImageIO.read(urlImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, 0, 0, null); 	
		super.paintComponent(g);
	}
}
