package upc.tfg.interfaces;

import upc.tfg.logic.Carta;

public interface TaulerListener {
	public void cartaSeleccionada(int jugadorID, Carta cartaEntity);
	public void passejantMogut(int jugadorID, String districtName);
}
