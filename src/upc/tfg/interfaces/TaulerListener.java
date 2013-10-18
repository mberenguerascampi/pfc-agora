package upc.tfg.interfaces;

import upc.tfg.logic.Carta;

public interface TaulerListener {
	public void cartaSeleccionada(int jugadorID, Carta cartaEntity);
	public void cartaRobada(int jugadorID, Carta cartaEntity);
	public void passejantMogut(int jugadorID, String districtName);
	public void passejantMogutEntreDistrictes(String districtName1, String districtName2, int color);
	public void cartaAgafada(int jugadorID, int barallaID);
	public void nextPlayer();
	public void infoButtonPressed();
	public boolean saveButtonPressed(String nom);
}
