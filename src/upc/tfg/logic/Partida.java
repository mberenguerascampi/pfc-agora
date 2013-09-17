package upc.tfg.logic;

import java.sql.Date;
import java.util.ArrayList;

public class Partida {
	private String nom;
	private Date data;
	private int torn;
	private int pas;
	private int idJugadorActual;
	private Tauler tauler;
	private ArrayList<Jugador> jugadors;
	private static Partida instance = null;
	
	public Partida() {
		
	}
	
	public Partida(String nom, Date data, int torn, int pas) {
		this.nom = nom;
		this.data = data;
		this.torn = torn;
		this.pas = pas;
		jugadors = new ArrayList<Jugador>();
		tauler = new Tauler();
		instance = this;
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
	
}
