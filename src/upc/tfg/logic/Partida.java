package upc.tfg.logic;

import java.sql.Date;
import java.util.ArrayList;

public class Partida {
	private String nom;
	private Date data;
	private int torn;
	private int pas;
	private int idJugadorActual;
	private int passejantsAMoure;
	private Tauler tauler;
	private ArrayList<Jugador> jugadors;
	private Baralla baralla;
	private Baralla baralla2;
	private static Partida instance = null;
	
	public Partida() {
		
	}
	
	public Partida(String nom, Date data, int torn, int pas) {
		instance = this;
		this.nom = nom;
		this.data = data;
		this.torn = torn;
		this.pas = pas;
		idJugadorActual = 1;
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
		++pas;
		return true;
	}
	
	public boolean avancarTorn(){
		++torn;
		return true;
	}
	
	public void avancarJugador(){
		if(idJugadorActual == 4){
			avancarPas();
		}
		else{
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

	public void setIdJugadorActual(int idJugadorActual) {
		this.idJugadorActual = idJugadorActual;
	}

	public int getPassejantsAMoure() {
		return passejantsAMoure;
	}

	public void setPassejantsAMoure(int passejantsAMoure) {
		this.passejantsAMoure = passejantsAMoure;
	}
	
	public void decrementaPassejantsAMoure(){
		System.out.println("Passejants: "+passejantsAMoure);
		--passejantsAMoure;
		if(passejantsAMoure == 0)avancarJugador();
	}
}
