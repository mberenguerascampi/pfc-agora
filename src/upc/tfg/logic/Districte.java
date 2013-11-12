package upc.tfg.logic;

import java.awt.Image;
import java.util.ArrayList;

import upc.tfg.utils.Constants;

/**
 * Classe que representa l'entitat d'un districte
 * @author Marc
 *
 */
public class Districte {
	private String nom;
	private int valor;
	private Image image;
	private int districteID;
	private ArrayList<Passejant> passejantsBlaus;
	private ArrayList<Passejant> passejantsVermells;
	private ArrayList<Passejant> passejantsVerds;
	private ArrayList<Passejant> passejantsGrocs;
	
	/**
	 * Constructora de la classe
	 */
	public Districte() {
		passejantsBlaus = new ArrayList<Passejant>();
		passejantsVermells = new ArrayList<Passejant>();
		passejantsVerds = new ArrayList<Passejant>();
		passejantsGrocs = new ArrayList<Passejant>();
	}
	
	/**
	 * Constructora de la classe que fa una còpia del districte que es passa per paràmetre
	 * @param d Districte que volem copiar
	 */
	public Districte(Districte d){
		this.nom = d.getNom();
		this.valor = d.getValor();
		this.image = d.getImage();
		this.districteID = d.getDistricteID();
		passejantsBlaus = new ArrayList<Passejant>();
		passejantsVermells = new ArrayList<Passejant>();
		passejantsVerds = new ArrayList<Passejant>();
		passejantsGrocs = new ArrayList<Passejant>();
		addPassejants(passejantsBlaus, Constants.BLAU, d);
		addPassejants(passejantsVermells, Constants.VERMELL, d);
		addPassejants(passejantsVerds, Constants.VERD, d);
		addPassejants(passejantsGrocs, Constants.GROC, d);
	}
	
	/**
	 * Constructora de la classe que crea un districte a partir de les caractterístiques que li passem
	 * @param nom Nom del districte
	 * @param valor Valor del districte
	 * @param imatge Imatge que té el districte
	 * @param districteID Identificador del districte
	 */
	public Districte(String nom, int valor, Image imatge, int districteID) {
		this.nom = nom;
		this.valor = valor;
		this.image = imatge;
		this.districteID = districteID;
		passejantsBlaus = new ArrayList<Passejant>();
		passejantsVermells = new ArrayList<Passejant>();
		passejantsVerds = new ArrayList<Passejant>();
		passejantsGrocs = new ArrayList<Passejant>();
		
//		Passejant p = new Passejant(Constants.VERMELL, false);
//		afegeixPassejant(p);
//		Passejant p2 = new Passejant(Constants.VERMELL, false);
//		afegeixPassejant(p2);
//		Passejant p3 = new Passejant(Constants.VERD, false);
//		afegeixPassejant(p3);
//		Passejant p4 = new Passejant(Constants.GROC, false);
//		afegeixPassejant(p4);
//		Passejant p5 = new Passejant(Constants.VERMELL, false);
//		afegeixPassejant(p5);
	}
	
	/**
	 * Acció que permet afegir un passejant a un array el conjunt de passejant que té un districte
	 * d'un determinat color
	 * @param arrayP ArrayList on volem afgir els passejants
	 * @param color Color del qual volem obtenir els passejants
	 * @param d Districte d'on volem treure'n els passejants
	 */
	private void addPassejants(ArrayList<Passejant> arrayP, int color, Districte d){
		ArrayList<Passejant> auxArray = d.getArray(color);
		for(int i = 0; i < auxArray.size(); ++i) {
			Passejant p = new Passejant(auxArray.get(i).getColor(), auxArray.get(i).getBloquejat());
			arrayP.add(p);
		}
	}
	
	/**
	 * Acció que afegeix un determinat passejant al districte
	 * @param passejant Passejant que volem afegir
	 */
	public void afegeixPassejant(Passejant passejant){
		System.out.println("Passejant afegit");
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
	
	/**
	 * Acció que inicialitza un determinat nombre de passejant, de un determinat color, i els afegeix al districte
	 * @param color Color dels passejants a afegir
	 * @param numP Nombre de passejants que volem afegir
	 * @param numBloquejats Nombre de passejants que han d'estar bloquejats del total 
	 */
	public void inicialitzaIAfefeixPassejants(int color, int numP, int numBloquejats){
		int bloquejatsActualment = 0;
		for(int i = 0; i < numP; ++i){
			boolean bloquejat = (bloquejatsActualment <= numBloquejats);
			Passejant p = new Passejant(color, bloquejat);
			afegeixPassejant(p);
			if(bloquejat)++bloquejatsActualment;
		}
	}
	
	/**
	 * Funció que esborra un passejant del districte
	 * @param color Color del passejant que volem esborrar
	 * @return El passsejant que hem tre d'aquest districte
	 */
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
	
	/**
	 * Funció que ens indica si en el districte hi ha dos o més jugadors amb el nombre més alt de passejants
	 * @return True si es produeix empat, false en cas contrari
	 */
	public boolean teMateixNombreMaxPassejants(){
		int max1 = Math.max(passejantsBlaus.size(), passejantsVermells.size());
		int min1 = Math.min(passejantsBlaus.size(), passejantsVermells.size());
		int max2 = Math.max(passejantsGrocs.size(), passejantsVerds.size());
		int min2 = Math.min(passejantsGrocs.size(), passejantsVerds.size());
		//Empat entre (blau o vermell) i (groc o verd)
		if ((max1 != 0 || Partida.getInstance().getTorn() >= 5) && max1 == max2)return true; 
		//Empat entre blau i vermell)
		if (max1 > max2 && max1 == min1)return true;
		//Empat entre groc i verd
		if (max2 > max1 && max2 == min2)return true;
		return false;
	}
	
	/**
	 * Funció que indica si el districte té cap passejant no bloquejat d'un determinat color
	 * @param color Color del qual volem conèixer la disponibilitat
	 * @return True si hi ha cap passejant disponible, false en cas contrari
	 */
	public boolean tePassejantsDisponibles(int color){
		ArrayList<Passejant>temp = getArray(color);
		if(temp.size() == 0)return false;
		for (Passejant p:temp){
			if(!p.getBloquejat())return true;
		}
		return false;
	}
	
	/**
	 * Funció que diu si hi ha cap passejant disponible per moure d'algun color
	 * @return True si n'hi ha algun de disponible, false en cas contrari
	 */
	public boolean tePassejantsDisponibles(){
		return (tePassejantsDisponibles(Constants.BLAU) || tePassejantsDisponibles(Constants.VERMELL) || tePassejantsDisponibles(Constants.VERD) || tePassejantsDisponibles(Constants.GROC));
	}
	
	/**
	 * Funció que indica si es pot afegir un passejant al districte d'un determinat color
	 * @param color Color a tenir en compte per mirar si es pot afegir el passejant
	 * @return True si es pot afegir, false en cas contrari
	 */
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
	
	/**
	 * Funció que indica si es pot treure un passejant del districte d'un determinat color
	 * @param color Color a tenir en compte per mirar si es pot treure el passejant
	 * @return True si es pot treure, false en cas contrari
	 */
	public boolean potTreurePassejant(int color){
		ArrayList<Passejant>temp = getArray(color);
		ArrayList<Passejant>temp2 = getMaxArray();
		//Si els passejants del determinat color no són els que predominen es poden treure
		if(!temp.equals(temp2)) return true;
		//Estem treient un passejant del color que predomina
		else {
			int newSize = temp.size()-1;
			//Si es zero permetem treure
			if(Partida.getInstance().getTorn() < 5 && newSize == 0)return true;
			//Si el treure un passejant implica que es produeixi un empat tornem false
			if(passejantsBlaus.size() == newSize || passejantsVermells.size() == newSize 
					|| passejantsGrocs.size() == newSize || passejantsVerds.size() == newSize){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Funció per obtenir la llista de passejants que més predominen
	 * @return ArrayList dels passejants del color que és més abundant en el districte
	 */
	private ArrayList<Passejant> getMaxArray(){
		int maxSize = Math.max(Math.max(passejantsBlaus.size(), passejantsVermells.size()), 
					Math.max(passejantsGrocs.size(), passejantsVerds.size()));
		if(maxSize == passejantsBlaus.size()) return passejantsBlaus;
		else if(maxSize == passejantsVermells.size()) return passejantsVermells;
		else if(maxSize == passejantsGrocs.size()) return passejantsGrocs;
		else return passejantsVerds;
	}
	
	
	/**
	 * Funció per obtenir el nombre de passejants d'un determinat color
	 * @param color Color del qual volem obtenir el nombre de passejants
	 * @return El nombre de passejants del color
	 */
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
	
	/**
	 * Funció que peremt obtenir la llista de passejants d'un determinat color
	 * @param color Color del qual voleem obtenir la llista de passejants
	 * @return ArrayList amb els passejants del color pasat com a paràmetre
	 */
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
	
	/**
	 * Acció que desbloqueja tots els passejants del districte
	 */
	public void restartPassejants(){
		for(Passejant p:passejantsBlaus)p.desbloquejar();
		for(Passejant p:passejantsVermells)p.desbloquejar();
		for(Passejant p:passejantsVerds)p.desbloquejar();
		for(Passejant p:passejantsGrocs)p.desbloquejar();
	}
	
	/**
	 * Funció que indica quin és el color que més abunda en el districte
	 * @return L'identificador del color que predomina en el districte
	 */
	public int getColorGuanyador(){
		int sizes[] = {passejantsBlaus.size(), passejantsVermells.size(), passejantsVerds.size(), passejantsGrocs.size()};
		int colors[] = {Constants.BLAU, Constants.VERMELL, Constants.VERD, Constants.GROC};
		int max = Math.max(Math.max(sizes[0],  sizes[1]), Math.max(sizes[2],  sizes[3]));
		for(int i = 0; i < sizes.length; ++i){
			if(sizes[i] == max) return colors[i];
		}
		return 0;
	}
	
	public int getDiferenciaRespecteGuanyador(){
		int sizes[] = {passejantsBlaus.size(), passejantsVermells.size(), passejantsVerds.size(), passejantsGrocs.size()};
		int max = Math.max(Math.max(sizes[0],  sizes[1]), Math.max(sizes[2],  sizes[3]));
		int max2 = Integer.MIN_VALUE;
		for(int i = 0; i < sizes.length; ++i){
			if(sizes[i] != max) {
				max2 = Math.max(max2, sizes[i]);
			}
		}
		return max-max2;
	}
	
	/**
	 * Funció que ens indica si es produïrà un empat en nombre màxim de passejants si afegim un
	 * determinat nombre de passejants d'un determinat color
	 * @param numPassejants Nombre de passejants a afegir
	 * @param color Color dels passejants a afegir
	 * @return True si es produeix empat, false en cas contrari
	 */
	public boolean tindraMateixNombrePassejants(int numPassejants, int color) {
		ArrayList<Passejant>temp = getArray(color);
		ArrayList<Passejant>temp2 = getMaxArray();
		//Si els passejants del determinat color són els que predominen es poden afegir
		if(temp.equals(temp2)) return false;
		else if(temp2.size() == temp.size() + numPassejants) return true;
		return false;
	}
	
	/**
	 * Funció per obtenir el nombre de passejants bloquejats d'un determinat color
	 * @param color Color del qual volem obtenir el nombre de passejants bloquejats
	 * @return El nombre de passejants bloquejats del color 
	 */
	public int getNumPassejantsBloquejats(int color){
		int numBloquejats = 0;
		ArrayList<Passejant> passejants = getArray(color);
		for(Passejant p:passejants){
			if(p.getBloquejat()) ++numBloquejats;
		}
		return numBloquejats;
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
}
