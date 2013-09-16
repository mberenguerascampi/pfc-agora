package upc.tfg.logic;

import java.sql.Date;

public class Partida {
	private String nom;
	private Date data;
	private int torn;
	private int pas;
	
	public Partida() {
		
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
		return true;
	}
	
	public boolean avancarTorn(){
		return true;
	}
	
	public boolean finalitzarPartida(){
		return true;
	}
	
	public boolean afegirJugador(){
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
	
	
}
