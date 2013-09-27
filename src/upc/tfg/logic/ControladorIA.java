package upc.tfg.logic;

import upc.tfg.agora.Agora;
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
			Carta cartaSeleccionada = robot.getCartaSeleccionada();
			logic.cartaSeleccionada(cartaSeleccionada, jugadorID);
			System.out.println("Carta seleccionada -> " + cartaSeleccionada.getNom() + " del districte " + cartaSeleccionada.getDistricte().getNom());
		}
		else if (pas == 3){
			for (int i = 0; i < 2; ++i){
				PassejantsAMoure pam = robot.getPassejantDistricte();
				logic.mouPassejantsEntreDistrictes(pam.districteOrigen.getNom(), pam.districteDesti.getNom(), pam.color);
				System.out.println("Passejant a moure de color " + pam.color + " del districte " + pam.districteOrigen.getNom());
			}
		}
		else if (pas == 4){
			int idBaralla = robot.getBarallaPerRobarCarta();
			logic.cartaAgafadaDeLaBaralla(jugadorID, idBaralla);
			System.out.println("Baralla " + idBaralla);
		}
	}
}
