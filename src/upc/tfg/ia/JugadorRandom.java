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
		if(Partida.getInstance().getTorn() == Partida.getInstance().getUltimTorn()){
			return getPassejantUltimTorn();
		}
		ArrayList<PassejantsAMoure> possiblesPam = getPossiblesPassejantsAMoure();
		if(possiblesPam == null || possiblesPam.size() == 0)return null;
		//Districte[] districtes = Partida.getInstance().getTauler().getDistrictes(); 
		Random rand = new Random(System.currentTimeMillis());
		PassejantsAMoure solucio = possiblesPam.get(rand.nextInt(possiblesPam.size()));
		return solucio;
	}
	
	public PassejantsAMoure getPassejantUltimTorn(){
		Districte[] districtes = Partida.getInstance().getTauler().getDistrictes(); 
		Random rand = new Random(System.currentTimeMillis());
		int color = jugador.getColor();
		
		//Obtenim el districte del qual agafarem un passejant
		boolean trobat = false;
		while(!trobat){
			System.out.println("Obtenim el districte del qual agafarem un passejant");
			int i = rand.nextInt(districtes.length);
			while(!districtes[i].tePassejantsDisponibles(color)) {
				i = rand.nextInt(districtes.length);
			}
			Districte d = districtes[i];
			System.out.println(d.getNom());
			
			//Obtenim el districte on afegir el passejant
			System.out.println("Obtenim el districte on afegir el passejant");
			List<Integer> dAdjacents = Partida.getInstance().getDistrictesAdjacents(d);
			int k = rand.nextInt(dAdjacents.size());
			Districte dDesti = districtes[k];
			int numIntents = 0;
			while(numIntents < dAdjacents.size()*2 && !Partida.getInstance().potMoure(d.getNom(), dDesti.getNom(), color)){
				System.out.println(dDesti.getNom());
				k = rand.nextInt(dAdjacents.size());
				dDesti = districtes[k];
				++numIntents;
			}
			if(numIntents < dAdjacents.size()*2){
				trobat = true;
				return new PassejantsAMoure(color, d, dDesti);
			}
		}
		return null;
	}
	
	public int getBarallaPerRobarCarta(){
		Random rand = new Random(System.currentTimeMillis());
		int barallaID = rand.nextInt(6);
		return (barallaID%2)+1;
	}
}
