package upc.tfg.logic;

import java.awt.Image;

public class Carta {
	private String nom;
	private int valor;
	private boolean showing;
	private Image image;
	private Districte districte;
	
	public Carta() {
		
	}
	
	//Public Methods
	public void girar(){
		
	}

	
	//Getters & Setters
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public boolean isShowing() {
		return showing;
	}

	public void setShowing(boolean showing) {
		this.showing = showing;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Districte getDistricte() {
		return districte;
	}

	public void setDistricte(Districte districte) {
		this.districte = districte;
	}
}
