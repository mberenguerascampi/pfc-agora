package upc.tfg.logic;

public class Passejant {
	private boolean bloquejat;
	private int color;
	
	/**
	 * Classe que representa l'entitat d'un pasejant
	 */
	public Passejant() {	
	}
	
	/**
	 * Constructora de la classe
	 * @param color Color del nou passejant
	 * @param bloquejat Boolean que indica si el passejant està bloquejat o no
	 */
	public Passejant(int color, boolean bloquejat){
		this.color = color;
		this.bloquejat = bloquejat;
	}
	
	//Public Methods
	/**
	 * Acció que bloqueja el passejant
	 */
	public void bloquejar(){
		bloquejat = true;
	}
	
	/**
	 * Acció que desbloqueja el passejant
	 */
	public void desbloquejar(){
		bloquejat = false;
	}
	
	//Getters & setters
	
	public boolean getBloquejat(){
		return bloquejat;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
