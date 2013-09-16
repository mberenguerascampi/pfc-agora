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

import upc.tfg.gui.VistaMenuPrincipal;
import upc.tfg.gui.VistaTauler;
import upc.tfg.interfaces.MenuPrincipalListener;
import upc.tfg.interfaces.TaulerListener;
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
	
	public Agora() throws IOException {
		contentPane = getContentPane();
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);
		
		initFrame();
		initViews();
		initMenu();
		
		Agora.instance = this;
		//new ImageToNumberArray();
		//pack();
	}
	
	public Agora getInstance() throws IOException{
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
		System.out.println("You clicked the button");
		 menuPrincipal.setVisible(false);
		 //TODO: Dades hardcoded 
		 afegeixCarta(4, 1, 1);
		 afegeixCarta(4, 2, 2);
		 afegeixCarta(4, 3, 3);
		 afegeixCarta(4, 4, 4);
		 afegeixCarta(4, 5, 5);
		 afegeixCarta(1, 1, 1);
		 afegeixCarta(1, 2, 2);
		 afegeixCarta(1, 3, 3);
		 afegeixCarta(1, 4, 4);
		 afegeixCarta(1, 5, 5);
		 afegeixCarta(3, 1, 1);
		 afegeixCarta(3, 2, 2);
		 afegeixCarta(3, 3, 3);
		 afegeixCarta(3, 4, 4);
		 afegeixCarta(3, 5, 5);
		 afegeixCarta(2, 1, 1);
		 afegeixCarta(2, 2, 2);
		 afegeixCarta(2, 3, 3);
		 afegeixCarta(2, 4, 4);
		 afegeixCarta(2, 5, 5);
		 tauler.afegeixPassejants(7, 0);
		 tauler.setVisible(true);
		 addMenu();
		 //cardLayout.next(contentPane);
	}
	
	//FUNCIONS QUE IMPLEMENTEN EL LITENER DEL TAULER
	/**
	 * Funció cridada quan el jugador prem una carta en el tauler
	 */
	public void cartaSeleccionada(int jugadorID, int cartaID)
	{
		System.out.println("Carta seleccionada");
	}
	
	//FUNCIONS PÜBLIQUES PER MODIFiCAR LA CAPA DE PRESENTACIÓ
	/**
	 * Mostra en la pantalla del tauler la carta amb identificador cartaID en les cartes del jugador amb 
	 * identificador jugadorID
	 * @param jugadorID identificador del jugador
	 * @param cartaID identificador de la carta
	 * @param posicio posició que ocupa la carta dintre del conjunt de cartes del jugador
	 */
	public void afegeixCarta(int jugadorID, int posicio, int cartaID)
	{
		tauler.afegeixCarta(jugadorID, posicio, cartaID);
	}
}

