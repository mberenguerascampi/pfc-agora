package upc.tfg.logic;

import java.util.Random;

public class Baralla {
	private int id;
	private Carta[] cartes;
	
	public Baralla(){
		
	}
	
	public void barrejar(){
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		int n = cartes.length;
		for(int i = 0; i < n; ++i){
			int r1, r2;
			r1 = rand.nextInt(n-1);
			r2 = rand.nextInt(n-1);
			while (r1 == r2) r2 = rand.nextInt(n-1);
			Carta c1 = cartes[r1];
			cartes[r1] = cartes[r2];
			cartes[r2] = c1;
		}
	}
}
