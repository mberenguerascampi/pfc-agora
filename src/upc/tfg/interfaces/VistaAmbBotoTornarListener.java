package upc.tfg.interfaces;

import upc.tfg.logic.Partida;

public interface VistaAmbBotoTornarListener {
	public void backButtonPressed();
	public void loadGame(Partida partida);
	public void deleteGame(String nom);
	public void començarPartida(String nomJ1, String nomJ2, String nomJ3, String nomJ4, int[]colors, int[]ias);
	public void mostraSituacioFinalTauler();
}
