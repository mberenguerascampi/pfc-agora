package upc.tfg.ia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import upc.tfg.utils.PassejantsAMoure;

import upc.tfg.interfaces.JugadorRobot;
import upc.tfg.logic.Carta;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;

public class JugadorRandom extends DefaultJugador implements JugadorRobot{
	private Jugador jugador;
	
	public JugadorRandom(Jugador jugador) {
		this.jugador = jugador;
	}
	
	public Carta getCartaRival(){
		int idJugadorAnterior = jugador.getId()-1;
		if(idJugadorAnterior == 0)idJugadorAnterior = 4;
		ArrayList<Carta> cartes = Partida.getInstance().getJugador(idJugadorAnterior).getCartes();
		Random rand = new Random(System.currentTimeMillis());
		int i = rand.nextInt(cartes.size());
		return cartes.get(i);
	}
	
	public Carta getCartaSeleccionada(){
		ArrayList<Carta> cartes = getPossiblesCartes(jugador);
		if(cartes == null) return null;
		Random rand = new Random(System.currentTimeMillis());
		int i = rand.nextInt(cartes.size());
		return cartes.get(i);
	}
	
	public PassejantsAMoure getPassejantDistricte(){
		ArrayList<PassejantsAMoure> possiblesPam = getPossiblesPassejantsAMoure();
		if(possiblesPam == null || possiblesPam.size() == 0)return null;
		//Districte[] districtes = Partida.getInstance().getTauler().getDistrictes(); 
		Random rand = new Random(System.currentTimeMillis());
		PassejantsAMoure solucio = possiblesPam.get(rand.nextInt(possiblesPam.size()));
		return solucio;
	}
	
	public int getBarallaPerRobarCarta(){
		Random rand = new Random(System.currentTimeMillis());
		int barallaID = rand.nextInt(6);
		return (barallaID%2)+1;
	}
	
	public int getProximJugadorInici(){
		int[] possiblesJugadors = getPossibleJugadorInici();
		Random rand = new Random(System.currentTimeMillis());
		int indexJ = rand.nextInt(possiblesJugadors.length);
		return possiblesJugadors[indexJ];
	}
}
