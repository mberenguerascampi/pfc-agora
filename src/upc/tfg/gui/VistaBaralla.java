package upc.tfg.gui;


import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import upc.tfg.logic.Baralla;
import upc.tfg.utils.Constants;

public class VistaBaralla extends JPanel{
	/**
	 * Vista que representa una baralla de cartes
	 */
	private static final long serialVersionUID = 8352073768985693808L;
	public static final int BARALLA_HEIGHT = 115;//VistaTauler.CARTA_HEIGHT+10;
	public static final int BARALLA_WIDTH = 84;//VistaTauler.CARTA_WIDTH+10;
	private Image img = null;
	
	Baralla baralla;
	VistaCarta vistaCarta;

	/**
	 * Constructora de la classe
	 * @param baralla Baralla que es vol representar en la capa de presentació
	 */
	public VistaBaralla(Baralla baralla) {
		setLayout(null);
		setOpaque(false);
		this.baralla = baralla;
		vistaCarta = new VistaCarta(baralla.getCartes().get(1), 1, 0);
		vistaCarta.setBounds(10, 0, VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT);
		add(vistaCarta);
	}
	
	/**
	 * Acció per actualitzar la vista amb la informació correcte de la capa de domini
	 */
	public void updateView(){
		if(baralla.getCartes().size() > 1){
			vistaCarta.setCartaEntity(baralla.getCartes().get(1));
		}
		else if(baralla.getCartes().size() > 0){
			vistaCarta.setCartaEntity(baralla.getCartes().get(0));
		}
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
