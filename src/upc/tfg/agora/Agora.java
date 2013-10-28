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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import upc.tfg.gui.CustomMenuBar;
import upc.tfg.gui.VistaAbout;
import upc.tfg.gui.VistaBaralla;
import upc.tfg.gui.VistaCarregarPartida;
import upc.tfg.gui.VistaComençarPartida;
import upc.tfg.gui.VistaFinalPartida;
import upc.tfg.gui.VistaIdioma;
import upc.tfg.gui.VistaInstruccions;
import upc.tfg.gui.VistaMenuPrincipal;
import upc.tfg.gui.VistaPopUp;
import upc.tfg.gui.VistaPuntuacions;
import upc.tfg.gui.VistaTauler;
import upc.tfg.interfaces.MenuBarListener;
import upc.tfg.interfaces.MenuPrincipalListener;
import upc.tfg.interfaces.PopupButtonsListener;
import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.interfaces.TaulerListener;
import upc.tfg.logic.Carta;
import upc.tfg.logic.ControladorLogic;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;
import upc.tfg.logic.Tauler;
import upc.tfg.utils.Constants;
import upc.tfg.utils.DefaultDataBase;
import upc.tfg.utils.ImageToNumberArray;
import upc.tfg.utils.ResultatsFinals;

public class Agora extends JFrame implements MenuPrincipalListener, TaulerListener,
										VistaAmbBotoTornarListener, ActionListener, MenuBarListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9046011155156686371L;
	Container contentPane;
	CardLayout cardLayout;
	VistaMenuPrincipal menuPrincipal;
	VistaTauler tauler;
	VistaFinalPartida finalPartida;
	VistaIdioma vIdioma;
	VistaAbout vAbout;
	VistaPuntuacions vPuntuacions;
	VistaComençarPartida vComençarPartida;
	VistaCarregarPartida vCarregarPartida;
	VistaInstruccions vInstruccions;
	private CustomMenuBar menubar;
	private static Agora instance = null;
	private ControladorLogic logic;
	private boolean backToTauler = false;
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
		menubar = new CustomMenuBar(this);
	}
	
	//FUNCIONS QUE IMPLEMENTEN EL LISTENER DEL MENU SUPERIOR
	
	public void desferJugada(){
		logic.desfesJugada();
	}
	
	public void tornarAlMenu(){
		amagaVistes();
		menuPrincipal.updateView();
        menuPrincipal.setVisible(true);
        removeMenu();
	}
	
	public void acabarPartidaiTornarAlMenu(){
		if(tauler.showGoMenuPopup()){
			tornarAlMenu();
		}
	}
	
	public void exitApplication(){
		//System.exit(0);
		tauler.showPopupConfirmar();
	}
	
	public void mostraPopupGuardar(){
		tauler.showPopupGuardar();
	}
	
	public void carregarPartida(){
		amagaVistes();
		vCarregarPartida.setVisible(true);
		backToTauler = true;
        removeMenu();
	}
	
	public void crearPartida(){
		començarPartida(Partida.getInstance().getNomJugador(1), Partida.getInstance().getNomJugador(2), 
				Partida.getInstance().getNomJugador(3), Partida.getInstance().getNomJugador(4), 
				logic.getColors());
	}
	
	public void mostraPuntuacionsTemporals(){
		ResultatsFinals resultats = Partida.getInstance().getPuntuacioFinal();
		mostraFinalPartida(resultats, false);
	}
	//////
	
	private void amagaVistes(){
		menuPrincipal.setVisible(false);
		tauler.setVisible(false);
		finalPartida.setVisible(false);
		vAbout.setVisible(false);
		vCarregarPartida.setVisible(false);
		vIdioma.setVisible(false);
		vInstruccions.setVisible(false);
		vPuntuacions.setVisible(false);
		vComençarPartida.setVisible(false);
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
		finalPartida = new VistaFinalPartida(this);
		finalPartida.setVisible(false);
		vIdioma = new VistaIdioma(this);
		vIdioma.setVisible(false);
		vAbout = new VistaAbout(this);
		vAbout.setVisible(false);
		vPuntuacions = new VistaPuntuacions(this);
		vPuntuacions.setVisible(false);
		vComençarPartida = new VistaComençarPartida(this);
		vComençarPartida.setVisible(false);
		vCarregarPartida = new VistaCarregarPartida(this);
		vCarregarPartida.setVisible(false);
		vInstruccions = new VistaInstruccions(this);
		vInstruccions.setVisible(false);
		
		contentPane.add(menuPrincipal);
		contentPane.add(tauler);
		contentPane.add(finalPartida);
		contentPane.add(vIdioma);
		contentPane.add(vAbout);
		contentPane.add(vPuntuacions);
		contentPane.add(vComençarPartida);
		contentPane.add(vCarregarPartida);
		contentPane.add(vInstruccions);
	}
	
	private void afegeixCartesJugador(int jugadorID){
		Carta[] cartes = logic.getCartes(5);
		 for(int i = 0; i < 5; ++i){
			 afegeixCarta(jugadorID, i+1, cartes[i]);
			 logic.afegeixCarta(jugadorID, cartes[i]);
		 }
	}
	
	private void mostraBaralla(boolean barallaDividida){
		if(!barallaDividida)logic.divideixBaralla();
		tauler.afegeixBaralles();
	}
	
	//FUNCIONS QUE IMPLEMENTEN EL LISTENER DEL MENÚ PRINCIPAL
	@Override
	public void playPressed() {
		System.out.println("PLAY PRESSED");
		 menuPrincipal.setVisible(false);
		 vComençarPartida.setVisible(true);
	}
	
	@Override
	public void loadButtonPressed() {
		menuPrincipal.setVisible(false);
		vCarregarPartida.setVisible(true);
	}

	@Override
	public void highscoresButtonPressed() {
		menuPrincipal.setVisible(false);
		vPuntuacions.setVisible(true);
	}

	@Override
	public void languageButtonPressed() {
		menuPrincipal.setVisible(false);
		vIdioma.setVisible(true);
	}

	@Override
	public void instructionsButtonPressed() {
		menuPrincipal.setVisible(false);
		vInstruccions.setVisible(true);
	}

	@Override
	public void aboutButtonPressed() {
		menuPrincipal.setVisible(false);
		vAbout.setVisible(true);
	}
	
	//FUNCIONS QUE IMPLEMENTEN EL LISTENER DEL TAULER
	/**
	 * Funció cridada quan el jugador prem una carta en el tauler
	 */
	public void cartaSeleccionada(int jugadorID, Carta cartaEntity)
	{
		logic.cartaSeleccionada(cartaEntity, jugadorID);
	}
	
	public void cartaDescartada(Carta carta, int jugadorID){
		logic.cartaDescartada(carta, jugadorID);
	}
	
	public void cartaRobada(int jugadorID, Carta cartaEntity)
	{
		logic.cartaRobada(jugadorID, cartaEntity);
	}
	
	public void passejantMogut(int idJugador, String nomDistricte){
		logic.mouPassejantADistricte(nomDistricte, idJugador);
	}
	
	public void passejantMogutEntreDistrictes(String districtName1, String districtName2, int color){
		logic.mouPassejantsEntreDistrictes(districtName1, districtName2, color, false);
	}
	
	public void nextPlayer(){
		Partida.getInstance().avancarJugador();
		updateView();
	}
	
	public void cartaAgafada(int jugadorID, int barallaID){
		logic.cartaAgafadaDeLaBaralla(jugadorID, barallaID);
	}
	
	public void infoButtonPressed(){
		
	}
	
	public boolean saveButtonPressed(String nom){
		return logic.guardarPartida(nom);
	}
	
	public void començarPartida(String nomJ1, String nomJ2, String nomJ3, String nomJ4, int[] colors){
		 menuPrincipal.setVisible(false);
		 tauler.reset();
		 tauler.removeAll();
		 logic.comencarPartida(nomJ1, nomJ2, nomJ3, nomJ4, colors);
		 afegeixCartesJugador(1);
		 afegeixCartesJugador(2);
		 afegeixCartesJugador(3);
		 afegeixCartesJugador(4);
		 mostraBaralla(false);
		 //tauler.comencaIntercanviCartes();
		 tauler.afegeixPassejants(Partida.getInstance().getJugador(1).getTotalPassejants(), 1);
		 tauler.setVisible(true);
		 addMenu();
		 logic.getProximMoviment();
	}
	
	//FUNCIONS QUE IMPLEMENTEN EL LISTENER DEL FINAL DE PARTIDA
	public void backButtonPressed(){
		if(backToTauler){
			tauler.setVisible(true);
			addMenu();
		}
		else{
			tornarAlMenu();
		}
		backToTauler = false;
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
		if(jugadorID == 1 && cartaEntity != null)cartaEntity.setShowing(true);
		tauler.afegeixCarta(jugadorID, posicio, cartaEntity);
	}
	
	public void afegeixCartaAPosicioBuida(int jugadorID, Carta cartaEntity)
	{
		if(jugadorID == 1)cartaEntity.setShowing(true);
		tauler.afegeixCartaAPosicioBuida(jugadorID, cartaEntity);
	}
	
	public void treureCartaSeleccionada(){
		tauler.treureCartaSeleccionada();
	}
	
	public void updateView(){
		tauler.updateView();
	}
	
	public void intercanviaCartes(){
		tauler.intercanviaCartes();
	}
	
	public void seleccionaCartaiMouPassejants(int jugadorID, Carta carta){
		tauler.seleccionaCartaiMouPassejants(jugadorID, carta);
	}
	
	public void seleccionaCartaPerRobar(int jugadorID, Carta cartaEntity){
		tauler.seleccionaCartaARobar(jugadorID, cartaEntity);
	}
	
	public void mostraFinalPartida(ResultatsFinals resultats, boolean isFinalPartida){
		removeMenu();
		backToTauler = !isFinalPartida;
		finalPartida.setResultats(resultats,isFinalPartida);
		tauler.setVisible(false);
		menuPrincipal.setVisible(false);
		finalPartida.setVisible(true);
	}

	public void mouPassejant(Districte districteA, Districte districteB,
			int color) {
		tauler.mouPassejant(districteA, districteB, color);
	}

	public boolean isAnimationOn() {
		return tauler.animationOn;
	}

	public void deseleccionaCarta() {
		tauler.deseleccionaCarta();
	}

	public void afegeixPassejants(int jugadorID) {
		tauler.updatePassejants(jugadorID);
	}
	
	public void loadGame(Partida partida){
		menuPrincipal.setVisible(false);
		 tauler.reset();
		 tauler.removeAll();
		 logic.carregarPartida(partida);
		 for(Jugador j:partida.getJugadors()){
			 afegeixCartesJugador(j.getId(), j.getCartes());
		 }
		 mostraBaralla(true);
		 tauler.afegeixPassejants(partida.getJugador(1).getTotalPassejants(), 1);
		 tauler.setVisible(true);
		 addMenu();
	}
	
	public void deleteGame(String nom){
		logic.esborraPartida(nom);
	}
	
	private void afegeixCartesJugador(int jugadorID, ArrayList<Carta>cartes){
		 //Afegim les cartes que tenia el jugador
		 for(int i = 0; i < cartes.size(); ++i){
			 afegeixCarta(jugadorID, i+1, cartes.get(i));
		 }
		 //Si el jugador no té les cinc cartes la posem buida
		 for(int i = cartes.size(); i < 5; ++i){
			 afegeixCarta(jugadorID, i+1, null);
		 }
	}

}

