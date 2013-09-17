package upc.tfg.logic;

import java.util.ArrayList;

public class Jugador {
	private String nom;
	private int id;
	private int color;
	private ArrayList<Passejant> passejants;
	int totalPassejants;
	
	public Jugador(String nom, int id, int color) {
		this.nom = nom;
		this.id = id;
		this.color = color;
		totalPassejants = 30;
		passejants = new ArrayList<Passejant>();
		
		for(int i = 0; i < totalPassejants; ++i){
			passejants.add(new Passejant(this.color, false));
		}
	}
	
	
	public Passejant getUnPassejant(){
		--totalPassejants;
		return passejants.remove(totalPassejants);
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

}
