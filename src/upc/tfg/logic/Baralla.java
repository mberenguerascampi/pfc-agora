package upc.tfg.logic;

import java.util.ArrayList;
import java.util.Random;

import upc.tfg.utils.CartesBD;

/**
 * Classe que representa l'entitat d'una baralla de cartes
 * @author Marc
 *
 */
public class Baralla {
	private ArrayList<Carta> cartes;
	
	/**
	 * Constructora de la classe amb totes les cartes possibles
	 */
	public Baralla(){
		cartes = new ArrayList<Carta>();
		for(int i = 0; i < CartesBD.nomsCartes.length; ++i){
			cartes.add(new Carta(CartesBD.nomsCartes[i], CartesBD.valorsCartes[i], CartesBD.nomsCartesComplets[i]));
		}
	}
	
	/**
	 * Constructora de la classe
	 * @param cartes Array de cartes amb el que es vol crear la baralla
	 */
	public Baralla(Carta[] cartes){
		this.cartes = new ArrayList<Carta>();
		if(cartes == null) return;
		for(Carta carta:cartes){
			this.cartes.add(carta);
		}
	}
	
	/**
	 * Constructora de la classe
	 * @param cartes ArrayList de cartes amb el que es vol crear la baralla
	 */
	public Baralla(ArrayList<Carta> cartes){
		this.cartes = cartes;
	}
	
	/**
	 * Funció que barreja tote les cartes que hi ha a la baralla
	 */
	public void barrejar(){
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		int n = cartes.size();
		for(int i = 0; i < n; ++i){
			int r1, r2;
			r1 = rand.nextInt(n-1);
			r2 = rand.nextInt(n-1);
			while (r1 == r2) r2 = rand.nextInt(n-1);
			Carta c1 = cartes.get(r1);
			cartes.set(r1, cartes.get(r2));
			cartes.set(r2, c1);
		}
	}
	
	/**
	 * Funció que true un cert nombre de cartes de la baralla en l'ordre en el qual estan
	 * @param numCartes Nombre de cartes que es vol treue
	 * @return Un array amb les cartes que s'han eliminat de la baralla
	 */
	public Carta[] getCartes(int numCartes){
		Carta[] cartesADonar = new Carta[numCartes];
		for (int i = 0; i < numCartes; ++i){
			cartesADonar[i] = cartes.remove(i);
		}
		return cartesADonar;
	}
	
	/**
	 * Funció que retorn les cartes que actualment hi ha a la baralla
	 * @return ArrayList amb les cartes
	 */
	public ArrayList<Carta> getCartes(){
		return this.cartes;
	}
	
	/**
	 * Mètode per consultar el nombre de cartes
	 * @return El número de cartes de la baralla
	 */
	public int getNumCartes(){
		return cartes.size();
	}
}
