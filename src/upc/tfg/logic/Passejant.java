package upc.tfg.logic;

public class Passejant {
	private boolean bloquejat;
	private int color;
	
	public Passejant() {	
	}
	
	public Passejant(int color, boolean bloquejat){
		this.color = color;
		this.bloquejat = bloquejat;
	}
	
	//Public Methods
	public void bloquejar(){
		bloquejat = true;
	}
	
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
