package upc.tfg.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

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
	
	public VistaCarta(Carta carta, int jugadorID) {
		setOpaque(false);
		setLayout(null);
		setFocusPainted(false); 
		setContentAreaFilled(false); 
		setBorderPainted(false);
		this.cartaEntity = carta;
		this.jugadorID = jugadorID;
		addImageCard();
	}
	
	private void addImageCard()
	{ 
	    Image img = null;
	    if(cartaEntity.isShowing()){
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
}
