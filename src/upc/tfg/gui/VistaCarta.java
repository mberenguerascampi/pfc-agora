package upc.tfg.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import upc.tfg.logic.Carta;
import upc.tfg.utils.Constants;
import upc.tfg.utils.RotatedIcon;

public class VistaCarta extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7329640154909640312L;
	private Carta cartaEntity;
	private int jugadorID;
	private boolean seleccionada;
	private int posicio;
	private boolean estaBuida = false;
	
	public VistaCarta(){
		setOpaque(false);
		setLayout(null);
		setFocusPainted(false); 
		setContentAreaFilled(false); 
		setBorderPainted(false);
		jugadorID = 1;
		posicio = 0;
	}
	
	public VistaCarta(Carta carta, int jugadorID, int posicio) {
		setOpaque(false);
		setLayout(null);
		setFocusPainted(false); 
		setContentAreaFilled(false); 
		setBorderPainted(false);
		this.cartaEntity = carta;
		this.jugadorID = jugadorID;
		this.posicio = posicio;
		addImageCard();
	}
	
	private void addImageCard()
	{ 
	    Image img = null;
	    if(estaBuida){
	    	URL urlImg = getClass().getResource(Constants.fileUrl+"cartes/marcCarta.png");
	    	try {
				img = ImageIO.read(urlImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    else if(cartaEntity.isShowing()){
	    	img = cartaEntity.getImage().getScaledInstance(VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
	    }
	    else{
	    	img = cartaEntity.getDistricte().getImage().getScaledInstance(VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ; 
	    }
	    ImageIcon icon = new ImageIcon(img);
	    RotatedIcon rIcon = null;
	    
	    switch(jugadorID){
	    	case 1:
	    		setIcon(icon);
	    		break;
	    	case 2:
	    		rIcon = new RotatedIcon(icon, -90);
	    		setIcon(rIcon);
	    		break;
	    	case 3:
	    		rIcon = new RotatedIcon(icon, 180);
	    		setIcon(rIcon);
	    		break;
	    	case 4:
	    		rIcon = new RotatedIcon(icon, 90);
	    		setIcon(rIcon);
	    		break;
	    }
	}
	
	public void updateView(){
		addImageCard();
	}

	public boolean isSeleccionada() {
		return seleccionada;
	}

	public void setSeleccionada(boolean seleccionada) {
		this.seleccionada = seleccionada;
		repaint();
	}
	
	public Carta getCartaEntity(){
		return cartaEntity;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (seleccionada){
			Image img = null;
		    URL urlImg = getClass().getResource(Constants.fileUrl+"transparent_background.png");
			try {
				img = ImageIO.read(urlImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img, 0, 0, null); 	
		}
	}

	public int getPosicio() {
		return posicio;
	}

	public void setPosicio(int posicio) {
		this.posicio = posicio;
	}

	public void setCartaEntity(Carta cartaEntity) {
		this.cartaEntity = cartaEntity;
		updateView();
	}

	public boolean isEstaBuida() {
		return estaBuida;
	}

	public void setEstaBuida(boolean estaBuida) {
		this.estaBuida = estaBuida;
	}

	public int getJugadorID() {
		return jugadorID;
	}

	public void setJugadorID(int jugadorID) {
		this.jugadorID = jugadorID;
	}
}
