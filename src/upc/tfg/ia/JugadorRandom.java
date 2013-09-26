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
		boolean trobat = false;
		int maxIntents = 12;
		
		while (!trobat){
			int intents = 0;
			
			//Obtenim el districte del qual agafarem un passejant
			int i = rand.nextInt(districtes.length-1);
			while(!districtes[i].tePassejantsDisponibles()) i = rand.nextInt(districtes.length-1);
			Districte d = districtes[i];
			
			//Obtenim el color del passejant que agafem 
			int j = rand.nextInt(Constants.COLORS.length-1);
			while(!d.tePassejantsDisponibles(Constants.COLORS[j]))j = rand.nextInt(Constants.COLORS.length-1);
			
			//Obtenim el districte on afegir el passejant
			int k = rand.nextInt(districtes.length-1);
			while(intents < maxIntents && !d.equals(districtes[k]) && !districtes[k].potAfegirPassejant(Constants.COLORS[j])){
				k = rand.nextInt(districtes.length-1);
				++intents;
			}
			if (intents < maxIntents) {
				return new PassejantsAMoure(Constants.COLORS[j], d, districtes[k]);
			}
		}
		return null;
	}
	
	public int getBarallaPerRobarCarta(){
		Random rand = new Random(System.currentTimeMillis());
		int barallaID = rand.nextInt(1);
		return barallaID+1;
	}
}
