package upc.tfg.agora;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import upc.tfg.gui.VistaBaralla;
import upc.tfg.gui.VistaMenuPrincipal;
import upc.tfg.gui.VistaTauler;
import upc.tfg.interfaces.MenuPrincipalListener;
import upc.tfg.interfaces.TaulerListener;
import upc.tfg.logic.Carta;
import upc.tfg.logic.ControladorLogic;
import upc.tfg.logic.Partida;
import upc.tfg.logic.Tauler;
import upc.tfg.utils.Constants;
import upc.tfg.utils.ImageToNumberArray;

public class Agora extends JFrame implements MenuPrincipalListener, TaulerListener, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9046011155156686371L;
	Container contentPane;
	CardLayout cardLayout;
	VistaMenuPrincipal menuPrincipal;
	VistaTauler tauler;
	private JMenuBar menubar;
	private static Agora instance = null;
	private ControladorLogic logic;
	public boolean finish = false;
	
	public Agora() throws IOException {
		contentPane = getContentPane();
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);
		
		initFrame();
		initViews();
		initMenu();
		
		Agora.instance = this;
		logic = new ControladorLogic(this);
		logic.comencarPartida();
		
		//new ImageToNumberArray();
		//pack();
	}
	
	public static Agora getInstance() throws IOException{
		if(instance != null)return instance;
		else return new Agora();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// playPressed();
	}
	
	private void initFrame(){
		setTitle("Àgora Barcelona");
		setVisible(true);
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
		setSize(768, 1024);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setBackground(Color.BLACK);
		
		//Posem una icona a la aplicació
		URL urlIconImg = getClass().getResource(Constants.fileUrl+"icona.png");
		Image icon =  new ImageIcon(urlIconImg).getImage();
		setIconImage(icon);
	}
	
	private void initMenu(){
		menubar = new JMenuBar();
        ImageIcon icon = new ImageIcon(getClass().getResource(Constants.fileUrl+"exit.png"));

        JMenu file = new JMenu("File");
        file.setFont(Constants.fontGillSansBold);
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit", icon);
        eMenuItem.setBackground(Constants.colorGreen);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        file.add(eMenuItem);

        menubar.add(file);
        menubar.setBackground(Constants.colorGreen);
        menubar.setFont(Constants.fontGillSansBold);
        
	}
	
	private void addMenu()
	{
		setJMenuBar(menubar);
	}
	
	private void removeMenu()
	{
		setJMenuBar(null);
	}
	
	private void initViews(){
		menuPrincipal = new VistaMenuPrincipal(this);
		menuPrincipal.setVisible(true);
		tauler = new VistaTauler(this);
		tauler.setVisible(false);
		
		contentPane.add(menuPrincipal);
		contentPane.add(tauler);
	}

	@Override
	public void playPressed() {
		 menuPrincipal.setVisible(false);
		 afegeixCartesJugador(1);
		 afegeixCartesJugador(2);
		 afegeixCartesJugador(3);
		 afegeixCartesJugador(4);
		 mostraBaralla();
		 //tauler.comencaIntercanviCartes();
		 tauler.afegeixPassejants(7, 0);
		 tauler.setVisible(true);
		 addMenu();
		 //cardLayout.next(contentPane);
	}
	
	private void afegeixCartesJugador(int jugadorID){
		Carta[] cartes = logic.getCartes(5);
		 for(int i = 0; i < 5; ++i){
			 afegeixCarta(jugadorID, i+1, cartes[i]);
		 }
	}
	
	private void mostraBaralla(){
		logic.divideixBaralla();
		tauler.afegeixBaralles();
	}
	
	//FUNCIONS QUE IMPLEMENTEN EL LITENER DEL TAULER
	/**
	 * Funció cridada quan el jugador prem una carta en el tauler
	 */
	public void cartaSeleccionada(int jugadorID, Carta cartaEntity)
	{
		System.out.println("Carta seleccionada ->" + cartaEntity.getValor());
		logic.cartaSeleccionada(cartaEntity);
	}
	
	
	public void passejantMogut(int idJugador, String nomDistricte){
		logic.mouPassejantADistricte(nomDistricte, idJugador);
	}
	
	public void passejantMogutEntreDistrictes(String districtName1, String districtName2, int color){
		logic.mouPassejantsEntreDistrictes(districtName1, districtName2, color);
	}
	
	public void nextPlayer(){
		Partida.getInstance().avancarJugador();
		updateView();
	}
	
	//FUNCIONS PÜBLIQUES PER MODIFiCAR LA CAPA DE PRESENTACIÓ
	/**
	 * Mostra en la pantalla del tauler la carta amb identificador cartaID en les cartes del jugador amb 
	 * identificador jugadorID
	 * @param jugadorID identificador del jugador
	 * @param cartaID identificador de la carta
	 * @param posicio posició que ocupa la carta dintre del conjunt de cartes del jugador
	 */
	public void afegeixCarta(int jugadorID, int posicio, Carta cartaEntity)
	{
		if(jugadorID == 1)cartaEntity.setShowing(true);
		tauler.afegeixCarta(jugadorID, posicio, cartaEntity);
	}
	
	public void treureCartaSeleccionada(){
		tauler.treureCartaSeleccionada();
	}
	
	public void updateView(){
		tauler.updateView();
	}
}

