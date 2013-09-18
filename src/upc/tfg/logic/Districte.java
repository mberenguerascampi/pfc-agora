package upc.tfg.logic;

import java.awt.Image;
import java.util.ArrayList;

import upc.tfg.utils.Constants;

public class Districte {
	private String nom;
	private int valor;
	private Image image;
	private int districteID;
	private ArrayList<Passejant> passejantsBlaus;
	private ArrayList<Passejant> passejantsVermells;
	private ArrayList<Passejant> passejantsVerds;
	private ArrayList<Passejant> passejantsGrocs;
	
	public Districte() {
		passejantsBlaus = new ArrayList<Passejant>();
		passejantsVermells = new ArrayList<Passejant>();
		passejantsVerds = new ArrayList<Passejant>();
		passejantsGrocs = new ArrayList<Passejant>();
	}
	
	public Districte(String nom, int valor, Image imatge, int districteID) {
		this.nom = nom;
		this.valor = valor;
		this.image = imatge;
		this.districteID = districteID;
		passejantsBlaus = new ArrayList<Passejant>();
		passejantsVermells = new ArrayList<Passejant>();
		passejantsVerds = new ArrayList<Passejant>();
		passejantsGrocs = new ArrayList<Passejant>();
	}
	
	public void afegeixPassejant(Passejant passejant){
		switch (passejant.getColor()){
			case Constants.BLAU:
				passejantsBlaus.add(passejant);
				break;
			case Constants.VERMELL:
				passejantsVermells.add(passejant);
				break;
			case Constants.GROC:
				passejantsGrocs.add(passejant);
				break;
			case Constants.VERD:
				passejantsVerds.add(passejant);
				break;
			default:
				break;
		}
		
	}
	
	public Passejant removePassejant(int color){
		switch (color){
			case Constants.BLAU:
				return passejantsBlaus.remove(0);
			case Constants.VERMELL:
				return passejantsVermells.remove(0);
			case Constants.GROC:
				return passejantsGrocs.remove(0);
			case Constants.VERD:
				return passejantsVerds.remove(0);
			default:
				return null;
		}
	}
	
	public boolean tePassejantsDisponibles(int color){
		ArrayList<Passejant>temp = getArray(color);
		if(temp.size() == 0)return false;
		for (Passejant p:temp){
			if(!p.getBloquejat())return true;
		}
		return false;
	}
	
	public boolean potAfegirPassejant(int color){
		ArrayList<Passejant>temp = getArray(color);
		if(temp.size()+1 >= passejantsBlaus.size() && temp.size()+1 >= passejantsVermells.size()
				&& temp.size()+1 >= passejantsGrocs.size() && temp.size()+1 >= passejantsVerds.size()){
			if(temp.size()+1 == passejantsBlaus.size() || temp.size()+1 == passejantsVermells.size()
					|| temp.size()+1 == passejantsGrocs.size() || temp.size()+1 == passejantsVerds.size()){
				return false;
			}
		}
		return true;
	}
	
	public int getNumPassejantsBlaus(){
		return passejantsBlaus.size();
	}
	
	public int getNumPassejantsVermells(){
		return passejantsVermells.size();
	}
	
	public int getNumPassejantsVerds(){
		return passejantsVerds.size();
	}
	
	public int getNumPassejantsGrocs(){
		return passejantsGrocs.size();
	}
	
	private ArrayList<Passejant> getArray(int color){
		ArrayList<Passejant>temp = null;
		switch (color){
			case Constants.BLAU:
				temp = passejantsBlaus;
				break;
			case Constants.VERMELL:
				temp = passejantsVermells;
				break;
			case Constants.GROC:
				temp = passejantsGrocs;
				break;
			case Constants.VERD:
				temp = passejantsVerds;
				break;
			default:
				return null;
		}
		return temp;
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
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}

	public int getDistricteID() {
		return districteID;
	}

	public void setDistricteID(int districteID) {
		this.districteID = districteID;
	}
}