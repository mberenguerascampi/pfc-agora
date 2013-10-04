package upc.tfg.gui;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

import upc.tfg.interfaces.TaulerListener;
import upc.tfg.logic.Baralla;
import upc.tfg.logic.Carta;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;
import upc.tfg.utils.ImageToNumberArray;
import upc.tfg.utils.Posicio;

public class VistaTauler extends DefaultView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5693342904886251734L;
	private static final int IMG_TAULER_WIDTH = 508;
	private static final int IMG_TAULER_HEIGHT = 401;
	public static final int CARTA_WIDTH = 74;
	public static final int CARTA_HEIGHT = 105;
	public static final int CARTA_DESCARTADA_X = Constants.paddingX+50;
	public static final int CARTA_DESCARTADA_Y = Constants.paddingY+Constants.height-300;
	public static final int BARALLA_MARGIN_X = Constants.paddingX+50;
	public static final int BARALLA_MARGIN_Y = Constants.paddingY+Constants.height/2-200;
	
	private int taulerX;
	private int taulerY;
	private boolean draggingPassejant = false;
	private VistaPassejant passejantEstatic;
	private boolean animationOn = false;
	private boolean cartaSeleccionada = false;
	private Carta cartaEntitySeleccionada = null;
	private VistaCarta vistaCartaSeleccionada = null;
	private String nomDistricteSeleccionat;
	private String nomAnteriorDistricteSeleccionat;
	
	private VistaInformacio infoView;
	private VistaInformacioCarta cardInfoView;
	private VistaEstat stateView;
	private int[][]map;
	private VistaCarta[] cartesIntercanvi = new VistaCarta[4];
	
	private TaulerListener listener;
	private ArrayList<VistaCarta> cartes = new ArrayList<VistaCarta>();
	private JLabel tauler_img;
	private JLabel districte_seleccionat_img;
	private JLabel marcCarta;
	private VistaBaralla vBaralla1;
	private VistaBaralla vBaralla2;
	private VistaCarta vCartaBaralla1;
	private VistaCarta vCartaBaralla2;
	private VistaCarta cartesDescartades;
	private int previousDistrict = -1;
	
	public VistaTauler(TaulerListener tListener) {
		listener = tListener;
		setLayout(null);
		setSize(Constants.width, Constants.height);
		taulerX = Constants.paddingX + (Constants.width/2) - (IMG_TAULER_WIDTH/2)-VistaInformacio.WIDTH/2;
	    taulerY = Constants.paddingY + (Constants.height/2) - (IMG_TAULER_HEIGHT/2)-20;
		
	    //Afegim el listener que ens detecti quin districte seleccionem
	    MouseAdapter listener = new MouseAdapter() {
	    	Point p = null;
			   
	        @Override
	        public void mousePressed(MouseEvent e) {
	          p = e.getLocationOnScreen();
	          int x = p.x-tauler_img.getLocationOnScreen().x;
	          int y = p.y-tauler_img.getLocationOnScreen().y;
	          selectDistrict(x, y);
	          if(previousDistrict == -1)infoView.setVisible(false);
	        }
		};
		addMouseListener(listener);
	    
		//Llegim la matriu guardada que ens mapeja els districtes dins la imatge del tauler
		//readMapMatrix();
		try {
			ImageToNumberArray im = new ImageToNumberArray();
			map = im.pix;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//TODO: Botó per fer proves
		JButton button = new JButton();
		button.setLayout(null);
		button.setBounds(10, 10, 190, 50);
		button.setText("Passar al següent jugador");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VistaTauler.this.listener.nextPlayer();
			}
		});
		button.setVisible(false);
		add(button);
	}
	
	public void setVisible(boolean visibility)
	{
		super.setVisible(visibility);
		if(visibility){
			mostraCartes();
			addDistrictInformationView();
			addCartaInformationview();
			addStateView();
			afegeixMarcCarta();
			addTauler();
			setBackgroundName("tauler_background.jpg");
			//addSkin("tauler_background.jpg");
		}
	}
	
	private void addDistrictInformationView() {
		infoView = new VistaInformacio();
		infoView.setBounds(Constants.paddingX+Constants.width - VistaInformacio.INFORMATION_WIDTH, VistaEstat.ESTAT_HEIGHT, VistaInformacio.INFORMATION_WIDTH, VistaInformacio.INFORMATION_HEIGHT);
		
		//Afegim els listeners als passejants del districte
		configuraPassejantsDistrictes(infoView.vpBlau, infoView.vpBlauDinamic, Constants.BLAU);
		configuraPassejantsDistrictes(infoView.vpVermell, infoView.vpVermellDinamic, Constants.VERMELL);
		configuraPassejantsDistrictes(infoView.vpVerd, infoView.vpVerdDinamic, Constants.VERD);
		configuraPassejantsDistrictes(infoView.vpGroc, infoView.vpGrocDinamic, Constants.GROC);
		
		add(infoView.vpBlauDinamic);
		add(infoView.vpVermellDinamic);
		add(infoView.vpVerdDinamic);
		add(infoView.vpGrocDinamic);
		add(infoView);	
		infoView.setVisible(false);
	}
	
	
	private void configuraPassejantsDistrictes(VistaPassejant vpEstatic, VistaPassejant vpDinamic, int color){
		DragAndDropListener ddListener = new DragAndDropListener(vpDinamic, vpEstatic);
		ddListener.betweenDistricts = true;
		PassejantsDistricteListener pdlistener = new PassejantsDistricteListener(vpEstatic, vpDinamic);
		final Rectangle rect = new Rectangle(infoView.getLocation().x+vpEstatic.getLocation().x,
				infoView.getLocation().y+vpEstatic.getLocation().y, 95, 102);
		vpDinamic.addMouseListener(ddListener);
		vpDinamic.addMouseMotionListener(ddListener);
		pdlistener.color = color;
		pdlistener.rect = rect;
		vpDinamic.addActionListener(pdlistener);
		vpDinamic.setBounds(rect);
	}
	
	private void addCartaInformationview(){
		cardInfoView = new VistaInformacioCarta();
		cardInfoView.setBounds(Constants.paddingX+Constants.width - VistaInformacio.INFORMATION_WIDTH, VistaEstat.ESTAT_HEIGHT, VistaInformacio.INFORMATION_WIDTH, VistaInformacio.INFORMATION_HEIGHT);
		add(cardInfoView);	
		cardInfoView.setVisible(false);
	}
	
	private void addStateView(){
		stateView = new VistaEstat();
		stateView.setBounds(Constants.paddingX+Constants.width - VistaEstat.ESTAT_WIDTH, 0, VistaEstat.ESTAT_WIDTH, VistaEstat.ESTAT_HEIGHT);
		add(stateView);	
	}
	
	/**
	 * Afegeix un número determinat de passejants a la vista del tauler
	 * @param numPassejants
	 * @param jugadorID
	 */
	public void afegeixPassejants(int numPassejants, int jugadorID){
		final VistaPassejant vp = new VistaPassejant(VistaPassejant.PASSEJANT_BLAU, numPassejants);
		Rectangle frame = new Rectangle(taulerX+IMG_TAULER_WIDTH-VistaPassejant.PASSEJANT_WIDTH, taulerY+IMG_TAULER_HEIGHT, 
				VistaPassejant.PASSEJANT_WIDTH, VistaPassejant.PASSEJANT_HEIGHT);
		vp.setBounds(frame);
		passejantEstatic = new VistaPassejant(VistaPassejant.PASSEJANT_BLAU, numPassejants);
		passejantEstatic.setBounds(frame);
		
		//Afegim el listener
		MouseAdapter listener = new DragAndDropListener(vp, passejantEstatic);
		vp.addMouseListener(listener);
		vp.addMouseMotionListener(listener);
		vp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				draggingPassejant = false;
				Thread t;
				if (previousDistrict == -1 || (cartaEntitySeleccionada != null && cartaEntitySeleccionada.getDistricte().getDistricteID() != previousDistrict)){
					t = new Thread(new AnimacioPassejant(vp, passejantEstatic, passejantEstatic.getLocation(), passejantEstatic.getBounds(), false, false));
					//TODO: actualitzar la capa lògica 
				}
				else{
					t = new Thread(new AnimacioPassejant(vp, passejantEstatic, infoView.getLocation(), passejantEstatic.getBounds(), true, false));
					//TODO: actualitzar la vista d'informacio
				}
				
				animationOn = true;
				t.start();
			}
		});
		add(vp);
		add(passejantEstatic);
	}

	/**
	 * Mostra en la pantalla del tauler la carta amb identificador cartaID en les cartes del jugador amb 
	 * identificador jugadorID
	 * @param jugadorID identificador del jugador
	 * @param cartaID identificador de la carta
	 * @param posicio posició que ocupa la carta dintre del conjunt de cartes del jugador
	 */
	public void afegeixCarta(int jugadorID, int posicio, final Carta cartaEntity)
	{
		final VistaCarta carta = new VistaCarta(cartaEntity, jugadorID, posicio);
		Posicio pos = getCardPosition(jugadorID, posicio);
		int x = pos.x;
		int y = pos.y;
		setCardSize(x, y, carta, jugadorID);
		afegeixListenerACarta(cartaEntity, jugadorID, carta);
	    cartes.add(carta);
	}
	
	public void afegeixCartaAPosicioBuida(int jugadorID, final Carta cartaEntity)
	{
		//afegeixCarta(jugadorID, Integer.valueOf(marcCarta.getName()), cartaEntity);
		//add(cartes.get(cartes.size()-1));
		if(jugadorID == 1){
			marcCarta.setVisible(false);
			vistaCartaSeleccionada.setEstaBuida(false);
			vistaCartaSeleccionada.setBounds(marcCarta.getBounds());
			vistaCartaSeleccionada.setCartaEntity(cartaEntity);
			vistaCartaSeleccionada.setEnabled(true);
			vistaCartaSeleccionada.setSeleccionada(false);
			vistaCartaSeleccionada.removeActionListener(vistaCartaSeleccionada.getActionListeners()[0]);
			afegeixListenerACarta(cartaEntity, jugadorID, vistaCartaSeleccionada);
			vistaCartaSeleccionada.updateView();
			vistaCartaSeleccionada = null;
		}
		else{
			for(VistaCarta vc:cartes){
				if(vc.getJugadorID() == jugadorID && vc.isEstaBuida()){
					vc.setEstaBuida(false);
					vc.setCartaEntity(cartaEntity);
					vc.setEnabled(true);
					vc.setSeleccionada(false);
					vc.removeActionListener(vc.getActionListeners()[0]);
					afegeixListenerACarta(cartaEntity, jugadorID, vc);
					vc.updateView();
				}
			}
		}
		updateView();
	}
	
	private void afegeixListenerACarta(final Carta cartaEntity, final int jugadorID, final VistaCarta carta){
		 //Si pulsem la carta es crida el mètode corresponent
	    final int jugadorSeleccionat = jugadorID;
	    if(jugadorID == 1){
	    	carta.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!cartaSeleccionada && Partida.getInstance().getPas() == 2 && Partida.getInstance().getIdJugadorActual() == jugadorID){
						listener.cartaSeleccionada(jugadorSeleccionat, cartaEntity);
						Rectangle rect = carta.getBounds();
						carta.setBounds(rect.x, rect.y-25, rect.width, rect.height);
						if(vistaCartaSeleccionada != null){
							rect = vistaCartaSeleccionada.getBounds();
							vistaCartaSeleccionada.setBounds(rect.x, rect.y+25, rect.width, rect.height);
						}
						cartaEntitySeleccionada = cartaEntity;
						vistaCartaSeleccionada = carta;
						cardInfoView.setCarta(cartaEntity);
						infoView.setVisible(false);
						cardInfoView.setVisible(true);
					}
					else if(cartaEntitySeleccionada == cartaEntity){
						infoView.setVisible(false);
						cardInfoView.setVisible(true);
					}
				}
			});
	    }
	    
	    else{
	    		carta.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO: Comprovar que les condicions son les adequades
					if(Partida.getInstance().getPas() != 1) return;
					if(!cartaSeleccionada){
						cardInfoView.setCarta(cartaEntity);
						infoView.setVisible(false);
						cardInfoView.setVisible(true);
						cardInfoView.setSelectVisible(true);
						for(ActionListener l:cardInfoView.selectButton.getActionListeners()){
							cardInfoView.selectButton.removeActionListener(l);
						}
						cardInfoView.selectButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								cartesIntercanvi[0] = carta;
								carta.setSeleccionada(true);
								listener.cartaRobada(1, cartaEntity);
							}
						});
					}
					else if(cartaEntitySeleccionada == cartaEntity){
						infoView.setVisible(false);
						cardInfoView.setVisible(true);
					}
				}
			});
	    }
	}
	
	public void seleccionaCartaARobar(int jugadorID, Carta cartaEntity){
		System.out.println("GUI Carta a robar del jugador" + jugadorID + " del districte " + cartaEntity.getDistricte().getNom());
		int idPropietariCarta = jugadorID-1;
		if (idPropietariCarta == 0)idPropietariCarta = 4;
		for(VistaCarta vc:cartes){
			if (vc.getJugadorID() == idPropietariCarta && vc.getCartaEntity().equals(cartaEntity)) {
				System.out.println("GUI carta trobada");
				vc.setSeleccionada(true);
				cartesIntercanvi[jugadorID-1] = vc;
			}
		}
		ArrayList<Carta> jcartes = Partida.getInstance().getJugador(idPropietariCarta).getCartes();
		for(Carta c:jcartes){
			if (c.equals(cartaEntity)) {
				System.out.println("GUI carta trobada!!!!!!!!!!!!!!");
			}
		}
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
		int x = taulerX;
	    int y = taulerY;
	    
	    final int margin = 10;
	    final int margin_between_cards = CARTA_WIDTH + 4;
	    
	    switch(jugadorID){
	    	case 1:
	    		y += IMG_TAULER_HEIGHT + margin;
	    		x += margin_between_cards*(posicio-1);
	    		break;
	    	case 2:
	    		x += IMG_TAULER_WIDTH + margin;
	    		y += IMG_TAULER_HEIGHT - CARTA_WIDTH - margin_between_cards*(posicio-1);
	    		break;
	    	case 3:
	    		y -= margin + CARTA_HEIGHT;
	    		x += IMG_TAULER_WIDTH - CARTA_WIDTH;
	    		x -= margin_between_cards*(posicio-1);
	    		break;
	    	case 4:
	    		x -= margin + CARTA_HEIGHT;
	    		y += margin_between_cards*(posicio-1);
	    		break;
	    }
	    
	    return new Posicio(x, y);
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
	    skin.setBounds(taulerX, taulerY, IMG_TAULER_WIDTH, IMG_TAULER_HEIGHT);
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
	    districte_seleccionat_img.setIcon(icon);
	}
	
	private void selectDistrict(int x, int y){
		if(x > 0 && x < IMG_TAULER_WIDTH && y > 0 && y < IMG_TAULER_HEIGHT){
	      if(cardInfoView.isVisible())cardInfoView.setVisible(false);
	      if(!infoView.isVisible())infoView.setVisible(true);
      	  if(map[y][x] != previousDistrict){
	        	  previousDistrict = map[y][x];
	        	  //Configurem la visibilitat de les vistes
	        	  if(!districte_seleccionat_img.isVisible())districte_seleccionat_img.setVisible(true);
	        	  switch(map[y][x]){
	        	  	case Constants.LES_CORTS:
	        	  		setDistrictedSelected("seleccionat_corts.png");
	        	  		nomDistricteSeleccionat = "LES CORTS";
	        	  		break;
	        	  	case Constants.SARRIA_SANT_GERVASI:
	        	  		setDistrictedSelected("seleccionat_sarria.png");
	        	  		nomDistricteSeleccionat = "SARRIÀ SANT GERVASI";
	        	  		break;
	        	  	case Constants.GRACIA:
	        	  		setDistrictedSelected("seleccionat_gracia.png");
	        	  		nomDistricteSeleccionat = "GRÀCIA";
	        	  		break;
	        	  	case Constants.HORTA_GUINARDO:
	        	  		setDistrictedSelected("seleccionat_horta.png");
	        	  		nomDistricteSeleccionat = "HORTA GUINARDO";
	        	  		break;
	        	  	case Constants.NOU_BARIS:
	        	  		setDistrictedSelected("seleccionat_nou.png");
	        	  		nomDistricteSeleccionat = "NOU BARRIS";
	        	  		break;
	        	  	case Constants.SANT_ANDREU:
	        	  		setDistrictedSelected("seleccionat_andreu.png");
	        	  		nomDistricteSeleccionat = "SANT ANDREU";
	        	  		break;
	        	  	case Constants.SANTS_MONTJUIC:
	        	  		setDistrictedSelected("seleccionat_sants.png");
	        	  		nomDistricteSeleccionat = "SANTS MONTJUIC";
	        	  		break;
	        	  	case Constants.EIXAMPLE:
	        	  		setDistrictedSelected("seleccionat_eixample.png");
	        	  		nomDistricteSeleccionat = "EIXAMPLE";
	        	  		break;
	        	  	case Constants.SANT_MARTI:
	        	  		setDistrictedSelected("seleccionat_marti.png");
	        	  		nomDistricteSeleccionat = "SANT MARTÍ";
	        	  		break;
	        	  	case Constants.CIUTAT_VELLA:
	        	  		setDistrictedSelected("seleccionat_vella.png");
	        	  		nomDistricteSeleccionat = "CIUTAT VELLA";
	        	  		break;
	        	  	default:
	        	  		districte_seleccionat_img.setVisible(false);
	        	  		previousDistrict = -1;
	        	  		break;
	        	  }
	        	  
	        	  Districte d = Partida.getInstance().getDistricte(nomDistricteSeleccionat);
	        	  if(d!=null)infoView.setDistricte(d);
      	  }
        }
        else{
      	  districte_seleccionat_img.setVisible(false);
      	  previousDistrict = -1;
        }
	}
	
	public void afegeixMarcCarta(){
		marcCarta = new JLabel("");
		marcCarta.setOpaque(false);
		marcCarta.setLayout(null);
		URL urlImg = getClass().getResource(Constants.fileUrl+"cartes/marcCarta.png");
	    ImageIcon icon = new ImageIcon(urlImg);
	    marcCarta.setIcon(icon);
	    marcCarta.setVisible(false);
	    add(marcCarta);
	}
	
	public void visualitzaMarcCarta(VistaCarta carta){
		Rectangle rect = carta.getBounds();
		rect.y += 25;
		marcCarta.setBounds(rect);
		marcCarta.setVisible(true);
		marcCarta.setName(String.valueOf(carta.getPosicio()));
	}
	
	public void afegeixBaralles(){
		ActionListener aListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(agafaCartaBaralla(vCartaBaralla1)){
					listener.cartaAgafada(1, 1);
				}
				else{
					animationOn = true;
					Point goal = vBaralla1.getLocation();
					goal.x += 10;
					Thread t = new Thread(new AnimacioCartes(vCartaBaralla1, goal));
					t.start();
				}
			}
		};
		
		ActionListener aListener2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(agafaCartaBaralla(vCartaBaralla2)){
					listener.cartaAgafada(1, 2);
				}
				else{
					animationOn = true;
					Point goal = vBaralla2.getLocation();
					goal.x += 10;
					Thread t = new Thread(new AnimacioCartes(vCartaBaralla2, goal));
					t.start();
				}
			}
		};
		
		vCartaBaralla1 = new VistaCarta(Partida.getInstance().getBaralla().getCartes().get(0), 1, 0);
		vCartaBaralla1.setBounds(BARALLA_MARGIN_X, BARALLA_MARGIN_Y, VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT);
		DragAndDropListener listener = new DragAndDropListener();
		listener.shouldSelectDistrict = false;
		listener.isCartaBaralla = true;
		vCartaBaralla1.addMouseListener(listener);
		vCartaBaralla1.addMouseMotionListener(listener);
		vCartaBaralla1.addActionListener(aListener);
		add(vCartaBaralla1);
		
		vBaralla1 = new VistaBaralla(Partida.getInstance().getBaralla());
		vBaralla1.setBounds(BARALLA_MARGIN_X-10, BARALLA_MARGIN_Y, VistaBaralla.BARALLA_WIDTH, VistaBaralla.BARALLA_HEIGHT);
		add(vBaralla1);
		
		vCartaBaralla2 = new VistaCarta(Partida.getInstance().getBaralla2().getCartes().get(0), 1, 0);
		vCartaBaralla2.setBounds(BARALLA_MARGIN_X, BARALLA_MARGIN_Y+140, VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT);
		vCartaBaralla2.addMouseListener(listener);
		vCartaBaralla2.addMouseMotionListener(listener);
		vCartaBaralla2.addActionListener(aListener2);
		add(vCartaBaralla2);
		
		vBaralla2 = new VistaBaralla(Partida.getInstance().getBaralla2());
		vBaralla2.setBounds(BARALLA_MARGIN_X-10, BARALLA_MARGIN_Y+140, VistaBaralla.BARALLA_WIDTH, VistaBaralla.BARALLA_HEIGHT);
		add(vBaralla2);
		
		cartesDescartades = new VistaCarta();
		cartesDescartades.setBounds(CARTA_DESCARTADA_X, CARTA_DESCARTADA_Y, CARTA_WIDTH, CARTA_HEIGHT);
		cartesDescartades.setVisible(false);
		cartesDescartades.setEnabled(false);
		add(cartesDescartades);
	}
	
	private boolean agafaCartaBaralla(VistaCarta carta){
		Rectangle rect1 = carta.getBounds();
		Rectangle rect2 = marcCarta.getBounds();
		if(rect2.intersects(rect1))return true;
		return false;
	}
	
	public void treureCartaSeleccionada(){
		visualitzaMarcCarta(vistaCartaSeleccionada);
		Point goal = new Point(CARTA_DESCARTADA_X, CARTA_DESCARTADA_Y);
		animationOn = true;
		cartaSeleccionada = false;
		cartaEntitySeleccionada = null;
		AnimacioCartes anim = new AnimacioCartes(vistaCartaSeleccionada, goal);
		anim.descartada = true;
		Thread t = new Thread(anim);
		t.start();
		System.out.println("Main thread");
	}
	
	public void treureCarta(VistaCarta vc, Point origin){
		Point goal = new Point(CARTA_DESCARTADA_X, CARTA_DESCARTADA_Y);
		animationOn = true;
		AnimacioCartes anim = new AnimacioCartes(vc, goal);
		anim.descartada = true;
		anim.origin = origin;
		Thread t = new Thread(anim);
		t.start();
	}
	
	public void updateView(){
		if(vistaCartaSeleccionada != null){
			vistaCartaSeleccionada.updateView();
			vistaCartaSeleccionada.setSeleccionada(false);
			vistaCartaSeleccionada.setEnabled(false);
		}
		stateView.actualitzaEstat();
		updateBaralla(vCartaBaralla1, vBaralla1, Partida.getInstance().getBaralla());
		updateBaralla(vCartaBaralla2, vBaralla2, Partida.getInstance().getBaralla2());
		vCartaBaralla1.setBounds(BARALLA_MARGIN_X, BARALLA_MARGIN_Y, VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT);
		vCartaBaralla2.setBounds(BARALLA_MARGIN_X, BARALLA_MARGIN_Y+140, VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT);
	}
	
	private void updateBaralla(VistaCarta vCartaBaralla, VistaBaralla vBaralla,Baralla baralla){
		if (baralla.getCartes().size() == 0){
			vCartaBaralla.setVisible(false);
			vBaralla.setVisible(false);
		}
		else vCartaBaralla.setCartaEntity(baralla.getCartes().get(0));
		vBaralla.updateView();
	}
	
	public void intercanviaCartes(){
		cardInfoView.setSelectVisible(false);
		cardInfoView.setVisible(false);
		System.out.println("Intercanviem cartes");
		int n = cartesIntercanvi.length;
		Carta cartaJugador1 = cartesIntercanvi[n-1].getCartaEntity();
		for(int i = n-1; i > 0; --i){
			Carta cartaAIntercanviar = cartesIntercanvi[i-1].getCartaEntity();
			//Si la carta robada era del jugador1 la girem; si la carta robada es la que ha robat el jugador1 tambe la girem
			if(i-1 <= 1)cartaAIntercanviar.girar();
			cartesIntercanvi[i].setCartaEntity(cartaAIntercanviar);
			cartesIntercanvi[i].setSeleccionada(false);
			//Borrem el action listener que tenia i posem el corresponent
			cartesIntercanvi[i].removeActionListener(cartesIntercanvi[i].getActionListeners()[0]);
			afegeixListenerACarta(cartaAIntercanviar, cartesIntercanvi[i].getJugadorID(), cartesIntercanvi[i]);
		}
		cartesIntercanvi[0].setCartaEntity(cartaJugador1);
		cartesIntercanvi[0].setSeleccionada(false);
		cartesIntercanvi[0].removeActionListener(cartesIntercanvi[0].getActionListeners()[0]);
		afegeixListenerACarta(cartaJugador1, cartesIntercanvi[0].getJugadorID(), cartesIntercanvi[0]);
	}
	
	public void seleccionaCartaiMouPassejants(int jugadorID, Carta carta){
		Point origin = null;
		VistaCarta cartaSeleccionadaAux = null;
		for (VistaCarta vc:cartes){
			if(vc.getJugadorID() == jugadorID && vc.getCartaEntity().equals(carta)){
				origin = vc.getLocation();
				seleccionaCarta(vc, jugadorID);
				cartaSeleccionadaAux = vc;
			}
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(cartaSeleccionadaAux == null){
			System.out.println("NULL");
		}
		treureCarta(cartaSeleccionadaAux, origin);
	}
	
	private void seleccionaCarta(VistaCarta vc, int jugadorID){
		Rectangle rect = vc.getBounds();
		if (jugadorID == 1){
			rect.y -= 25;
		}
		else if (jugadorID == 2){
			rect.x -= 25;
		}
		else if (jugadorID == 3){
			rect.y +=25;
		}
		else if (jugadorID == 4){
			rect.x += 25;
		}
		vc.setBounds(rect);
		vc.updateView();
		cardInfoView.setCarta(vc.getCartaEntity());
		infoView.setVisible(false);
		cardInfoView.setVisible(true);
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

	class DragAndDropListener extends MouseAdapter {
		Point p = null;
		VistaPassejant vistaPassejants = null;
		VistaPassejant vistaPassejantEstatic = null;
		public boolean shouldSelectDistrict = true;
		public boolean betweenDistricts = false;
		public boolean isCartaBaralla = false;
		
		public DragAndDropListener() {}
		public DragAndDropListener(VistaPassejant vistaPassejants, VistaPassejant vistaPassejantEstatic) {
			this.vistaPassejants = vistaPassejants;
			this.vistaPassejantEstatic = vistaPassejantEstatic;
		}
		   
        @Override
        public void mousePressed(MouseEvent e) {
          p = e.getLocationOnScreen();
          int x = p.x-tauler_img.getLocationOnScreen().x;
          int y = p.y-tauler_img.getLocationOnScreen().y;
          if(betweenDistricts && previousDistrict != -1)nomAnteriorDistricteSeleccionat = nomDistricteSeleccionat;
          if(shouldSelectDistrict)selectDistrict(x, y);
        }
   
        @Override
        public void mouseDragged(MouseEvent e) {
        	  if(vistaPassejants != null && (Partida.getInstance().getIdJugadorActual() != 1))return;
	          if(betweenDistricts){
	        	  if(Partida.getInstance().getDistricte(nomAnteriorDistricteSeleccionat).getNumPassejants(vistaPassejantEstatic.getiColor()) == 0 || Partida.getInstance().getPas() != 3) return;
	        	  if(Partida.getInstance().esUltimTorn() && vistaPassejantEstatic.getiColor() != Partida.getInstance().getColorJugadorActual())return;
	        	  if(!Partida.getInstance().getDistricte(nomAnteriorDistricteSeleccionat).tePassejantsDisponibles(vistaPassejantEstatic.getiColor()))return;
	          }
	          else if(isCartaBaralla){
	        	  if(Partida.getInstance().getPas() != 4)return;
	        	  if(vistaCartaSeleccionada!=null)vistaCartaSeleccionada.setSeleccionada(cartaSeleccionada);
	          }
	          else {
	        	  if(Partida.getInstance().getPas() != 2)return;
	        	  cartaSeleccionada = true;
	        	  if(vistaCartaSeleccionada!=null)vistaCartaSeleccionada.setSeleccionada(cartaSeleccionada);
	          }
        	  JComponent c = (JComponent) e.getSource();
	          Point l = c.getLocation();
	          Point here = e.getLocationOnScreen();
	          c.setLocation(l.x + here.x - p.x, l.y + here.y - p.y);
	          p = here;
	          int x = here.x-tauler_img.getLocationOnScreen().x;
	          int y = here.y-tauler_img.getLocationOnScreen().y;
	          if(shouldSelectDistrict){
	        	  selectDistrict(x, y);
	          }
	          if(vistaPassejants != null && !draggingPassejant){
	        	  vistaPassejantEstatic.setNum(vistaPassejants.getNum()-1);
	        	  vistaPassejants.setNum(0);
	        	  vistaPassejants.setShowZero(false);
	        	  draggingPassejant = true;
	        	  if(betweenDistricts)infoView.setDraggingPassejant(true, vistaPassejantEstatic.getiColor());
	          }
        }
	}
	
	class PassejantsDistricteListener implements ActionListener{
		public Rectangle rect;
		public int color;
		VistaPassejant vpEstatic;
		VistaPassejant vpDinamic;
		
		public PassejantsDistricteListener(VistaPassejant vpEstatic, VistaPassejant vpDinamic) {
			this.vpEstatic = vpEstatic;
			this.vpDinamic = vpDinamic;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			draggingPassejant = false;
			infoView.setDraggingPassejant(false, color);
			Thread t;
			if (previousDistrict == -1 || !(Partida.getInstance().potMoure(nomAnteriorDistricteSeleccionat, nomDistricteSeleccionat, vpEstatic.getiColor()))){
				t = new Thread(new AnimacioPassejant(vpDinamic, vpEstatic, rect.getLocation(), rect, false, true));
			}
			else{
				t = new Thread(new AnimacioPassejant(vpDinamic, vpEstatic, rect.getLocation(), rect, true, true));
			}
			
			animationOn = true;
			t.start();
		}
	};
	
	class AnimacioPassejant implements Runnable{
		VistaPassejant passejant;
		VistaPassejant vPassejantEstatic;
		Point goal;
		boolean toInfoView;
		Rectangle finalPosition;
		public boolean betweenDistricts = false;
		
		public AnimacioPassejant(VistaPassejant passejant, VistaPassejant vPassejantEstatic, Point goal, Rectangle finalPosition, boolean toInfoView, boolean betweenDistricts) {
			this.passejant = passejant;
			this.goal = goal;
			this.toInfoView = toInfoView;
			this.finalPosition = finalPosition;
			this.vPassejantEstatic = vPassejantEstatic;
			this.betweenDistricts = betweenDistricts;
		}
		
		@Override
		public void run() {
			if(animationOn){
				int i = 0;
				while (passejant.getLocation().x != goal.x || passejant.getLocation().y != goal.y){
					try
					{
						Point current = passejant.getLocation();
						int x = current.x;
						int y = current.y;
						//utilitzem la variable equilibra per tal que el passejant abanci de forma uniforma cap al seu objectiu
						int equilibra = 0;
						if(i%3 != 0){
							if(Math.abs(x-goal.x) > Math.abs(y-goal.y)) equilibra = 1;
							else if (Math.abs(y-goal.y) > Math.abs(x-goal.x)) equilibra = 2;
						}
						if(i%3 == 0)Thread.sleep(1);
						if(x < goal.x) {
							++x;
							if(equilibra != 2 && goal.x - x > 4)x+=4;
						}
						else if (x > goal.x){
							--x;
							if (equilibra != 2 && x - goal.x > 4)x-=4;
						}
						if(y < goal.y){
							++y;
							if(equilibra != 1 && goal.y - y > 4)y+=4;
						}
						else if (y > goal.y){
							--y;
							if (equilibra != 1 && y - goal.y > 4)y-=4;
						}
						passejant.setLocation(x,y);
						++i;
					}
					catch(Exception e) {}
				}
				passejant.setBounds(finalPosition);
				animationOn = false;
				if(!toInfoView){
					if(!betweenDistricts){
						passejant.setNum(vPassejantEstatic.getNum()+1);
					}
					else{
						passejant.setNum(vPassejantEstatic.getNum());
					}
				}
				else{
					if(!betweenDistricts){
						passejant.setNum(vPassejantEstatic.getNum());
						listener.passejantMogut(1, nomDistricteSeleccionat);
						infoView.update();
					}
					else{
						int numPassejants = getNumPassejants();
						passejant.setNum(numPassejants);
						System.out.println(nomAnteriorDistricteSeleccionat + " -> " + nomDistricteSeleccionat);
						listener.passejantMogutEntreDistrictes(nomAnteriorDistricteSeleccionat, nomDistricteSeleccionat, passejant.getiColor());
						infoView.update();
					}
				}
			}
		}
		
		private int getNumPassejants(){
			int numPassejants = 0;
			if(vPassejantEstatic.getColor().equals(VistaPassejant.PASSEJANT_BLAU)){
				numPassejants = Partida.getInstance().getDistricte(nomDistricteSeleccionat).getNumPassejantsBlaus()+1;
			}
			else if(vPassejantEstatic.getColor().equals(VistaPassejant.PASSEJANT_VERMELL)){
				numPassejants = Partida.getInstance().getDistricte(nomDistricteSeleccionat).getNumPassejantsVermells()+1;
			}
			else if(vPassejantEstatic.getColor().equals(VistaPassejant.PASSEJANT_VERD)){
				numPassejants = Partida.getInstance().getDistricte(nomDistricteSeleccionat).getNumPassejantsVerds()+1;
			}
			else if(vPassejantEstatic.getColor().equals(VistaPassejant.PASSEJANT_GROC)){
				numPassejants = Partida.getInstance().getDistricte(nomDistricteSeleccionat).getNumPassejantsGrocs()+1;
			}
			return numPassejants;
		}
	}
	
	class AnimacioCartes implements Runnable{
		VistaCarta carta;
		Point goal;
		Point origin = null;
		boolean descartada = false;
		
		public AnimacioCartes(VistaCarta carta, Point goal) {
			this.carta = carta;
			this.goal = goal;
		}
		
		@Override
		public void run() {
			System.out.println("Animacio");
			if(animationOn){
				int i = 0;
				while (carta.getLocation().x != goal.x || carta.getLocation().y != goal.y){
					try
					{
						Point current = carta.getLocation();
						int x = current.x;
						int y = current.y;
						//utilitzem la variable equilibra per tal que el passejant abanci de forma uniforma cap al seu objectiu
						int equilibra = 0;
						if(i%3 != 0){
							if(Math.abs(x-goal.x) > Math.abs(y-goal.y)) equilibra = 1;
							else if (Math.abs(y-goal.y) > Math.abs(x-goal.x)) equilibra = 2;
						}
						if(i%3 == 0)Thread.sleep(2);
						if(x < goal.x) {
							++x;
							if(equilibra != 2 && goal.x - x > 4)x+=4;
						}
						else if (x > goal.x){
							--x;
							if (equilibra != 2 && x - goal.x > 4)x-=4;
						}
						if(y < goal.y){
							++y;
							if(equilibra != 1 && goal.y - y > 4)y+=4;
						}
						else if (y > goal.y){
							--y;
							if (equilibra != 1 && y - goal.y > 4)y-=4;
						}
						carta.setLocation(x,y);
						++i;
					}
					catch(Exception e) {}
				}
			}
			if(descartada){
				carta.setEstaBuida(true);
				carta.updateView();
				cartesDescartades.setVisible(true);
				if(origin != null)carta.setLocation(origin);
				else carta.setBounds(cartesDescartades.getBounds());
				cartesDescartades.setCartaEntity(carta.getCartaEntity());
			}
		}
	}
}
