package upc.tfg.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

import upc.tfg.interfaces.TaulerListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.ImageToNumberArray;
import upc.tfg.utils.Posicio;
import upc.tfg.utils.RotatedIcon;

public class VistaTauler extends DefaultView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5693342904886251734L;
	private static final int IMG_TAULER_WIDTH = 508;
	private static final int IMG_TAULER_HEIGHT = 401;
	private static final int CARTA_WIDTH = 74;
	private static final int CARTA_HEIGHT = 105;
	
	private VistaInformacio infoView;
	private int[][]map;
	
	TaulerListener listener;
	ArrayList<JButton> cartes = new ArrayList<JButton>();
	JLabel tauler_img;
	JLabel districte_seleccionat_img;
	private int previousDistrict = -1;
	
	public VistaTauler(TaulerListener tListener) {
		listener = tListener;
		setLayout(null);
		setSize(Constants.width, Constants.height);
		
		//Llegim la matriu guardada que ens mapeja els districtes dins la imatge del tauler
		//readMapMatrix();
		try {
			ImageToNumberArray im = new ImageToNumberArray();
			map = im.pix;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setVisible(boolean visibility)
	{
		super.setVisible(visibility);
		if(visibility){
			mostraCartes();
			addDistrictInformationView();
			addTauler();
			addSkin("tauler_background.jpg");
		}
	}
	
	private void addDistrictInformationView() {
		infoView = new VistaInformacio();
		infoView.setBounds(Constants.width - VistaInformacio.INFORMATION_WIDTH, 150, VistaInformacio.INFORMATION_WIDTH, VistaInformacio.INFORMATION_HEIGHT);
		add(infoView);	
	}

	/**
	 * Mostra en la pantalla del tauler la carta amb identificador cartaID en les cartes del jugador amb 
	 * identificador jugadorID
	 * @param jugadorID identificador del jugador
	 * @param cartaID identificador de la carta
	 * @param posicio posició que ocupa la carta dintre del conjunt de cartes del jugador
	 */
	public void afegeixCarta(int jugadorID, int posicio, int cartaID)
	{
		JButton carta = new JButton();
		carta.setOpaque(false);
		carta.setLayout(null);
		Posicio pos = getCardPosition(jugadorID, posicio);
		int x = pos.x;
		int y = pos.y;
		setCardSize(x, y, carta, jugadorID);
		carta.setFocusPainted(false); 
		carta.setContentAreaFilled(false); 
		carta.setBorderPainted(false);
		
		//Afegim la imatge de la carta al butó
		addImageCard(carta, cartaID, jugadorID);
	    
	    //Si pulsem la carta es crida el mètode corresponent
	    final int jugadorSeleccionat = jugadorID;
	    final int cartaSeleccionada = cartaID;
	    carta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.cartaSeleccionada(jugadorSeleccionat, cartaSeleccionada);
				//TODO: Comprovar si la carta ha estat deixada en una posició vàlida
			}
		});
	    

	    MouseAdapter listener = new MouseAdapter() {
	    	 
	        Point p = null;
	   
	        @Override
	        public void mousePressed(MouseEvent e) {
	          p = e.getLocationOnScreen();
	          System.out.println("Mouse pressed");
	        }
	   
	        @Override
	        public void mouseDragged(MouseEvent e) {
	          JComponent c = (JComponent) e.getSource();
	          Point l = c.getLocation();
	          Point here = e.getLocationOnScreen();
	          c.setLocation(l.x + here.x - p.x, l.y + here.y - p.y);
	          p = here;
	          int x = here.x-tauler_img.getBounds().x;
	          int y = here.y-tauler_img.getBounds().y;
	          if(x > 0 && x < IMG_TAULER_WIDTH && y > 0 && y < IMG_TAULER_HEIGHT){
	        	  //System.out.println(map[y][x]);
	        	  if(map[y][x] != previousDistrict){
		        	  previousDistrict = map[y][x];
		        	  districte_seleccionat_img.setVisible(true);
		        	  switch(map[y][x]){
		        	  	case Constants.LES_CORTS:
		        	  		setDistrictedSelected("seleccionat_corts.png");
		        	  		infoView.setNomDistricte("LES CORTS");
		        	  		break;
		        	  	case Constants.SARRIA_SANT_GERVASI:
		        	  		setDistrictedSelected("seleccionat_sarria.png");
		        	  		infoView.setNomDistricte("SARRIÀ SANT GERVASI");
		        	  		break;
		        	  	case Constants.GRACIA:
		        	  		setDistrictedSelected("seleccionat_gracia.png");
		        	  		infoView.setNomDistricte("GRACIA");
		        	  		break;
		        	  	case Constants.HORTA_GUINARDO:
		        	  		setDistrictedSelected("seleccionat_horta.png");
		        	  		infoView.setNomDistricte("HORTA GUINARDO");
		        	  		break;
		        	  	case Constants.NOU_BARIS:
		        	  		setDistrictedSelected("seleccionat_nou.png");
		        	  		infoView.setNomDistricte("NOU BARRIS");
		        	  		break;
		        	  	case Constants.SANT_ANDREU:
		        	  		setDistrictedSelected("seleccionat_andreu.png");
		        	  		infoView.setNomDistricte("SANT ANDREU");
		        	  		break;
		        	  	case Constants.SANTS_MONTJUIC:
		        	  		setDistrictedSelected("seleccionat_sants.png");
		        	  		infoView.setNomDistricte("SANTS MONTJUIC");
		        	  		break;
		        	  	case Constants.EIXAMPLE:
		        	  		setDistrictedSelected("seleccionat_eixample.png");
		        	  		infoView.setNomDistricte("EIXAMPLE");
		        	  		break;
		        	  	case Constants.SANT_MARTI:
		        	  		setDistrictedSelected("seleccionat_marti.png");
		        	  		infoView.setNomDistricte("SANT MARTI");
		        	  		break;
		        	  	case Constants.CIUTAT_VELLA:
		        	  		setDistrictedSelected("seleccionat_vella.png");
		        	  		infoView.setNomDistricte("CIUTAT VELLA");
		        	  		break;
		        	  	default:
		        	  		districte_seleccionat_img.setVisible(false);
		        	  		break;
		        	  }
	        	  }
	          }
	          else{
	        	  districte_seleccionat_img.setVisible(false);
	          }
	          //System.out.println(here.x + " - " + here.y);
	        }
	      };
        //MouseListener listener = new DragMouseAdapter();
        carta.addMouseListener(listener);
        carta.addMouseMotionListener(listener);

        carta.setTransferHandler(new TransferHandler("icon"));
	    cartes.add(carta);
	}
	
	class DragMouseAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            JComponent c = (JComponent) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            handler.exportAsDrag(c, e, TransferHandler.COPY);
        }
    }
	
	private Posicio getCardPosition(int jugadorID, int posicio)
	{
		//Inicialitzem x i y a la posició (0,0) en el tauler
		int x = Constants.paddingX + (Constants.width/2) - (IMG_TAULER_WIDTH/2);
	    int y = Constants.paddingY + (Constants.height/2) - (IMG_TAULER_HEIGHT/2);
	    
	    final int margin = 10;
	    final int margin_between_cards = CARTA_WIDTH + 4;
	    
	    switch(jugadorID){
	    	case 1:
	    		y += IMG_TAULER_HEIGHT + margin;
	    		if (posicio <= 3){
	    			x += margin_between_cards*(posicio-1);
	    		}
	    		else{
	    			
	    		}
	    		break;
	    	case 2:
	    		x += IMG_TAULER_WIDTH + margin;
	    		y += IMG_TAULER_HEIGHT - CARTA_WIDTH;
	    		break;
	    	case 3:
	    		y -= margin + CARTA_HEIGHT;
	    		x += IMG_TAULER_WIDTH - CARTA_WIDTH;
	    		if (posicio <= 3){
	    			x -= margin_between_cards*(posicio-1);
	    		}
	    		else{
	    			
	    		}
	    		break;
	    	case 4:
	    		x -= margin + CARTA_HEIGHT;
	    		break;
	    }
	    
	    return new Posicio(x, y);
	}
	
	private void addImageCard(JButton carta, int cartaID, int jugadorID)
	{
		URL urlImg = getClass().getResource(Constants.fileUrl+"agora.png");
	    ImageIcon icon = new ImageIcon(urlImg);
	    Image tempImg = icon.getImage() ;  
	    Image newimg = tempImg.getScaledInstance(CARTA_WIDTH, CARTA_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
	    icon = new ImageIcon(newimg);
	    RotatedIcon rIcon = null;
	    
	    switch(jugadorID){
	    	case 1:
	    		carta.setIcon(icon);
	    		break;
	    	case 2:
	    		rIcon = new RotatedIcon(icon, -90);
	    		carta.setIcon(rIcon);
	    		break;
	    	case 3:
	    		rIcon = new RotatedIcon(icon, 180);
	    		carta.setIcon(rIcon);
	    		break;
	    	case 4:
	    		rIcon = new RotatedIcon(icon, 90);
	    		carta.setIcon(rIcon);
	    		break;
	    }
	}
	
	private void setCardSize(int x, int y, JButton carta, int jugadorID)
	{
		switch(jugadorID){
	    	case 1:
	    		carta.setBounds(x, y, CARTA_WIDTH, CARTA_HEIGHT);
	    		break;
	    	case 2:
	    		carta.setBounds(x, y, CARTA_HEIGHT, CARTA_WIDTH);
	    		break;
	    	case 3:
	    		carta.setBounds(x, y, CARTA_WIDTH, CARTA_HEIGHT);
	    		break;
	    	case 4:
	    		carta.setBounds(x, y, CARTA_HEIGHT, CARTA_WIDTH);
	    		break;
		}
	}
	
	private void mostraCartes()
	{
		for (JButton carta:cartes){
			add(carta);
		}
	}
	
	
	private void addTauler()
	{
		districte_seleccionat_img = addTaulerSkin("seleccionat_gracia.png");
		tauler_img = addTaulerSkin("tauler_img2.png");
		districte_seleccionat_img.setVisible(false);
	}
	
	private JLabel addTaulerSkin(String imageName)
	{
		JLabel skin = new JLabel("");
		skin.setLayout(null);
        
        //Agafem la imatge i la posem a la mida que volem
        URL urlTaulerImg = getClass().getResource(Constants.fileUrl+imageName);
        ImageIcon icon = new ImageIcon(urlTaulerImg);
        Image tempImg = icon.getImage();
        Image newimg = tempImg.getScaledInstance( IMG_TAULER_WIDTH, IMG_TAULER_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
	    icon = new ImageIcon( newimg );
	    
	    //Coloquem el tauler al centre de la imatge
	    int x = Constants.paddingX + (Constants.width/2) - (IMG_TAULER_WIDTH/2);
	    int y = Constants.paddingY + (Constants.height/2) - (IMG_TAULER_HEIGHT/2);
	    skin.setBounds(x, y, IMG_TAULER_WIDTH, IMG_TAULER_HEIGHT);
	    skin.setSize(IMG_TAULER_WIDTH, IMG_TAULER_HEIGHT);
	    skin.setIcon(icon);
	    
	    add(skin);
	    return skin;
	}
	
	private void setDistrictedSelected(String imageName)
	{
		 //Agafem la imatge i la posem a la mida que volem
        URL urlTaulerImg = getClass().getResource(Constants.fileUrl+imageName);
        ImageIcon icon = new ImageIcon(urlTaulerImg);
        //Image tempImg = icon.getImage();
        //Image newimg = tempImg.getScaledInstance( IMG_TAULER_WIDTH, IMG_TAULER_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
	    //icon = new ImageIcon( newimg );
	    
	    districte_seleccionat_img.setIcon(icon);
	}
	
	private void readMapMatrix()
	{
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("./src/txt/map_districtes.txt"));
		    String line = br.readLine();
		    int i = 0;
		    map = new int[IMG_TAULER_HEIGHT][IMG_TAULER_WIDTH];
		    
		    while (line != null) {
		    	for(int j = 0; j < IMG_TAULER_WIDTH; ++j){
		    		System.out.println(line);
		    		map[i][j] = line.charAt(j);
		    	}
		    	++i;
		        line = br.readLine();
		    }
		    br.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
				e.printStackTrace();
		}
	}

}
