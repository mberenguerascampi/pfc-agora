package upc.tfg.gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import upc.tfg.logic.Carta;
import upc.tfg.utils.RotatedIcon;

public class VistaCarta extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7329640154909640312L;
	Carta cartaEntity;
	int jugadorID;
	
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
}
