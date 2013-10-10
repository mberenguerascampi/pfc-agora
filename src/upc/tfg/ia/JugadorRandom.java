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
		int i = rand.nextInt(cartes.size());
		return cartes.get(i);
	}
	
	public Carta getCartaSeleccionada(){
		ArrayList<Carta> cartes = jugador.getCartes();
		if (jugador.getId() == 4){
			for(Carta carta:cartes){
				System.out.println("           DISTRICTE: " + carta.getDistricte().getNom() +
						", CARTA: " + carta.getNom());
			}
		}
		Random rand = new Random(System.currentTimeMillis());
		int i = rand.nextInt(cartes.size());
		return cartes.get(i);
	}
	
	public PassejantsAMoure getPassejantDistricte(){
		Districte[] districtes = Partida.getInstance().getTauler().getDistrictes(); 
		Random rand = null;
		boolean trobat = false;
		int maxIntents = 20;
		int totalIntents = 0;
		
		while (!trobat && totalIntents < 30){
			rand = new Random(System.currentTimeMillis());
			int intents = 0;
			
			//Obtenim el districte del qual agafarem un passejant
			System.out.println("Obtenim el districte del qual agafarem un passejant");
			int i = rand.nextInt(districtes.length);
			while(!districtes[i].tePassejantsDisponibles()) i = rand.nextInt(districtes.length);
			Districte d = districtes[i];
			System.out.println(d.getNom());
			
			//Obtenim el color del passejant que agafem
			System.out.println("Obtenim el color del passejant que agafem");
			int j = rand.nextInt(Constants.COLORS.length);
			while(!d.tePassejantsDisponibles(Constants.COLORS[j]))j = rand.nextInt(Constants.COLORS.length);
			System.out.println(Constants.COLORS[j]);
			
			//Obtenim el districte on afegir el passejant
			System.out.println("Obtenim el districte on afegir el passejant");
			List<Integer> dAdjacents = Partida.getInstance().getDistrictesAdjacents(d);
			int k = rand.nextInt(dAdjacents.size());
			while(intents < maxIntents && !Partida.getInstance().potMoure(d.getNom(), districtes[k].getNom(), Constants.COLORS[j])){
				while(!d.tePassejantsDisponibles(Constants.COLORS[j]))j = rand.nextInt(Constants.COLORS.length);
				while(d.equals(districtes[k])) k = rand.nextInt(dAdjacents.size());
				++intents;
			}
			if (intents < maxIntents) {
				System.out.println("Obtingut");
				return new PassejantsAMoure(Constants.COLORS[j], d, districtes[k]);
			}
			++totalIntents;
		}
		System.out.println("NO obtingut");
		return getSolucioPassejantDistricte();
	}
	
	private PassejantsAMoure getSolucioPassejantDistricte(){
		Districte[] districtes = Partida.getInstance().getTauler().getDistrictes();
		for(int i = 0; i < districtes.length; ++i){
			if(districtes[i].tePassejantsDisponibles()){
				for(int k = 0; k < Constants.COLORS.length; ++k){
					if(districtes[i].tePassejantsDisponibles(Constants.COLORS[k])){
						for(int j = 0; j < districtes.length; ++j){
							System.out.println(districtes[i].getNom());
							if(i != j && Partida.getInstance().potMoure(districtes[i].getNom(), districtes[j].getNom(), Constants.COLORS[k])){
								return new PassejantsAMoure(Constants.COLORS[k], districtes[i], districtes[j]);
							}
						}
					}
				}
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
