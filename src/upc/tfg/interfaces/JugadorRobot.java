package upc.tfg.interfaces;

import upc.tfg.logic.Carta;
import upc.tfg.utils.PassejantsAMoure;

public interface JugadorRobot {
	public void obtenirMoviment(int pas);
	public Carta getCartaRival();
	public Carta getCartaSeleccionada();
	public PassejantsAMoure getPassejantDistricte();
	public int getBarallaPerRobarCarta();
}
