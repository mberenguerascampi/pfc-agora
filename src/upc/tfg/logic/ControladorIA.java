package upc.tfg.logic;

import upc.tfg.ia.JugadorRandom;
import upc.tfg.utils.PassejantsAMoure;
import upc.tfg.interfaces.JugadorRobot;

public class ControladorIA {
	private ControladorLogic logic;
	private Jugador[] jugadors;
	private JugadorRobot[] robots;
	
	public ControladorIA(ControladorLogic logic) {
		this.logic = logic;
		jugadors = new Jugador[3];
		robots = new JugadorRobot[3];
	}
	
	public void setJugadors(Jugador[] jugadors){
		this.jugadors = jugadors;
		for(int i = 0; i < jugadors.length; ++i){
			robots[i] = new JugadorRandom(jugadors[i]);
		}
	}
	
	public void getProximMoviment(int jugadorID, int pas){
		JugadorRobot robot = robots[jugadorID-2];
		System.out.println("Obtenir moviment pas -> " + pas);
		if(pas == 1){
			Carta cartaRobada = robot.getCartaRival();
			logic.cartaRobada(jugadorID, cartaRobada);
			System.out.println("Carta robada -> " + cartaRobada.getNom() + " del districte " + cartaRobada.getDistricte().getNom());
		}
		else if(pas == 2){
			if(jugadors[jugadorID-2].getTotalPassejants() == 0) {
				//TODO: Descartar carta
				return;
			}
			Carta cartaSeleccionada = robot.getCartaSeleccionada();
			logic.cartaSeleccionada(cartaSeleccionada, jugadorID);
			System.out.println("Carta seleccionada -> " + cartaSeleccionada.getNom() + " del districte " + cartaSeleccionada.getDistricte().getNom());
		}
		else if (pas == 3){
			for (int i = 0; i < 2; ++i){
				PassejantsAMoure pam = robot.getPassejantDistricte();
				if(pam != null)logic.mouPassejantsEntreDistrictes(pam.districteOrigen.getNom(), pam.districteDesti.getNom(), pam.color);
				else{
					Partida.getInstance().avancarJugador();
					logic.getProximMoviment();
				}
				if(pam != null)System.out.println("Passejant a moure de color " + pam.color + " del districte " + pam.districteOrigen.getNom());
			}
		}
		else if (pas == 4){
			int idBaralla = 0;
			if(Partida.getInstance().getBaralla().getCartes().size() == 0 && Partida.getInstance().getBaralla2().getCartes().size() == 0){
				idBaralla = -1;
			}
			else if (Partida.getInstance().getBaralla().getCartes().size() == 0) idBaralla = 2;
			else if (Partida.getInstance().getBaralla2().getCartes().size() == 0) idBaralla = 1;
			else idBaralla = robot.getBarallaPerRobarCarta();
			logic.cartaAgafadaDeLaBaralla(jugadorID, idBaralla);
			System.out.println("Baralla " + idBaralla);
		}
	}
}
