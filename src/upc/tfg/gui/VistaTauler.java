package upc.tfg.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import upc.tfg.interfaces.TaulerListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.Posicio;

public class VistaTauler extends DefaultView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5693342904886251734L;
	private static final int IMG_TAULER_WIDTH = 538;
	private static final int IMG_TAULER_HEIGHT = 388;
	private static final int CARTA_WIDTH = 74;
	private static final int CARTA_HEIGHT = 105;
	
	TaulerListener listener;
	ArrayList<JButton> cartes = new ArrayList<JButton>();
	
	public VistaTauler(TaulerListener tListener) {
		listener = tListener;
		setLayout(null);
		setSize(Constants.width, Constants.height);
	}
	
	public void setVisible(boolean visibility)
	{
		super.setVisible(visibility);
		if(visibility){
			mostraCartes();
			addTauler();
			addSkin(Constants.fileUrl+"tauler_background.jpg");
		}
	}
	
	/**
	 * Mostra en la pantalla del tauler la carta amb identificador cartaID en les cartes del jugador amb 
	 * identificador jugadorID
	 * @param jugadorID identificador del jugador
	 * @param cartaID identificador de la carta
	 * @param posicio posici� que ocupa la carta dintre del conjunt de cartes del jugador
	 */
	public void afegeixCarta(int jugadorID, int posicio, int cartaID)
	{
		JButton carta = new JButton();
		carta.setOpaque(false);
		carta.setLayout(null);
		Posicio pos = getCardPosition(jugadorID, posicio);
		int x = pos.x;
		int y = pos.y;
		carta.setBounds(x, y, CARTA_WIDTH, CARTA_HEIGHT);
		carta.setFocusPainted(false); 
		carta.setContentAreaFilled(false); 
		carta.setBorderPainted(false);
		
		//Afegim la imatge de la carta al but�
		URL urlImg = getClass().getResource(Constants.fileUrl+"agora.png");
	    ImageIcon icon = new ImageIcon(urlImg);
	    Image tempImg = icon.getImage() ;  
	    Image newimg = tempImg.getScaledInstance(CARTA_WIDTH, CARTA_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
	    icon = new ImageIcon(newimg);
	    carta.setIcon(icon);
	    
	    //Si pulsem la carta es crida el m�tode corresponent
	    final int jugadorSeleccionat = jugadorID;
	    final int cartaSeleccionada = cartaID;
	    carta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.cartaSeleccionada(jugadorSeleccionat, cartaSeleccionada);
			}
		});
	    
	    cartes.add(carta);
	}
	
	private Posicio getCardPosition(int jugadorID, int posicio)
	{
		//Inicialitzem x i y a la posici� (0,0) en el tauler
		int x = Constants.paddingX + (Constants.width/2) - (IMG_TAULER_WIDTH/2);
	    int y = Constants.paddingY + (Constants.height/2) - (IMG_TAULER_HEIGHT/2);
	    
	    final int margin_top = 10;
	    
	    switch(jugadorID){
	    	case 1:
	    		y += IMG_TAULER_HEIGHT + margin_top;
	    		break;
	    	case 2:
	    		
	    		break;
	    	case 3:
	    		y -= margin_top;
	    		break;
	    	case 4:
	    		break;
	    }
	    
	    return new Posicio(x, y);
	}
	
	private void mostraCartes()
	{
		for (JButton carta:cartes){
			add(carta);
		}
	}
	
	
	private void addTauler()
	{
		JLabel tauler_img = new JLabel("");
        tauler_img.setLayout(null);
        
        //Agafem la imatge i la posem a la mida que volem
        URL urlTaulerImg = getClass().getResource(Constants.fileUrl+"tauler_img.jpg");
        ImageIcon icon = new ImageIcon(urlTaulerImg);
        Image tempImg = icon.getImage();
        Image newimg = tempImg.getScaledInstance( IMG_TAULER_WIDTH, IMG_TAULER_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
	    icon = new ImageIcon( newimg );
	    
	    //Coloquem el tauler al centre de la imatge
	    int x = Constants.paddingX + (Constants.width/2) - (IMG_TAULER_WIDTH/2);
	    int y = Constants.paddingY + (Constants.height/2) - (IMG_TAULER_HEIGHT/2);
	    tauler_img.setBounds(x, y, IMG_TAULER_WIDTH, IMG_TAULER_HEIGHT);
	    tauler_img.setSize(IMG_TAULER_WIDTH, IMG_TAULER_HEIGHT);
	    tauler_img.setIcon(icon);
        add(tauler_img);
	}

}
