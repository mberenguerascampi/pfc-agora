package upc.tfg.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import upc.tfg.gui.VistaAlertes;
import upc.tfg.gui.VistaPassejant;
import upc.tfg.utils.Constants;
import upc.tfg.utils.DefaultDataBase;
import upc.tfg.utils.ErrorController;
import upc.tfg.utils.ResultatsFinals;

/**
 * Classe singleton que representa una Partida
 * @author Marc
 *
 */
public class Partida {
	private String nom;
	private Date data;
	private int torn;
	private int pas;
	private int idJugadorActual;
	private int idJugadorInici;
	private int passejantsAMoure;
	private Tauler tauler;
	private ArrayList<Jugador> jugadors;
	private Baralla baralla;
	private Baralla baralla2;
	private static Partida instance = null;
	private Carta cartaSeleccionada = null;
	private Map<Integer,Carta> cartesAIntercanviar = new HashMap<Integer,Carta>();
	private int ultimTorn = 12;
	private int[] arrayIA = {};
	
	public Partida() {
		
	}
	
	/**
	 * Constructora de la classe
	 * @param nom Nom de la partida
	 * @param data Data en que es va guardar la partida
	 * @param torn Torn actual de la partida
	 * @param pas Pas actual de la partida
	 */
	public Partida(String nom, Date data, int torn, int pas, int[]arrayIA) {
		instance = this;
		this.nom = nom;
		this.data = data;
		this.torn = torn;
		this.pas = pas;
		this.arrayIA = arrayIA;
		idJugadorInici = 1;
		idJugadorActual = idJugadorInici;
		jugadors = new ArrayList<Jugador>();
		tauler = new Tauler();
		baralla = new Baralla();
		baralla.barrejar();
	}
	
	/**
	 * Constructora de la partida
	 * @param nom Nom de la partida 
	 * @param data Data en que es va crear o guardar la partida
	 * @param torn Torn actual de la partida 
	 * @param pas Pas actual de la partida 
	 * @param jugadors Llistat de jugadors que té la partida 
	 * @param districtes Llistat dels districtes que té la partida
	 * @param idJugadorInici Identificador del jugador que tira primer en el pas actual
	 * @param passejantsAMoure Nombre de passejants que pot moire el jugador en aquest pas 
	 * @param cartesB1 Cartes que hi ha a la baralla1
	 * @param cartesB2 cartes que hi ha a la baralla2 
	 * @param cartesAIntercanviar Cartes que ss'han d'intercanviar en el cas en que estiguem a meitat del pas1
	 */
	public Partida(String nom, String data, int torn, int pas, ArrayList<Jugador> jugadors, 
			Districte[] districtes, int idJugadorInici, int passejantsAMoure,
			ArrayList<Carta> cartesB1, ArrayList<Carta> cartesB2,
			Map<Integer,Carta> cartesAIntercanviar, int[]arrayIA) {
		instance = this;
		this.cartesAIntercanviar = cartesAIntercanviar;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
		this.nom = nom;
		this.data = null;
		this.arrayIA = arrayIA;
//		try {
//			if(data.equals("null"))this.data = null;
//			else this.data = df.parse(data);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		this.torn = torn;
		this.pas = pas;
		this.idJugadorInici = idJugadorInici;
		this.idJugadorActual = 1;
		this.passejantsAMoure = passejantsAMoure;
		this.jugadors = jugadors;
		tauler = new Tauler();
		Tauler.setDistrictes(districtes);
		baralla = new Baralla(cartesB1);
		baralla2 = new Baralla(cartesB2);
	}
	
	/**
	 * Funció que ens permet obtenir la instància de la partida
	 * @return Instància de la partida
	 */
	public static Partida getInstance(){
		if(instance != null)return instance;
		else return new Partida();
	}
	
	//Public Methods
	/**
	 * Funció que crea per primer cop una partida 
	 * @return True en el cas que s'hagi creat correctament
	 */
	public boolean crear(){
		Random rand = new Random(System.currentTimeMillis());
		idJugadorInici = rand.nextInt(4)+1;
		idJugadorActual = idJugadorInici;
		System.out.println("JUGADOR INICI = " + idJugadorInici);
		return true;
	}
	
	/**
	 * Funció que guarda una partida amb un determinat nom
	 * @param nom Nom amb el que volem guardar la partida
	 * @return True si la partida s'ha guardat correctament, false en cas que no s'hagi pogut guardar per algun motiu
	 */
	public boolean guardar(String nom){
		Map<String,String> noms = DefaultDataBase.getNomsPartides();
		if(cartaSeleccionada != null)System.out.println(cartaSeleccionada.getNom() + " -> " + cartaSeleccionada.getValor());
		if(noms.keySet().contains(nom)){
			return false;
		}
		//Només es pot guardar en el torn de l'usuari i si no s'ha seleccionat cap carta en el torn 2
		else if(idJugadorActual != 1 || (pas == 2 && (passejantsAMoure > 0 && 
				(cartaSeleccionada != null && cartaSeleccionada.getValor() > passejantsAMoure)))){
			ErrorController.showError(idJugadorActual, ErrorController.NO_ES_POT_GUARDAR, null, null);
			return false;
		}
		else{
			this.nom = nom;
			Date date = new Date();
			this.data = date;
			DefaultDataBase.guardarPartida(this);
			return true;
		}
	}
	
	/**
	 * Funció que avança cap al següent pas
	 * @return True si s'ha pogut avnçar correctament, false en ca contrari
	 */
	public boolean avancarPas(){
		idJugadorActual = idJugadorInici;
		System.out.println("Avancem pas, Torn jugador " + idJugadorInici);
		++pas;
		if (pas > 4){
			pas = 1;
			avancarTorn();
		}
		else if(pas == 2){
			intercanviarCartes();
		}
		else if(pas == 3){
			passejantsAMoure = 2;
		}
		else if(pas == 4){
			restartPassejantsBloquejats();
			if(!hihaCartesDisponibles()){
				pas = 2;
				avancarTorn();
			}
		}
		return true;
	}
	
	/**
	 * Funció que serveix per robar una carta a un altre jugador
	 * @param jugadorID Identificador del jugador que roba la carta
	 * @param cartaEntity Carta que es vol robar
	 * @return True si es pot robar la carta, false en cas contrari
	 */
	public boolean cartaRobada(int jugadorID, Carta cartaEntity){
		cartesAIntercanviar.put(jugadorID, cartaEntity);
		avancarJugador();
		if(pas == 2){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Funció que retorna la carta que un jugador determinat vol robar
	 * @param jugadorID Identificador del jugador del qual volem obtenir la carta que vol robar
	 * @return Carta a robar 
	 */
	public Carta getCartaARobar(int jugadorID){
		return cartesAIntercanviar.get(jugadorID);
	}
	
	/**
	 * Acció que intercanvia les carte de manera que cada jugador que havia seleccionat una cart per 
	 * robar la obté, però perd la que li han robat a ell
	 */
	private void intercanviarCartes(){
		for(Jugador j:jugadors){
			int id = j.getId();
			int idAnterior = id-1;
			if(idAnterior == 0)idAnterior = 4;
			j.afegirCarta(cartesAIntercanviar.get(id));
			jugadors.get(idAnterior-1).treureCarta(cartesAIntercanviar.get(id));
		}
		cartesAIntercanviar.clear();
	}
	
	/**
	 * Funció que permet avançar de torn
	 * @return True si s'ha pogut realitzar l'acció, false en cas contrari
	 */
	public boolean avancarTorn(){
		++torn;
		if(torn > ultimTorn){
			/*int[] puntuacio = getPuntuacioFinal();
			System.out.println("RESULTATS: (BLAU, VERMELL, VERD, GROC)");
			for (int p:puntuacio){
				System.out.print(p + ", ");
			}*/
			return false;
		}
		return true;
	}
	
	/**
	 * Acció que permet avançar el jugador al que li toca jugar
	 */
	public void avancarJugador(){
		VistaAlertes.getInstance().setVisible(false);
		int idJugadorNouTorn = (idJugadorInici+1);
		if (idJugadorNouTorn == 5) idJugadorNouTorn = 1;
		if(idJugadorActual == idJugadorNouTorn){
			avancarPas();
		}
		else{
			if(cartaSeleccionada != null)cartaSeleccionada.girar();
			--idJugadorActual;
			if(idJugadorActual == 0)idJugadorActual = 4;
			System.out.println("Jugador " + idJugadorActual);
		}
		

		Locale defaultLocale = Locale.getDefault();
		ResourceBundle bundle = ResourceBundle.getBundle("AgoraBundle", defaultLocale);
		
		if(pas == 2 && idJugadorActual == 1){
			if(!potSeleccionarAlgunaCarta(idJugadorActual)){
				VistaAlertes.getInstance().setWarningText(bundle.getString("text_no_pot_seleccionar_carta"));
			}
		}
		if(pas == 3) {
			passejantsAMoure = 2;
			System.out.println("Passejants a moure2 -> " + passejantsAMoure );
			if(idJugadorActual == 1 && torn == ultimTorn){
				VistaAlertes.getInstance().setWarningText(bundle.getString("text_ultims_torns"));
			}
		}
	}
	
	/**
	 * Funció que en sindica si la partida pot finalitzar
	 * @return True si pot acabr, false en cas contrari
	 */
	public boolean finalitzarPartida(){
		if(torn > ultimTorn)return true;
		else return false;
	}
	
	/**
	 * Funció per afegir un jugaddor a la partida
	 * @param jugador Jugador que es vol afegir
	 * @return Boolean que ens indica si el jugador s'ha afegit correctament
	 */
	public boolean afegirJugador(Jugador jugador){
		jugadors.add(jugador);
		System.out.println("COLORRRRRRRRR "+jugador.getColor());
		return true;
	}
	
	/**
	 * Acció que divideix la baralla principal de la partida en dues d'una mida semblant
	 */
	public void divideixBaralla(){
		baralla2 = new Baralla(baralla.getCartes(baralla.getNumCartes()/2));
		baralla.barrejar();
		baralla2.barrejar();
	}
	
	/**
	 * Acció que desbloqueja tots els passejants de tots els districtes de la partida
	 */
	public void restartPassejantsBloquejats(){
		for (Districte d:tauler.getDistrictes()){
			d.restartPassejants();
		}
	}
	
	/**
	 * Funció que ens permet obtenir els resultats, segons les normes, de la partida en el moment actual
	 * @return Puntuació de cada jugador i identificador del jugador guanyador
	 */
	public ResultatsFinals getPuntuacioFinal(){
		return getPuntuacioFinal(tauler.getDistrictes());
	}
	
	/**
	 * Funció que ens permet obtenir els resultats, segons les normes, de la partida amb uns determinats districte
	 * @param districtes Array de districtes que es vol tenir en compte per a realitzar el recompte de la puntuació
	 * @return Puntuació de cada jugador i identificador del jugador guanyador
	 */
	public ResultatsFinals getPuntuacioFinal(Districte[] districtes){
		int puntsBlau, puntsVermell, puntsVerd, puntsGroc, distBlau, distVermell, distVerd, distGroc;
		puntsBlau = puntsVermell = puntsVerd = puntsGroc = 0;
		distBlau = distVermell = distVerd = distGroc = 0;
		for(Districte d:districtes){
			int colorGuanyador = d.getColorGuanyador();
			if(colorGuanyador == Constants.BLAU){
				++distBlau;
				puntsBlau += d.getValor();
			}
			else if(colorGuanyador == Constants.VERMELL){
				++distVermell;
				puntsVermell += d.getValor();
			}
			else if(colorGuanyador == Constants.VERD){
				++distVerd;
				puntsVerd += d.getValor();
			}
			else if(colorGuanyador == Constants.GROC){
				++distGroc;
				puntsGroc += d.getValor();
			}
		}
		int[] resultatsFinals = {puntsBlau, puntsVermell, puntsVerd, puntsGroc};
		int[] totalDistrictes = {distBlau, distVermell, distVerd, distGroc};
		int colorGuanyador = getColorGuanyador(resultatsFinals, totalDistrictes);
		System.out.println("GUANYADOR: " + colorGuanyador);
		return new ResultatsFinals(resultatsFinals[0], resultatsFinals[1], resultatsFinals[2],
				resultatsFinals[3], colorGuanyador);
	}
	
	/**
	 * Funció que a partir de un array de puntuacions per cada color i d'un array indicant el nombre de
	 * districtes en que ha guanyat cada color obté el identificaador del color guanyador
	 * @param resultatsFinals Puntuació per cada color
	 * @param totalDistrictes Per cada color, el nombre de districtes on ha sortit vencedor
	 * @return El color del guanyador
	 */
	private int getColorGuanyador(int[] resultatsFinals, int[] totalDistrictes){
		int colors[] = {Constants.BLAU, Constants.VERMELL, Constants.VERD, Constants.GROC};
		int max = Math.max(Math.max(resultatsFinals[0],  resultatsFinals[1]), Math.max(resultatsFinals[2],  resultatsFinals[3]));
		int colorGuanyador = 0;
		int indexAux = 0;
		for(int i = 0; i < resultatsFinals.length; ++i){
			if(resultatsFinals[i] == max) {
				if(colorGuanyador == 0){
					colorGuanyador = colors[i];
					indexAux = i;
				}
				else {
					if(totalDistrictes[i] > totalDistrictes[indexAux]) {
						colorGuanyador = colors[i];
						indexAux = i;
					}
				}
			}
		}
		return colorGuanyador;
	}
	
	/**
	 * Funcio que decrementa el nombre restant de passejants que pot moure un jugador
	 * @return true si s'ha acabat el torn del jugador
	 */
	public boolean decrementaPassejantsAMoure(){
		VistaAlertes.getInstance().setVisible(false);
		System.out.println("Passejants: "+passejantsAMoure);
		--passejantsAMoure;
		if(passejantsAMoure <= 0){
			avancarJugador();
			return true;
		}
		return false;
	}
	
	/**
	 * Funció per obtenir el text indicatiu de l'estat actual de la partida
	 * @return String amb el corresponent text
	 */
	public String getTextPas(){
		int idAnteriorJugador = idJugadorActual - 1;
		if (idAnteriorJugador == 0) idAnteriorJugador = 4;
		String nomAnteriorJugador = getNomJugador(idAnteriorJugador);
		String text = "<html> PAS " + pas + "<br>";
		switch(pas){
			case 1:
				text = text +  "El jugador " + getNomJugador(idJugadorActual) + " ha de robar <br> una carta al jugador " + nomAnteriorJugador;
				break;
			case 2:
				text = text + "El jugador " + getNomJugador(idJugadorActual) + " ha de seleccionar <br> una carta i moure tants passejants"+
							" al districte <br> corresponent com punts tingui <br> la plaça";
				//text = "<html> PAS " + pas + "<br>El jugador 1 ha de robar <br> una carta el jugador 2 </html>";
				break;
			case 3:
				text = text + "El jugador " + getNomJugador(idJugadorActual) + " ha de moure dos passejants a un districte adjacent ("+ (3-passejantsAMoure) +"/2)";
				break;
			case 4:
				text = text + "El jugador " + getNomJugador(idJugadorActual) + " ha de robar una carta de la pila que vulgui";
				break;
		}
		text = text + " </html>";
		return text;
	}
	
	/**
	 * Funció que ens indica els districtes adjacents a un determinat districte
	 * @param d Districte del qual volem obtenir els districtes adjacents 
	 * @return Llista amb els identificadors dels districtes adjacents
	 */
	public List<Integer> getDistrictesAdjacents(Districte d){
		return Constants.grafDistrictes.get(d.getDistricteID());
	}
	
	//Funcions per consultar si es poden realitzar certes accions
	/**
	 * Funció que indica si es pot moure un passejant d'un determinat color entre dos determinats districtes
	 * @param districte1 nom del districte origen d'on treure el passejant
	 * @param districte2 nom del districte destí on afegir el passejant
	 * @param color Color del passejant a  moure
	 * @return True si es pot moure, false en cas contrari
	 */
	public boolean potMoure(String districte1, String districte2, int color){
		Districte d1 = getDistricte(districte1);
		Districte d2 = getDistricte(districte2);
		return potMoure(d1, d2, color);
	}
	
	/**
	 * Funció que indica si es pot moure un passejant d'un determinat color entre dos determinats districtes
	 * @param districte1 Districte origen d'on treure el passejant
	 * @param districte2 Districte destí on afegir el passejant
	 * @param color Color del passejant a  moure
	 * @return True si es pot moure, false en cas contrari
	 */
	public boolean potMoure(Districte d1, Districte d2, int color){
		if(esUltimTorn() && color != getJugador(idJugadorActual).getColor()) return false;
		if(!potAfegirPassejant(d2, color)) return false;
		if(!potTreurePassejant(d1, color)) return false;
		if(hiHaDistricteAmbMateixNombrePassejants(d1, d2))return false;
		List<Integer> districtesAdjacents = Constants.grafDistrictes.get(d1.getDistricteID());
		for(int i = 0; i < districtesAdjacents.size(); ++i){
			if(districtesAdjacents.get(i) == d2.getDistricteID())return true;
		}
		ErrorController.showError(getIdJugadorActual(),ErrorController.DISTRICTE_NO_ADJACENT,d1,d2);
		return false;
	}
	
	/**
	 * Funció que indica si es produeix un empat en un districte en el màxim nombre de passejants en el cas
	 * en que no tinguem en compte un parell determinat de districtes
	 * @param excepcio1 Districte que no volem tenir en compte
	 * @param excepcio2 Districte que no volem tenir en compte
	 * @return True si es produeix un empat, false en cas contrari
	 */
	public boolean hiHaDistricteAmbMateixNombrePassejants(Districte excepcio1, Districte excepcio2){
		if(passejantsAMoure != 1) return false;
		for (Districte d:tauler.getDistrictes()){
			if (d != excepcio1 && d != excepcio2 && d.teMateixNombreMaxPassejants()){
				ErrorController.showError(getIdJugadorActual(),ErrorController.DISTRICTE_AMB_MATEIX_NOMBRE_PASSEJANTS, d, null);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Funció per conèixer si es produïrà un empat a nombre màxim de passejants en un districte
	 * en cas de afegir un determinat nombre de passejants a un districte
	 * @param numPassejants Nombre de passejants a afegir
	 * @param d Districte on afegir els passejants
	 * @param color Color dels passejants a afegir
	 * @return True si es produeix empat, false en cas contrari
	 */
	public boolean hiHauraDistricteAmbMateixNombrePassejants(int numPassejants, Districte d, int color){
		System.out.println("Puc afegir " + numPassejants + " passejants a " + d.getNom() + "?");
		if(numPassejants == 0)return false;
		return d.tindraMateixNombrePassejants(numPassejants, color);
	}
	
	/**
	 * Funció que indica si un determinat jugador té la possibilitat de escollir alguna carta en el pas2
	 * sense que es produiexi un empat
	 * @param idJugador Identificador del jugador 
	 * @return True si té la possibilitat d'escollir carta, false en cas contrari
	 */
	public boolean potSeleccionarAlgunaCarta(int idJugador){
		Jugador j = getJugador(idJugador);
		for(Carta c:j.getCartes()){
			int numPassejants = Math.min(c.getValor(), j.getTotalPassejants());
			if(!hiHauraDistricteAmbMateixNombrePassejants(numPassejants, c.getDistricte(), j.getColor())) return true;
		}
		return false;
	}
	
	/**
	 * Funció que indica si es pot afegir un passejant d'un cert color a un cert districte
	 * @param districte Districte on afegir el passejant
	 * @param color Color del passejant a afegir
	 * @return True si es pot afegir, false en cas contrari
	 */
	public boolean potAfegirPassejant(Districte districte, int color){
		if(passejantsAMoure == 1){
			if (districte.potAfegirPassejant(color)) return true;
			else{
				ErrorController.showError(getIdJugadorActual(), ErrorController.NO_POT_AFEGIR_PASSEJANT, districte, color);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Funció que indica si es pot treure un passejant d'un cert color d'un cert districte
	 * @param districte Districte d'on treure el passejant
	 * @param color Color del passejant a treure
	 * @return True si es pot treure, false en cas contrari
	 */
	public boolean potTreurePassejant(Districte districte, int color){
		if(passejantsAMoure == 1){
			if(districte.potTreurePassejant(color))return true;
			else{
				ErrorController.showError(getIdJugadorActual(), ErrorController.NO_POT_TREURE_PASSEJANT, districte, color);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Funció que ens indica si estem en l'utlim torn de la partida
	 * @return True en cas que sigui l'últim torn, false en cas contrari
	 */
	public boolean esUltimTorn(){
		return(torn == 12);
	}
	
	/**
	 * Acció que afegeix un certa carta a un determinat jugador
	 * @param jugadorID Identificador del jugador al qual volem afegir la carta
	 * @param carta Carta que volem afegir al jugador
	 */
	public void afegeixCarta(int jugadorID, Carta carta){
		for(Jugador j:jugadors){
			if(j.getId() == jugadorID)j.afegirCarta(carta);
		}
	}
	
	/**
	 * Acció que treu un certa carta d'un determinat jugador
	 * @param jugadorID Identificador del jugador del qual volem treure la carta
	 * @param carta Carta que volem treure del jugador
	 */
	public void treuCarta(int jugadorID, Carta carta){
		for(Jugador j:jugadors){
			if(j.getId() == jugadorID)j.treureCarta(carta);
		}
	}
	
	/**
	 * Funció per obtenir el nom d'un jugador
	 * @param jugadorID Identificador del jugador
	 * @return Nom del jugador
	 */
	public String getNomJugador(int jugadorID){
		for(Jugador j:jugadors){
			if(j.getId() == jugadorID)return j.getNom();
		}
		return "";
	}
	
	/**
	 * Funció que indica si hi ha cartes disponibles en alguna baralla
	 * @return True si hi ha cartes disponibles, false en cas contrari
	 */
	public boolean hihaCartesDisponibles(){
		return (getBaralla().getCartes().size() != 0 || getBaralla2().getCartes().size() != 0);
	}
	
	/**
	 * Funció per obtenir el nom d'un color 
	 * @param colorID Identificador del color
	 * @return Nom del color
	 */
	public String getNomColor(int colorID){
		if (colorID == Constants.BLAU) return VistaPassejant.PASSEJANT_BLAU;
		if (colorID == Constants.VERMELL) return VistaPassejant.PASSEJANT_VERMELL;
		if (colorID == Constants.VERD) return VistaPassejant.PASSEJANT_VERD;
		if (colorID == Constants.GROC) return VistaPassejant.PASSEJANT_GROC;
		return "";
	}

	/**
	 * Funció per desfer una jugada realitzada pel jugador humà
	 * @return El nombre de passejants que s0han de treure
	 */
	public int desfesJugada() {
		if(pas == 3 && idJugadorActual == 1){
			passejantsAMoure = 3;
			return 1;
		}
		if(pas == 2 && idJugadorActual == 1){
			int passejantsATreure = 0;
			if(getCartaSeleccionada() != null)passejantsATreure =getCartaSeleccionada().getValor()-passejantsAMoure;
			passejantsAMoure = 0;
			return passejantsATreure;
		}
		return 0;
	}
	
	public ArrayList<Districte> getDistrictesGuanyats(int idJugador){
		ArrayList<Districte> districtesGuanyats = new ArrayList<Districte>();
		int colorJugador = getJugador(idJugador).getColor();
		for(Districte d:tauler.getDistrictes()){
			int colorGuanyador = d.getColorGuanyador();
			if(colorGuanyador == colorJugador){
				districtesGuanyats.add(d);
			}
		}
		return districtesGuanyats;
	}
	
	
	//Getters & Setters

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getTorn() {
		return torn;
	}

	public void setTorn(int torn) {
		this.torn = torn;
	}

	public int getPas() {
		return pas;
	}

	public void setPas(int pas) {
		this.pas = pas;
	}

	public Tauler getTauler() {
		return tauler;
	}

	public void setTauler(Tauler tauler) {
		this.tauler = tauler;
	}
	
	public Jugador getJugador(int id){
		for(int i = 0; i < jugadors.size(); ++i){
			if(jugadors.get(i).getId() == id){
				return jugadors.get(i);
			}
		}
		return null;
	}
	
	public Districte getDistricte(String nom){
		Districte[] districtes = tauler.getDistrictes();
		for(Districte districte:districtes){
			if(districte.getNom().equalsIgnoreCase(nom)){
				return districte;
			}
		}
		return null;
	}
	
	public Districte getDistricte(int idDistricte){
		Districte[] districtes = tauler.getDistrictes();
		for(Districte districte:districtes){
			if(districte.getDistricteID() == idDistricte){
				return districte;
			}
		}
		return null;
	}

	public Baralla getBaralla() {
		return baralla;
	}

	public void setBaralla(Baralla baralla) {
		this.baralla = baralla;
	}
	
	public Baralla getBaralla2() {
		return baralla2;
	}

	public void setBaralla2(Baralla baralla) {
		this.baralla2 = baralla;
	}

	public int getIdJugadorActual() {
		return idJugadorActual;
	}

	public Carta getCartaSeleccionada() {
		return cartaSeleccionada;
	}

	public void setCartaSeleccionada(Carta cartaSeleccionada) {
		this.cartaSeleccionada = cartaSeleccionada;
	}

	public void setIdJugadorActual(int idJugadorActual) {
		this.idJugadorActual = idJugadorActual;
	}

	public int getPassejantsAMoure() {
		return passejantsAMoure;
	}

	public void setPassejantsAMoure(int passejantsAMoure) {
		this.passejantsAMoure = passejantsAMoure;
	}
	
	public Jugador[] getJugadorsRobot(){
		Jugador[] retJugadors = new Jugador[3];
		int i = 0;
		for(Jugador j:jugadors){
			if(j.getId() != 1){
				retJugadors[i] = j;
				++i;
			}
		}
		return retJugadors;
	}

	public int getIdJugadorInici() {
		return idJugadorInici;
	}

	public void setIdJugadorInici(int idJugadorInici) {
		this.idJugadorInici = idJugadorInici;
	}
	
	public int getColorJugadorActual(){
		for(Jugador j:jugadors){
			if(j.getId() == idJugadorActual){
				return j.getColor();
			}
		}
		return 0;
	}

	public ArrayList<Jugador> getJugadors() {
		return jugadors;
	}

	public int getUltimTorn() {
		return ultimTorn;
	}

	public void setUltimTorn(int ultimTorn) {
		this.ultimTorn = ultimTorn;
	}

	public Map<Integer, Carta> getCartesAIntercanviar() {
		return cartesAIntercanviar;
	}
	
	public Districte[] getDistrictes(){
		return tauler.getDistrictes();
	}
	
	public int getColor(int jugadorID) {
		return jugadors.get(jugadorID-1).getColor();
	}

	public int[] getArrayIA() {
		return arrayIA;
	}

	public void setArrayIA(int[] arrayIA) {
		this.arrayIA = arrayIA;
	}
}
