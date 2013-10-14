package upc.tfg.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import upc.tfg.gui.VistaPassejant;
import upc.tfg.utils.Constants;
import upc.tfg.utils.ErrorController;
import upc.tfg.utils.ResultatsFinals;

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
	private int ultimTorn = 1;
	private ControladorLogic logic;
	
	public Partida() {
		
	}
	
	public Partida(String nom, Date data, int torn, int pas, ControladorLogic logic) {
		instance = this;
		this.nom = nom;
		this.data = data;
		this.torn = torn;
		this.pas = pas;
		this.logic = logic;
		idJugadorInici = 1;
		idJugadorActual = idJugadorInici;
		jugadors = new ArrayList<Jugador>();
		tauler = new Tauler();
		baralla = new Baralla();
		baralla.barrejar();
	}
	
	public static Partida getInstance(){
		if(instance != null)return instance;
		else return new Partida();
	}
	
	//Public Methods
	public boolean crear(){
		return true;
	}
	
	public boolean carregar(){
		return true;
	}
	
	public boolean guardar(){
		return true;
	}
	
	public boolean esborrar(){
		return true;
	}
	
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
	
	public void avancarJugador(){
		if(idJugadorActual == 2){
			avancarPas();
		}
		else{
			if(cartaSeleccionada != null)cartaSeleccionada.girar();
			--idJugadorActual;
			if(idJugadorActual == 0)idJugadorActual = 4;
			System.out.println("Jugador " + idJugadorActual);
		}
		
		if(pas == 3) passejantsAMoure = 2;
	}
	
	public boolean finalitzarPartida(){
		if(torn > ultimTorn)return true;
		else return false;
	}
	
	public boolean afegirJugador(Jugador jugador){
		jugadors.add(jugador);
		return true;
	}
	
	public void divideixBaralla(){
		baralla2 = new Baralla(baralla.getCartes(baralla.getNumCartes()/2));
		baralla.barrejar();
		baralla2.barrejar();
	}
	
	public void restartPassejantsBloquejats(){
		for (Districte d:tauler.getDistrictes()){
			d.restartPassejants();
		}
	}
	
	public ResultatsFinals getPuntuacioFinal(){
		int puntsBlau, puntsVermell, puntsVerd, puntsGroc, distBlau, distVermell, distVerd, distGroc;
		puntsBlau = puntsVermell = puntsVerd = puntsGroc = 0;
		distBlau = distVermell = distVerd = distGroc = 0;
		for(Districte d:tauler.getDistrictes()){
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
	
	/**
	 * Funcio que decrementa el nombre restant de passejants que pot moure un jugador
	 * @return true si s'ha acabat el torn del jugador
	 */
	public boolean decrementaPassejantsAMoure(){
		System.out.println("Passejants: "+passejantsAMoure);
		--passejantsAMoure;
		if(passejantsAMoure <= 0){
			avancarJugador();
			return true;
		}
		return false;
	}
	
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
	
	public List<Integer> getDistrictesAdjacents(Districte d){
		return Constants.grafDistrictes.get(d.getDistricteID());
	}
	
	//Funcions per consultar si es poden realitzar certes accions
	
	public boolean potMoure(String districte1, String districte2, int color){
		Districte d1 = getDistricte(districte1);
		Districte d2 = getDistricte(districte2);
		return potMoure(d1, d2, color);
	}
	
	public boolean potMoure(Districte d1, Districte d2, int color){
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
	
	//Getters i setters

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
	
	public boolean esUltimTorn(){
		return(torn == 12);
	}
	
	public void afegeixCarta(int jugadorID, Carta carta){
		for(Jugador j:jugadors){
			if(j.getId() == jugadorID)j.afegirCarta(carta);
		}
	}
	
	public void treuCarta(int jugadorID, Carta carta){
		for(Jugador j:jugadors){
			if(j.getId() == jugadorID)j.treureCarta(carta);
		}
	}
	
	public String getNomJugador(int jugadorID){
		for(Jugador j:jugadors){
			if(j.getId() == jugadorID)return j.getNom();
		}
		return "";
	}
	
	public boolean hihaCartesDisponibles(){
		return (getBaralla().getCartes().size() != 0 || getBaralla2().getCartes().size() != 0);
	}
	
	public String getNomColor(int colorID){
		if (colorID == Constants.BLAU) return VistaPassejant.PASSEJANT_BLAU;
		if (colorID == Constants.VERMELL) return VistaPassejant.PASSEJANT_VERMELL;
		if (colorID == Constants.VERD) return VistaPassejant.PASSEJANT_VERD;
		if (colorID == Constants.GROC) return VistaPassejant.PASSEJANT_GROC;
		return "";
	}

	public int getColor(int jugadorID) {
		switch(jugadorID){
			case 1: return Constants.BLAU;
			case 2: return Constants.VERMELL;
			case 3: return Constants.VERD;
			case 4: return Constants.GROC;
		}
		return 0;
	}

	public boolean desfesJugada() {
		if(pas == 3 && idJugadorActual == 1){
			passejantsAMoure = 3;
			System.out.println("DESFENT JUGADA!");
			return true;
		}
		return false;
	}
}
