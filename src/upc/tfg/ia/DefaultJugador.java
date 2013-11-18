package upc.tfg.ia;

import java.util.ArrayList;

import upc.tfg.logic.Carta;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;
import upc.tfg.logic.Passejant;
import upc.tfg.utils.Constants;
import upc.tfg.utils.PassejantsAMoure;

/**
 * Classe per defecte d'un jugador robot que conté funcions auxiliars
 * @author Marc
 *
 */
public class DefaultJugador {
	
	public DefaultJugador() {
	}
	
	/**
	 * Mètode que ens permet conèixer si un possible moviment és una soolució o no
	 * @param districtes Array amb els districtes
	 * @param dOrigin Districte d'on volem treure un Passejant
	 * @param dFi Districte on volem afegir el passejant
	 * @param color Color del passejant que volem moure
	 * @return True si és solució, false en cas contrari
	 */
	public boolean teSolucioEnFutur(Districte[] districtes, Districte dOrigin, Districte dFi, int color){
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
	
	/**
	 * Funció que ens retorna una solució segura per moure un passejant entre districtes
	 * @param districtes
	 * @param futur
	 * @return
	 */
	public PassejantsAMoure getSolucioPassejantDistricte(Districte[] districtes, boolean futur){
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
	
	/**
	 * Funció que retorna els possibles moviments de passejants que pot fer un jugador en un moment determinat
	 * @return una llista que descriu els possibles moviments
	 */
	public ArrayList<PassejantsAMoure> getPossiblesPassejantsAMoure(){
		Districte[] districtes = Partida.getInstance().getDistrictes();
		ArrayList<PassejantsAMoure> pams = new ArrayList<PassejantsAMoure>();
		//Obtenim el llistat de possibles solucions
		for(int i = 0; i < districtes.length; ++i){
			if(districtes[i].tePassejantsDisponibles()){
				for(int k = 0; k < Constants.COLORS.length; ++k){
					if(districtes[i].tePassejantsDisponibles(Constants.COLORS[k])){
						for(int j = 0; j < districtes.length; ++j){
							System.out.println(districtes[i].getNom());
							if(i != j && Partida.getInstance().potMoure(districtes[i], districtes[j], Constants.COLORS[k])){
								if(teSolucioEnFutur(districtes, districtes[i], districtes[j], Constants.COLORS[k])){
									pams.add(new PassejantsAMoure(Constants.COLORS[k], districtes[i], districtes[j]));
								}
							}
						}
					}
				}
			}
		}
		return pams;
	}
	
	/**
	 * Funció que retorna les cartes que poden ser seleccionades
	 * @return Un llista amb totes les cartes que poden ser seleccionades
	 */
	public ArrayList<Carta> getPossiblesCartes(Jugador jugador){
		ArrayList<Carta> cartes = jugador.getCartes();
		ArrayList<Carta> solucio = new ArrayList<Carta>();
		
		//Comprovem que es compleixen les solucions necessàries
		if (cartes.size() == 0) return null;
		if(!Partida.getInstance().potSeleccionarAlgunaCarta(jugador.getId()))return null;
		
		//Agafem les possibles solucions
		for(int i = 0; i < cartes.size(); ++i){
			int numPassejantsAMoure = Math.min(jugador.getTotalPassejants(), cartes.get(i).getValor());
			if(!Partida.getInstance().hiHauraDistricteAmbMateixNombrePassejants(numPassejantsAMoure, cartes.get(i).getDistricte(), jugador.getColor())){
				solucio.add(cartes.get(i));
			}
		}
		return solucio;
	}
	
	public int[] getPossibleJugadorInici(){
		int[] possiblesJugadors = new int[3];
		int index = 0;
		for(int i = 1; i<=4; ++i){
			if(i != Partida.getInstance().getIdJugadorInici()){
				possiblesJugadors[index] = i;
				++index;
			}
		}
		return possiblesJugadors;
	}
}
