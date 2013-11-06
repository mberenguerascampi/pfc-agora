package upc.tfg.utils;

import java.util.Arrays;

public class ResultatsFinals {
	private int puntsJ1;
	private int puntsJ2;
	private int puntsJ3;
	private int puntsJ4;
	private int idJugadorGuanyador;
	
	public ResultatsFinals(int puntsJ1, int puntsJ2, int puntsJ3, int puntsJ4, int idJugadorGuanyador) {
		this.puntsJ1 = puntsJ1;
		this.puntsJ2 = puntsJ2;
		this.puntsJ3 = puntsJ3;
		this.puntsJ4 = puntsJ4;
		this.idJugadorGuanyador = idJugadorGuanyador;
	}

	public int getPuntsJ1() {
		return puntsJ1;
	}

	public int getPuntsJ2() {
		return puntsJ2;
	}

	public int getPuntsJ3() {
		return puntsJ3;
	}

	public int getPuntsJ4() {
		return puntsJ4;
	}

	public int getIdJugadorGuanyador() {
		return idJugadorGuanyador;
	}
	
	public int getPunts(int idJugador){
		switch(idJugador){
			case 1:
				return getPuntsJ1();
			case 2:
				return getPuntsJ2();
			case 3:
				return getPuntsJ3();
			case 4:
				return getPuntsJ4();
			default:
				return 0;
		}
	}
	
	public int getMaxPunts(int idJugadorExcepcio){
		int[] punts = new int[3];
		int index = 0;
		for(int i = 1; i <= 4; ++i){
			if(i != idJugadorExcepcio){
				punts[index] = getPunts(i);
				++index;
			}
		}
		Arrays.sort(punts);
		return punts[2];
	}
}
