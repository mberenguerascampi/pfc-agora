package upc.tfg.utils;

import java.util.Arrays;

/**
 * Classe que representa el resultats de calcular els punts en una partida
 * @author Marc
 *
 */
public class ResultatsFinals {
	private int puntsJ1;
	private int puntsJ2;
	private int puntsJ3;
	private int puntsJ4;
	private int idJugadorGuanyador;
	
	/**
	 * Constructora de la classe
	 * @param puntsJ1 Punts que ha obtingut el jugador amb id=1
	 * @param puntsJ2 Punts que ha obtingut el jugador amb id=2
	 * @param puntsJ3 Punts que ha obtingut el jugador amb id=3
	 * @param puntsJ4 Punts que ha obtingut el jugador amb id=4
	 * @param idJugadorGuanyador Identificador del jugador guanyador
	 */
	public ResultatsFinals(int puntsJ1, int puntsJ2, int puntsJ3, int puntsJ4, int idJugadorGuanyador) {
		this.puntsJ1 = puntsJ1;
		this.puntsJ2 = puntsJ2;
		this.puntsJ3 = puntsJ3;
		this.puntsJ4 = puntsJ4;
		this.idJugadorGuanyador = idJugadorGuanyador;
	}
	
	/**
	 * Funció que ens permet obtenir els punts d'un jugador en concret
	 * @param idJugador Identificador del jugador del qual volem conèixer la puntuació
	 * @return El nombre de punts del jugador
	 */
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
	
	/**
	 * Mètode que ens permet obtenir la màxima puntuació sense tenir en compte un jugador en concret
	 * @param idJugadorExcepcio Identificador del jugador que no volem tenir en compte per obtenir la màxima puntuació
	 * @return La màxima puntuació sense tenir en compte el jugador especificat
	 */
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
	
	public int getIdJugadorGuanyador(int idJugadorExcepcio) {
		int max = Integer.MIN_VALUE;
		int idGuanyador = 1;
		for(int i = 1; i <= 4; ++i){
			if(i != idJugadorExcepcio){
				if(max < getPunts(i)){
					max = getPunts(i);
					idGuanyador = i;
				}
			}
		}
		return idGuanyador;
	}

	//Getters & Setters
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
}
