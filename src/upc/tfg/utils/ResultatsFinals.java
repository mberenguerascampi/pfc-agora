package upc.tfg.utils;

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
}
