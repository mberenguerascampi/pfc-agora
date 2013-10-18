package upc.tfg.logic;

import java.util.ArrayList;
import java.util.Random;

import upc.tfg.utils.CartesBD;

public class Baralla {
	private int id;
	private ArrayList<Carta> cartes;
	
	public Baralla(){
		cartes = new ArrayList<Carta>();
		for(int i = 0; i < CartesBD.nomsCartes.length; ++i){
			cartes.add(new Carta(CartesBD.nomsCartes[i], CartesBD.valorsCartes[i], CartesBD.nomsCartesComplets[i]));
		}
	}
	
	public Baralla(Carta[] cartes){
		this.cartes = new ArrayList<Carta>();
		if(cartes == null) return;
		for(Carta carta:cartes){
			this.cartes.add(carta);
		}
	}
	
	public Baralla(ArrayList<Carta> cartes){
		this.cartes = cartes;
	}
	
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
	
	public Carta[] getCartes(int numCartes){
		Carta[] cartesADonar = new Carta[numCartes];
		for (int i = 0; i < numCartes; ++i){
			cartesADonar[i] = cartes.remove(i);
		}
		
		return cartesADonar;
	}
	
	public ArrayList<Carta> getCartes(){
		return this.cartes;
	}
	
	public int getNumCartes(){
		return cartes.size();
	}
}
