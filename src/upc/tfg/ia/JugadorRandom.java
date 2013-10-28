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
import upc.tfg.logic.Passejant;
import upc.tfg.utils.Constants;

public class JugadorRandom implements JugadorRobot{
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
		ArrayList<Carta> cartes = jugador.getCartes();
		Random rand = new Random(System.currentTimeMillis());
		if(cartes.size() == 0)return null;
		if(!Partida.getInstance().potSeleccionarAlgunaCarta(jugador.getId()))return null;
		int i = rand.nextInt(cartes.size());
		int numPassejantsAMoure = Math.min(jugador.getTotalPassejants(), cartes.get(i).getValor());
		while(Partida.getInstance().hiHauraDistricteAmbMateixNombrePassejants(numPassejantsAMoure, cartes.get(i).getDistricte(), jugador.getColor())){
			i = rand.nextInt(cartes.size());
			numPassejantsAMoure = Math.min(jugador.getTotalPassejants(), cartes.get(i).getValor());
		}
		return cartes.get(i);
	}
	
	public PassejantsAMoure getPassejantDistricte(){
		if(Partida.getInstance().getTorn() == Partida.getInstance().getUltimTorn()){
			return getPassejantUltimTorn();
		}
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
			while(!districtes[i].tePassejantsDisponibles() && intents < maxIntents) {
				i = rand.nextInt(districtes.length);
				++intents;
			}
			if(intents < maxIntents){
				intents = 0;
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
					if(teSolucioEnFutur(districtes, d, districtes[k], Constants.COLORS[j]))return new PassejantsAMoure(Constants.COLORS[j], d, districtes[k]);
				}
			}
			++totalIntents;
		}
		System.out.println("NO obtingut");
		return getSolucioPassejantDistricte(districtes, false);
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
	
	private boolean teSolucioEnFutur(Districte[] districtes, Districte dOrigin, Districte dFi, int color){
		int passejantsAMoure = Partida.getInstance().getPassejantsAMoure();
		System.out.println("Abans de retornar true");
		if(passejantsAMoure == 1)return true;
		else {
			Partida.getInstance().setPassejantsAMoure(1);
		}
		Districte[] districtesFutur = new Districte[districtes.length];
		Passejant p = null;
		Districte dAux = null;
		System.out.println("Abans de crear districtes auxiliars");
		for(int i = 0; i < districtes.length; ++i){
			if(districtes[i].equals(dOrigin)){
				Districte aux = new Districte(dOrigin);
				p = aux.removePassejant(color);
				districtesFutur[i] = aux;
			}
			else if(districtes[i].equals(dFi)){
				dAux = new Districte(dFi);
				districtesFutur[i] = dAux;
			}
			else{
				districtesFutur[i] = districtes[i];
			}
		}
		dAux.afegeixPassejant(p);
		System.out.println("Abans de buscar solucio");
		if (getSolucioPassejantDistricte(districtesFutur, true) != null){
			System.out.println("Abans de retornar true2");
			Partida.getInstance().setPassejantsAMoure(passejantsAMoure);
			return true;
		}
		else{
			System.out.println("Abans de retornar false");
			Partida.getInstance().setPassejantsAMoure(passejantsAMoure);
			return false;
		}
	}
	
	private PassejantsAMoure getSolucioPassejantDistricte(Districte[] districtes, boolean futur){
		for(int i = 0; i < districtes.length; ++i){
			if(districtes[i].tePassejantsDisponibles()){
				for(int k = 0; k < Constants.COLORS.length; ++k){
					if(districtes[i].tePassejantsDisponibles(Constants.COLORS[k])){
						for(int j = 0; j < districtes.length; ++j){
							System.out.println(districtes[i].getNom());
							if(i != j && Partida.getInstance().potMoure(districtes[i], districtes[j], Constants.COLORS[k])){
								if(futur || teSolucioEnFutur(districtes, districtes[i], districtes[j], Constants.COLORS[k])){
									return new PassejantsAMoure(Constants.COLORS[k], districtes[i], districtes[j]);
								}
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
