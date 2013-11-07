package upc.tfg.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import upc.tfg.agora.Agora;
import upc.tfg.gui.VistaAlertes;
import upc.tfg.utils.DefaultDataBase;
import upc.tfg.utils.ErrorController;
import upc.tfg.utils.PassejantsAMoure;
import upc.tfg.utils.ResultatsFinals;

/**
 * Controlador de la capa lògica
 * @author Marc
 *
 */
public class ControladorLogic {
	private Partida partida;
	private Agora agora;
	private ControladorIA controladorIA;
	private PassejantsAMoure lastPAM;
	private int[] colors;
	
	public ControladorLogic() {
		
	}
	
	/**
	 * Constructora de la classe
	 * @param agora Controlador de la capa de presentació
	 */
	public ControladorLogic(Agora agora) {
		this.agora = agora;
	}
	
	/**
	 * Acció que permet començar una partida
	 * @param nomJ1 Nom del jugador amb identificador 1
	 * @param nomJ2 Nom del jugador amb identificador 2
	 * @param nomJ3 Nom del jugador amb identificador 3
	 * @param nomJ4 Nom del jugador amb identificador 4
	 * @param colors Array amb els colors per cada jugador
	 */
	public void comencarPartida(String nomJ1, String nomJ2, String nomJ3, String nomJ4, int[] colors){
		this.colors = colors;
		Date date = new Date();
		partida = new Partida("",date,1,1);
		Jugador j1 = new Jugador(nomJ1,1,colors[0]);
		Jugador j2 = new Jugador(nomJ2,2,colors[1]);
		Jugador j3 = new Jugador(nomJ3,3,colors[2]);
		Jugador j4 = new Jugador(nomJ4,4,colors[3]);
		partida.afegirJugador(j1);
		partida.afegirJugador(j2);
		partida.afegirJugador(j3);
		partida.afegirJugador(j4);
		partida.crear();
		controladorIA = new ControladorIA(this);
		controladorIA.setJugadors(partida.getJugadorsRobot());
	}
	
	/**
	 * Acció que permet carregar una determinada partida
	 * @param partida Partida que es vol carregar
	 */
	public void carregarPartida(Partida partida){
		this.partida = partida;
		ArrayList<Jugador> jugadors = partida.getJugadors();
		int[] colorsACarregar = {jugadors.get(0).getColor(), jugadors.get(1).getColor(), jugadors.get(2).getColor(),
				jugadors.get(3).getColor()};
		this.colors = colorsACarregar;
		controladorIA = new ControladorIA(this);
		controladorIA.setJugadors(partida.getJugadorsRobot());
	}
	
	/**
	 * Acció que prepara el sistema per a realitzar el robatori de cartes del pas1 d'una partida
	 * @param partida 
	 */
	public void carregarCartesARobar(Partida partida){
		Map<Integer,Carta> cartesAIntercanviar = partida.getCartesAIntercanviar();
		for(int key:cartesAIntercanviar.keySet()){
			agora.seleccionaCartaPerRobar(key, cartesAIntercanviar.get(key));
			System.out.println("ROBAR -> jugador: "+ key + ", carta " + cartesAIntercanviar.get(key).getNom());
		}
	}
	
	/**
	 * Funció per guardar la partida actual amb un nom
	 * @param nom Nom amb el que es vol guardar la partida
	 * @return True ssi s'ha guardat correctament, false en cas contari
	 */
	public boolean guardarPartida(String nom){
		return partida.guardar(nom);
	}
	
	/**
	 * Acció per moure un passejant d0un determinat jugador a un determinat districte
	 * @param nomDistricte Nom del districte on volem moure el passejant
	 * @param idJugador Identificador del jugador d'on treiem el passejant
	 */
	public void mouPassejantADistricte(String nomDistricte, int idJugador){
		Jugador j = partida.getJugador(idJugador);
		if(j == null || j.getTotalPassejants() == 0)return;
		Passejant p = j.getUnPassejant();
		if(p == null){
			//TODO: Descartar carta
			return;
		}
		Districte[] districtes = partida.getTauler().getDistrictes();
		for(Districte districte:districtes){
			if(districte.getNom().equalsIgnoreCase(nomDistricte)){
				districte.afegeixPassejant(p);
				if(partida.decrementaPassejantsAMoure()){
					if(idJugador == 1){
						agora.treureCartaSeleccionada();
						agora.updateView();
						treuCarta(idJugador, partida.getCartaSeleccionada());
						getProximMoviment();
					}
				}
				return;
			}
		}
		
	}
	
	/**
	 * Acció que permet moure un passejant entre dos districtes
	 * @param nomDistricteA Nom del districte d'on treiem el passejant
	 * @param nomDistricteB Nom del districte on afegim el passejant
	 * @param color Color del passejant
	 * @param desfent Boolean que indica si la acció l'estem realitzant com a consequüència de desfer una jugada prèvia
	 */
	public void mouPassejantsEntreDistrictes(String nomDistricteA, String nomDistricteB, int color, boolean desfent){
		Districte districteA = partida.getDistricte(nomDistricteA);
		Districte districteB = partida.getDistricte(nomDistricteB);
		
		if(districteA != null && districteB != null){
			while(agora.isAnimationOn())System.out.println("Animacio on");;
			if(partida.getIdJugadorActual() != 1)agora.mouPassejant(districteA, districteB, color);
			else lastPAM = new PassejantsAMoure(color, districteA, districteB);
			while(agora.isAnimationOn());
			Passejant p = districteA.removePassejant(color);
			if(!desfent)p.bloquejar();
			else p.desbloquejar();
			districteB.afegeixPassejant(p);
			
			if(partida.decrementaPassejantsAMoure()){
				getProximMoviment();
			}
			else agora.updateView();
		}
	}
	
	public Carta[] getCartes(int numCartes){
		return partida.getBaralla().getCartes(numCartes);
	}
	
	/**
	 * Funció per indicar-li a la capa de domini que una carta ha sigut descartada
	 * @param carta Carta descartada
	 * @param jugadorID Identificador del jugador que ha descartat la carta
	 */
	public void cartaDescartada(Carta carta, int jugadorID){
		treuCarta(jugadorID, carta);
		partida.avancarJugador();
		getProximMoviment();
	}
	
	/**
	 * Funció per indicar-li a la capa de domini que una carta ha sigut seleccionada
	 * @param carta Carta seleccionada
	 * @param jugadorID Identificador del jugador que ha seleccionat la carta
	 */
	public void cartaSeleccionada(Carta carta, int jugadorID){
		Jugador j = partida.getJugador(jugadorID);
		//Si es produeix empat per afegir els passejants mostrem un error
		int numPassejantsAMoure = Math.min(j.getTotalPassejants(), carta.getValor());
		System.out.println("Passejants a Moure -> " + numPassejantsAMoure);
		if(partida.hiHauraDistricteAmbMateixNombrePassejants(numPassejantsAMoure, carta.getDistricte(), j.getColor())){
			ErrorController.showError(jugadorID, ErrorController.DISTRICTE_CARTA_AMB_MATEIX_NOMBRE_PASSEJANTS, carta.getDistricte(), carta.getValor());
			partida.setPassejantsAMoure(0);
			return;
		}
		else{
			VistaAlertes.getInstance().setVisible(false);
		}
		partida.setPassejantsAMoure(numPassejantsAMoure);
		if(partida.getPassejantsAMoure() == 0){
			if(jugadorID != 1){
				carta.girar();
				agora.seleccionaCartaiMouPassejants(jugadorID, carta);
			}
			treuCarta(jugadorID, carta);
			partida.avancarJugador();
			getProximMoviment();
		}
		else if(jugadorID == 1){
			partida.setCartaSeleccionada(carta);
			System.out.println("-----------------" + partida.getPassejantsAMoure());
		}
		else{
			//Actualitzam la capa de domini
			carta.girar();
			for(int i = 0; i < numPassejantsAMoure; ++i){
				mouPassejantADistricte(carta.getDistricte().getNom(), partida.getIdJugadorActual());
			}
			treuCarta(jugadorID, carta);
			//Actualitzem la capa de presentacio
			agora.seleccionaCartaiMouPassejants(jugadorID, carta);
			getProximMoviment();
		}
	}
	
	/**
	 * Funció per indicar-li a la capa de domini que un cert jugador a robat (seleccionat) una determinada carta
	 * @param jugadorID Identifcador del jugador que ha robat la carta
	 * @param cartaEntity Carta robada
	 */
	public void cartaRobada(int jugadorID, Carta cartaEntity){
		System.out.println("LOGIC: Carta robada del jugador"+jugadorID);
		if(jugadorID != 1){
			agora.seleccionaCartaPerRobar(jugadorID, cartaEntity);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(partida.cartaRobada(jugadorID, cartaEntity)){
			agora.intercanviaCartes();
		}
		agora.updateView();
		getProximMoviment();
	}
	
	/**
	 * Funció per indicar-li a la capa de domini que un cert jugador ha agafat una carta d'una baralla
	 * @param jugadorID Identifcador del jugador que ha agafat la carta
	 * @param barallaID Identificador de la baralla de la qual ha agafat la carta
	 */
	public void cartaAgafadaDeLaBaralla(int jugadorID, int barallaID){
		partida.avancarJugador();
		Carta cartaAgafada = null;
		if (barallaID == -1){
			getProximMoviment();
		}
		else{
			if(barallaID == 1) {
				cartaAgafada = partida.getBaralla().getCartes(1)[0];
			}
			else if (barallaID == 2){
				cartaAgafada = partida.getBaralla2().getCartes(1)[0];
			}
			afegeixCarta(jugadorID, cartaAgafada);
			agora.afegeixCartaAPosicioBuida(jugadorID, cartaAgafada);
			agora.updateView();
			getProximMoviment();
		}
	}
	
	/**
	 * Acció que divideix la baralla principal de la partida en dues baralles de mides similars
	 */
	public void divideixBaralla(){
		partida.divideixBaralla();
	}
	
	/**
	 * Funció per afegir una carta a un determinat jugador
	 * @param jugadorID Identificador del jugador a qui li volem afegir la carta
	 * @param carta Carta que volem afegir
	 */
	public void afegeixCarta(int jugadorID, Carta carta){
		partida.afegeixCarta(jugadorID, carta);
	}
	
	/**
	 * Funció per treure una carta d'un determinat jugador
	 * @param jugadorID Identificador del jugador del qui volem treure la carta
	 * @param carta Carta que volem treure
	 */
	public void treuCarta(int jugadorID, Carta carta){
		partida.treuCarta(jugadorID, carta);
	}
	
	/**
	 * Funció per indicar-li a la capa de domini que obtingui la següent tirada d'un jugador.
	 * En cas que sigui el jugador humà no fa res.
	 */
	public void getProximMoviment(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//Si estem en un jugador diferent a l'1 li demenem a la inteligència artificial el següent moviment
				//Sino esperem a que l'usuari tiri
				System.out.println("Get Proxim moviment");
				agora.updateView();
				if (partida.finalitzarPartida()){
					ResultatsFinals resultats = partida.getPuntuacioFinal();
					agora.mostraFinalPartida(resultats, true);
					return;
				}
				if (partida.getIdJugadorActual() == 1) return;
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				controladorIA.getProximMoviment(partida.getIdJugadorActual(), partida.getPas());
			}
		});
		t.start();
	}
	
	/**
	 * Acció que desfa una jugada feta pel jugador humà sempre hi quan es compleixin les condicions
	 */
	public void desfesJugada(){
		int passejantsATreure = partida.desfesJugada();
		if(passejantsATreure > 0 && Partida.getInstance().getPas() == 3){
			mouPassejantsEntreDistrictes(lastPAM.districteDesti.getNom(), lastPAM.districteOrigen.getNom(), lastPAM.color, true);
		}
		else if (passejantsATreure > 0){
			for(int i = 0; i < passejantsATreure; ++i){
				Jugador jAux = partida.getJugador(partida.getIdJugadorActual());
				retornaPassejantAJugador(jAux, partida.getCartaSeleccionada().getDistricte(), jAux.getColor());
			}
			partida.setCartaSeleccionada(null);
			agora.deseleccionaCarta();
			agora.afegeixPassejants(partida.getIdJugadorActual());
			agora.updateView();
		}
		else{
			ErrorController.showError(partida.getIdJugadorActual(), ErrorController.NO_ES_POT_TIRAR_ENDERRERE, null, null);
		}
	}
	
	/**
	 * Acció que permet que un jugador recuperi un determinat passejant
	 * @param j Jugador al qual volem retornar el passejant 
	 * @param d Districte on el jugador havia afegit el passejant
	 * @param color Color del passejant que s'havia mogut
	 */
	public void retornaPassejantAJugador(Jugador j, Districte d, int color){
		Passejant p = d.removePassejant(color);
		j.afegeixUnPassejant(p);
	}

	/**
	 * Acció que esborra una determinada partida
	 * @param nom Nom de la partida que volem eesborrar
	 */
	public void esborraPartida(String nom) {
		DefaultDataBase.deletePartida(nom);
	}

	/**
	 * Funció que selecciona qualsevol carta del jugador jugadorID, però no mou els passejants perquè el jugador
	 * no té cap carta amb les condicions necessàries per tirar.
	 * @param jugadorID
	 */
	public void seleccionaQualsevolCarta(int jugadorID) {
		partida.setPassejantsAMoure(0);
		Carta carta = Partida.getInstance().getJugador(jugadorID).getCartes().get(0);
		carta.girar();
		treuCarta(jugadorID, carta);
		//Actualitzem la capa de presentacio
		agora.seleccionaCartaiMouPassejants(jugadorID, carta);
		partida.avancarJugador();
		getProximMoviment();
	}
	
	//Getters & Setters

	public int[] getColors() {
		return colors;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}
}
