package upc.tfg.gui;


import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import upc.tfg.logic.Baralla;
import upc.tfg.utils.Constants;

public class VistaBaralla extends JPanel{
	public static final int BARALLA_HEIGHT = 115;//VistaTauler.CARTA_HEIGHT+10;
	public static final int BARALLA_WIDTH = 84;//VistaTauler.CARTA_WIDTH+10;
	private Image img = null;
	
	Baralla baralla;
	VistaCarta vistaCarta;

	public VistaBaralla(Baralla baralla) {
		setLayout(null);
		setOpaque(false);
		this.baralla = baralla;
		vistaCarta = new VistaCarta(baralla.getCartes().get(1), 1, 0);
		vistaCarta.setBounds(10, 0, VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT);
		add(vistaCarta);
	}
	
	public void updateView(){
		vistaCarta.setCartaEntity(baralla.getCartes().get(1));
	}
	
	public void paintComponent(Graphics page)
	{
	    super.paintComponent(page);
	    if(img == null){
	    	URL urlImg = getClass().getResource(Constants.fileUrl+"baralla.png");
			try {
				img = ImageIO.read(urlImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    page.drawImage(img, 0, 0, null);
	}
}
