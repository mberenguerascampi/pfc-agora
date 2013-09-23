package upc.tfg.logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import upc.tfg.utils.Constants;

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
	
	public Partida() {
		
	}
	
	public Partida(String nom, Date data, int torn, int pas) {
		instance = this;
		this.nom = nom;
		this.data = data;
		this.torn = torn;
		this.pas = pas;
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
		++pas;
		if (pas > 4){
			pas = 1;
			avancarTorn();
		}
		else if(pas == 3){
			passejantsAMoure = 2;
		}
		return true;
	}
	
	public boolean avancarTorn(){
		++torn;
		if(torn > 12){
			return false;
		}
		return true;
	}
	
	public void avancarJugador(){
		if(idJugadorActual == 4){
			avancarPas();
		}
		else{
			if(cartaSeleccionada != null)cartaSeleccionada.girar();
			++idJugadorActual;
		}
	}
	
	public boolean finalitzarPartida(){
		return true;
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
	
	public boolean potMoure(String districte1, String districte2, int color){
		Districte d1 = getDistricte(districte1);
		Districte d2 = getDistricte(districte2);
		if(!potAfegirPassejant(d2, color)) return false;
		if(!potTreurePassejant(d1, color)) return false;
		if(hiHaDistricteAmbMateixNombrePassejants(d1, d2))return false;
		List<Integer> districtesAdjacents = Constants.grafDistrictes.get(d1.getDistricteID());
		for(int i = 0; i < districtesAdjacents.size(); ++i){
			if(districtesAdjacents.get(i) == d2.getDistricteID())return true;
		}
		return false;
	}
	
	public boolean hiHaDistricteAmbMateixNombrePassejants(Districte excepcio1, Districte excepcio2){
		if(passejantsAMoure != 1) return false;
		for (Districte d:tauler.getDistrictes()){
			if (d != excepcio1 && d != excepcio2 && d.teMateixNombreMaxPassejants())return true;
		}
		return false;
	}
	
	public boolean potAfegirPassejant(Districte districte, int color){
		if(passejantsAMoure == 1){
			return districte.potAfegirPassejant(color);
		}
		return true;
	}
	
	public boolean potTreurePassejant(Districte districte, int color){
		if(passejantsAMoure == 1){
			return districte.potTreurePassejant(color);
		}
		return true;
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
	
	/**
	 * Funcio que decrementa el nombre restant de passejants que pot moure un jugador
	 * @return true si s'ha acabat el torn del jugador
	 */
	public boolean decrementaPassejantsAMoure(){
		System.out.println("Passejants: "+passejantsAMoure);
		--passejantsAMoure;
		if(passejantsAMoure == 0){
			avancarJugador();
			return true;
		}
		return false;
	}
	
	public String getTextPas(){
		String text = "<html> PAS " + pas + "<br>";
		switch(pas){
			case 1:
				text = text +  "El jugador " + idJugadorActual + " ha de robar <br> una carta al jugador " + (idJugadorActual-1);
				break;
			case 2:
				text = text + "El jugador " + idJugadorActual + " ha de seleccionar <br> una carta i moure tants passejants"+
							" al districte <br> corresponent com punts tingui <br> la plaça";
				//text = "<html> PAS " + pas + "<br>El jugador 1 ha de robar <br> una carta el jugador 2 </html>";
				break;
			case 3:
				text = text + "El jugador " + idJugadorActual + " ha de moure dos passejants a un districte adjacent";
				break;
			case 4:
				text = text + "El jugador " + idJugadorActual + " ha de robar una carta de la pila que vulgui";
				break;
		}
		text = text + " </html>";
		return text;
	}

	public int getIdJugadorInici() {
		return idJugadorInici;
	}

	public void setIdJugadorInici(int idJugadorInici) {
		this.idJugadorInici = idJugadorInici;
	}
}
