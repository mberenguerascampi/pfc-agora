package upc.tfg.ia;

import java.util.ArrayList;
import java.util.Random;
import upc.tfg.utils.PassejantsAMoure;

import upc.tfg.interfaces.JugadorRobot;
import upc.tfg.logic.Carta;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;

public class JugadorRandom implements JugadorRobot{
	private Jugador jugador;
	
	public JugadorRandom(Jugador jugador) {
		this.jugador = jugador;
	}

	@Override
	public void obtenirMoviment(int pas) {
		System.out.println("Obtenir moviment pas -> " + pas);
		if(pas == 1){
			Carta cartaRobada = getCartaRival();
			System.out.println("Carta robada -> " + cartaRobada.getNom() + " del districte " + cartaRobada.getDistricte().getNom());
		}
		else if(pas == 2){
			Carta cartaSeleccionada = getCartaSeleccionada();
			System.out.println("Carta seleccionada -> " + cartaSeleccionada.getNom() + " del districte " + cartaSeleccionada.getDistricte().getNom());
		}
		else if (pas == 3){
			PassejantsAMoure pam = getPassejantDistricte();
			System.out.println("Passejant a moure de color " + pam.color + " del districte " + pam.districte.getNom());
		}
		else if (pas == 4){
			int idBaralla = getBarallaPerRobarCarta();
			System.out.println("Baralla " + idBaralla);
		}
	}
	
	public Carta getCartaRival(){
		int idJugadorAnterior = jugador.getId()-1;
		if(idJugadorAnterior == 0)idJugadorAnterior = 4;
		ArrayList<Carta> cartes = Partida.getInstance().getJugador(idJugadorAnterior).getCartes();
		Random rand = new Random(System.currentTimeMillis());
		int i = rand.nextInt(cartes.size()-1);
		return cartes.get(i);
	}
	
	public Carta getCartaSeleccionada(){
		ArrayList<Carta> cartes = jugador.getCartes();
		Random rand = new Random(System.currentTimeMillis());
		int i = rand.nextInt(cartes.size()-1);
		return cartes.get(i);
	}
	
	public PassejantsAMoure getPassejantDistricte(){
		Districte[] districtes = Partida.getInstance().getTauler().getDistrictes(); 
		Random rand = new Random(System.currentTimeMillis());
		int i = rand.nextInt(districtes.length-1);
		while(!districtes[i].tePassejantsDisponibles()) i = rand.nextInt(districtes.length-1);
		Districte d = districtes[i];
		int j = rand.nextInt(Constants.COLORS.length-1);
		while(!d.tePassejantsDisponibles(Constants.COLORS[j]))j = rand.nextInt(Constants.COLORS.length-1);
		return new PassejantsAMoure(Constants.COLORS[j], d);
	}
	
	public int getBarallaPerRobarCarta(){
		Random rand = new Random(System.currentTimeMillis());
		int barallaID = rand.nextInt(1);
		return barallaID+1;
	}
}
