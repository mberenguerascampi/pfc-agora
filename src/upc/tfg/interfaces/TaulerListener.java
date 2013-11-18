package upc.tfg.interfaces;

import upc.tfg.logic.Carta;
import upc.tfg.utils.ResultatsFinals;

public interface TaulerListener {
	public void cartaSeleccionada(int jugadorID, Carta cartaEntity);
	public void cartaDescartada(Carta carta, int jugadorID);
	public void cartaRobada(int jugadorID, Carta cartaEntity);
	public void passejantMogut(int jugadorID, String districtName);
	public void passejantMogutEntreDistrictes(String districtName1, String districtName2, int color);
	public void cartaAgafada(int jugadorID, int barallaID);
	public void seleccionatJugadorInici(int idJugador);
	public void nextPlayer();
	public void infoButtonPressed();
	public boolean saveButtonPressed(String nom);
	public void tornarAlMenu();
	public void mostraFinalPartida(ResultatsFinals res, boolean resFinals);
}
