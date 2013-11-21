package upc.tfg.agora;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import upc.tfg.gui.CustomMenuBar;
import upc.tfg.gui.VistaAbout;
import upc.tfg.gui.VistaCarregarPartida;
import upc.tfg.gui.VistaComençarPartida;
import upc.tfg.gui.VistaFinalPartida;
import upc.tfg.gui.VistaIdioma;
import upc.tfg.gui.VistaInstruccions;
import upc.tfg.gui.VistaMenuPrincipal;
import upc.tfg.gui.VistaPuntuacions;
import upc.tfg.gui.VistaTauler;
import upc.tfg.interfaces.MenuBarListener;
import upc.tfg.interfaces.MenuPrincipalListener;
import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.interfaces.TaulerListener;
import upc.tfg.logic.Carta;
import upc.tfg.logic.ControladorLogic;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;
import upc.tfg.utils.AudioPlayer;
import upc.tfg.utils.Constants;
import upc.tfg.utils.ResultatsFinals;

/**
 * Controlador de tota la capa de presentació
 * @author Marc
 *
 */
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
	
	/**
	 * Constructora de la classe
	 * @throws IOException
	 */
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
	
	/**
	 * Funció que permet obtenir la instància de la classe
	 * @return La instància de la classe Agora
	 * @throws IOException
	 */
	public static Agora getInstance() throws IOException{
		if(instance != null)return instance;
		else return new Agora();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// playPressed();
	}
	
	/**
	 * Acció que inicialitza el frame de l'aplicació
	 */
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
	 
	/**
	 * Acció que inicialitza el menú superior que hi mentre juguem
	 */
	private void initMenu(){
		menubar = new CustomMenuBar(this);
	}
	
	/**
	 * Acció que oculta totes les vistes del frame
	 */
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
	
	/**
	 * Acció que posa el menú superior en la pantalla
	 */
	private void addMenu()
	{
		setJMenuBar(menubar);
	}
	
	/**
	 * Acció que treu el menú superior de la pantalla
	 */
	private void removeMenu()
	{
		setJMenuBar(null);
	}
	
	/**
	 * Acció que inicialitza totes les vistes que hia
	 */
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
	
	/**
	 * Acció que afegeix les cartes d'un determinat jugador
	 * @param jugadorID Identificador del jugador al qual volem afegir les cartes
	 */
	private void afegeixCartesJugador(int jugadorID){
		Carta[] cartes = logic.getCartes(5);
		 for(int i = 0; i < 5; ++i){
			 afegeixCarta(jugadorID, i+1, cartes[i]);
			 logic.afegeixCarta(jugadorID, cartes[i]);
		 }
	}
	
	/**
	 * Acció per visulalitzar les baralles en el tauler
	 * @param barallaDividida Boolean que indica si la baralla està dividida o encar no
	 */
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
	
	public void seleccionatJugadorInici(int idJugador){
		logic.jugadorIniciCanviat(idJugador);
	}
	
	public void infoButtonPressed(){
		
	}
	
	public boolean saveButtonPressed(String nom){
		return logic.guardarPartida(nom);
	}
	
	public void començarPartida(String nomJ1, String nomJ2, String nomJ3, String nomJ4, int[] colors, int[]ias){
		 menuPrincipal.setVisible(false);
		 tauler.reset();
		 tauler.removeAll();
		 logic.comencarPartida(nomJ1, nomJ2, nomJ3, nomJ4, colors,ias);
		 afegeixCartesJugador(1);
		 afegeixCartesJugador(2);
		 afegeixCartesJugador(3);
		 afegeixCartesJugador(4);
		 mostraBaralla(false);
		 //tauler.comencaIntercanviCartes();
		 tauler.afegeixPassejants(Partida.getInstance().getJugador(1).getTotalPassejants(), 1);
//		 tauler.afegeixPassejants(Partida.getInstance().getJugador(2).getTotalPassejants(), 2);
//		 tauler.afegeixPassejants(Partida.getInstance().getJugador(3).getTotalPassejants(), 3);
//		 tauler.afegeixPassejants(Partida.getInstance().getJugador(4).getTotalPassejants(), 4);
		 tauler.setVisible(true);
		 afegeixCartaAgora(Partida.getInstance().getIdJugadorInici());
		 addMenu();
		 logic.getProximMoviment();
	}
	
	//FUNCIONS QUE IMPLEMENTEN EL LISTENER DEL FINAL DE PARTIDA
	public void backButtonPressed(){
		if(backToTauler){
			tauler.tornaAMostrarTauler();
			addMenu();
		}
		else{
			tornarAlMenu();
		}
		backToTauler = false;
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
				logic.getColors(), Partida.getInstance().getArrayIA());
	}
	
	public void mostraPuntuacionsTemporals(){
		ResultatsFinals resultats = Partida.getInstance().getPuntuacioFinal();
		mostraFinalPartida(resultats, false);
	}
	
	public void mostraCalculPuntuacions(){
		amagaVistes();
		vInstruccions.setVisible(true);
		vInstruccions.showCalcularPuntacionsText();
		backToTauler = true;
        removeMenu();
	}
	
	public void mostraAjuda(){
		amagaVistes();
		vInstruccions.setVisible(true);
		backToTauler = true;
        removeMenu();
	}
	
	public void canviarVolum(){
		try {
			AudioPlayer.getInstance().changeState();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//////
	
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
	
	/**
	 * Acció per mostrar la carta Àgora en un deteminat jugador
	 * @param jugadorID Identificador del jugador al que afegir la carta Àgora
	 */
	public void afegeixCartaAgora(int jugadorID){
		tauler.afegeixCartaAgora(jugadorID);
	}
	
	/**
	 * Permet afegir una carta determinada en la posició buida de la mà d'un jugador
	 * @param jugadorID Identificador del jugador al que afegir la carta
	 * @param cartaEntity Carta que volem afegir
	 */
	public void afegeixCartaAPosicioBuida(int jugadorID, Carta cartaEntity)
	{
		if(jugadorID == 1)cartaEntity.setShowing(true);
		tauler.afegeixCartaAPosicioBuida(jugadorID, cartaEntity);
	}
	
	/**
	 * Acció per treure de la mà del jugador humà la carta que ha seleccionat
	 */
	public void treureCartaSeleccionada(){
		tauler.treureCartaSeleccionada();
	}
	
	/**
	 * Actualitza tota la capa de presentació per tal de mostrar les dades de la capa de domini
	 */
	public void updateView(){
		tauler.updateView();
	}
	
	/**
	 * Acció per intercanvia les cartes seleccionades en el pas 1 en el tauler
	 */
	public void intercanviaCartes(){
		tauler.intercanviaCartes();
	}
	
	/**
	 * Acció que selecciona una determinada carta d'un cert jugador i en mou tants passejants com el 
	 * valor de la carta
	 * @param jugadorID Identificador del jugador del qual seleccionem la carta
	 * @param carta Carta que volem seleccionar
	 */
	public void seleccionaCartaiMouPassejants(int jugadorID, Carta carta){
		tauler.seleccionaCartaiMouPassejants(jugadorID, carta);
	}
	
	/**
	 * Acció que selecciona una determinada carta d'un cert jugador per tal de ser robada en el futur
	 * @param jugadorID Identificador del jugador del qual seleccionem la carta
	 * @param cartaEntity Carta que volem robar
	 */
	public void seleccionaCartaPerRobar(int jugadorID, Carta cartaEntity){
		tauler.seleccionaCartaARobar(jugadorID, cartaEntity);
	}
	
	/**
	 * Mostra la pantalla del final de partida
	 * @param resultats Puntuació obtinguda per cada jugador en la partida
	 * @param isFinalPartida Boolean que indica si els reultats són definitius o provisional
	 */
	public void mostraFinalPartida(ResultatsFinals resultats, boolean isFinalPartida){
		removeMenu();
		backToTauler = !isFinalPartida;
		finalPartida.setResultats(resultats,isFinalPartida);
		tauler.setVisible(false);
		menuPrincipal.setVisible(false);
		finalPartida.setVisible(true);
	}
	
	public void mostraJugadorInici(int idJugador){
		tauler.setJugadorInici(idJugador);
	}

	/**
	 * Acció que mou un passejant d'un determinat color entre dos districtes
	 * @param districteA Districte origen d'on treure el passejant
	 * @param districteB Districte destí on afegir el passejant
	 * @param color Color del passejant
	 */
	public void mouPassejant(Districte districteA, Districte districteB,
			int color) {
		tauler.mouPassejant(districteA, districteB, color);
	}

	/**
	 * Funció que indica si s'està realiutzant alguna animació
	 * @return True si s'està realitzant, false en cas contrari
	 */
	public boolean isAnimationOn() {
		return tauler.animationOn;
	}

	/**
	 * Acció que deselecciona una carta prèviament seleccionada pel jugador humà
	 */
	public void deseleccionaCarta() {
		tauler.deseleccionaCarta();
	}

	/**
	 * Acció per mostrar el nombre de passejants d'un determinat jugador
	 * @param jugadorID Identificador del jugador del quen volem mostrar els passejants
	 */
	public void afegeixPassejants(int jugadorID) {
		tauler.updatePassejants(jugadorID);
	}
	
	/**
	 * Acció que carrega una partida en la Capa de Presentació
	 */
	public void loadGame(Partida partida){
		menuPrincipal.setVisible(false);
		 tauler.reset();
		 tauler.removeAll();
		 logic.carregarPartida(partida);
		 for(Jugador j:partida.getJugadors()){
			 afegeixCartesJugador(j.getId(), j.getCartes());
		 }
		 mostraBaralla(true);
		 afegeixCartaAgora(partida.getIdJugadorInici());
		 logic.carregarCartesARobar(partida);
		 tauler.afegeixPassejants(partida.getJugador(1).getTotalPassejants(), 1);
		 //TODO: Descomentar per afegir passejants
//		 tauler.afegeixPassejants(partida.getJugador(2).getTotalPassejants(), 2);
//		 tauler.afegeixPassejants(partida.getJugador(3).getTotalPassejants(), 3);
//		 tauler.afegeixPassejants(partida.getJugador(4).getTotalPassejants(), 4);
		 tauler.setVisible(true);
		 addMenu();
	}
	
	/**
	 * Acció per borrar una partida 
	 * @param nom Nom de la partida que volem borrar
	 */
	public void deleteGame(String nom){
		logic.esborraPartida(nom);
	}
	
	public void mostraSituacioFinalTauler(){
		amagaVistes();
		tauler.showSituacioFinal();
	}
	
	/**
	 * Afegeix a la mà d'un determinat jugador un conjunt de cartes
	 * @param jugadorID Identificador del jugador al que volem afegir les cartes
	 * @param cartes ArrayList de les cartes a afegir
	 */
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

