package upc.tfg.interfaces;

import upc.tfg.logic.Carta;
import upc.tfg.utils.PassejantsAMoure;

public interface JugadorRobot {
	public Carta getCartaRival();
	public Carta getCartaSeleccionada();
	public PassejantsAMoure getPassejantDistricte();
	public int getBarallaPerRobarCarta();
}
