package upc.tfg.logic;

import java.util.ArrayList;

/**
 * Classe que representa l'entitat d'un jugador
 * @author Marc
 *
 */
public class Jugador {
	private String nom;
	private int id;
	private int color;
	private ArrayList<Passejant> passejants;
	private ArrayList<Carta> cartes;
	private int tipusJugador;
	int totalPassejants;
	
	/**
	 * Constructora de la classe
	 * @param nom Nom del jugador
	 * @param id Identificador del jugador
	 * @param color Color del jugador
	 */
	public Jugador(String nom, int id, int color, int tipus) {
		this.nom = nom;
		this.id = id;
		this.color = color;
		this.tipusJugador = tipus;
		totalPassejants = 30;
		passejants = new ArrayList<Passejant>();
		cartes = new ArrayList<Carta>();
		
		for(int i = 0; i < totalPassejants; ++i){
			passejants.add(new Passejant(this.color, false));
		}
	}
	
	/**
	 * Funció que treu un passejant del jugador
	 * @return El passejant que s'ha tret
	 */
	public Passejant getUnPassejant(){
		--totalPassejants;
		if(totalPassejants < 0)return null;
		return passejants.remove(totalPassejants);
	}
	
	/**
	 * Acció que afegeix una carta a la mà del jugador
	 * @param carta Carta que s'afegeix
	 */
	public void afegirCarta(Carta carta){
		if(carta == null)return;
		System.out.println("Jugador" + id + " -> " + "Carta afegida -> " + carta.getNom());
		cartes.add(carta);
	}
	
	/**
	 * Acció que treu una carta de la mà del jugador
	 * @param carta Carta que es treu
	 */
	public void treureCarta(Carta carta){
		if(carta == null)return;
		System.out.println("Jugador" + id + " -> " + "Carta esborrada -> " + carta.getNom());
		cartes.remove(carta);
	}
	
	/**
	 * Acció que afegeix un determinat passejant al jugador
	 * @param p Passejant que es vol afegir
	 */
	public void afegeixUnPassejant(Passejant p){
		passejants.add(p);
		++totalPassejants;
	}
	
	/**
	 * Acció que permet determinar el nombre de passejants que volem que tingui el jugador
	 * @param num Nou nombre de passejants del jugador
	 */
	public void setNumPassejants(int num){
		passejants.clear();
		for (int i = 0; i < num; ++i){
			Passejant p = new Passejant(color, false);
			passejants.add(p);
		}
		totalPassejants = num;
	}
	
	//Getters & Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public int getTotalPassejants(){
		return totalPassejants;
	}

	public ArrayList<Carta> getCartes(){
		return cartes;
	}

	public int getTipusJugador() {
		return tipusJugador;
	}

	public void setTipusJugador(int tipusJugador) {
		this.tipusJugador = tipusJugador;
	}
}
