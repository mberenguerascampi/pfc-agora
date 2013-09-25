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
		
		Passejant p = new Passejant(Constants.VERMELL, false);
		afegeixPassejant(p);
		Passejant p2 = new Passejant(Constants.VERMELL, false);
		afegeixPassejant(p2);
		Passejant p3 = new Passejant(Constants.VERD, false);
		afegeixPassejant(p3);
		Passejant p4 = new Passejant(Constants.GROC, false);
		afegeixPassejant(p4);
		Passejant p5 = new Passejant(Constants.VERMELL, false);
		afegeixPassejant(p5);
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
	
	public boolean teMateixNombreMaxPassejants(){
		int max1 = Math.max(passejantsBlaus.size(), passejantsVermells.size());
		int min1 = Math.min(passejantsBlaus.size(), passejantsVermells.size());
		int max2 = Math.max(passejantsGrocs.size(), passejantsVerds.size());
		int min2 = Math.min(passejantsGrocs.size(), passejantsVerds.size());
		if (max1 == max2)return true;
		if (max1 > max2 && max1 == min1)return true;
		if (max2 > max1 && max2 == min2)return true;
		return false;
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
	
	public boolean potTreurePassejant(int color){
		ArrayList<Passejant>temp = getArray(color);
		ArrayList<Passejant>temp2 = getMaxArray();
		if(!temp.equals(temp2)) return true;
		else {
			int newSize = temp.size()-1;
			if(passejantsBlaus.size() == newSize || passejantsVermells.size() == newSize 
					|| passejantsGrocs.size() == newSize || passejantsVerds.size() == newSize){
				return false;
			}
		}
		return true;
	}
	
	private ArrayList<Passejant> getMaxArray(){
		int maxSize = Math.max(Math.max(passejantsBlaus.size(), passejantsVermells.size()), 
					Math.max(passejantsGrocs.size(), passejantsVerds.size()));
		if(maxSize == passejantsBlaus.size()) return passejantsBlaus;
		else if(maxSize == passejantsVermells.size()) return passejantsVermells;
		else if(maxSize == passejantsGrocs.size()) return passejantsGrocs;
		else return passejantsVerds;
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
	
	public int getNumPassejants(int color){
		switch (color){
			case Constants.BLAU:
				return getNumPassejantsBlaus();
			case Constants.VERMELL:
				return getNumPassejantsVermells();
			case Constants.GROC:
				return getNumPassejantsGrocs();
			case Constants.VERD:
				return getNumPassejantsVerds();
			default:
				return 0;
		}
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
	
	public void restartPassejants(){
		for(Passejant p:passejantsBlaus)p.desbloquejar();
		for(Passejant p:passejantsVermells)p.desbloquejar();
		for(Passejant p:passejantsVerds)p.desbloquejar();
		for(Passejant p:passejantsGrocs)p.desbloquejar();
	}
	
	public int getColorGuanyador(){
		int sizes[] = {passejantsBlaus.size(), passejantsVermells.size(), passejantsVerds.size(), passejantsGrocs.size()};
		int colors[] = {Constants.BLAU, Constants.VERMELL, Constants.VERD, Constants.GROC};
		int max = Math.max(Math.max(sizes[0],  sizes[1]), Math.max(sizes[2],  sizes[3]));
		for(int i = 0; i < sizes.length; ++i){
			if(sizes[i] == max) return colors[i];
		}
		return 0;
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
