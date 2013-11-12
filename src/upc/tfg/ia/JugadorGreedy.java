package upc.tfg.ia;

import java.util.ArrayList;

import upc.tfg.interfaces.JugadorRobot;
import upc.tfg.logic.Carta;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;
import upc.tfg.logic.Passejant;
import upc.tfg.utils.PassejantsAMoure;
import upc.tfg.utils.ResultatsFinals;

public class JugadorGreedy extends DefaultJugador implements JugadorRobot{
	private Jugador jugador;
	
	public JugadorGreedy(Jugador jugador) {
		this.jugador = jugador;
	}

	@Override
	public Carta getCartaRival() {
		int idJugadorAnterior = jugador.getId()-1;
		if(idJugadorAnterior == 0)idJugadorAnterior = 4;
		ArrayList<Carta> cartes = Partida.getInstance().getJugador(idJugadorAnterior).getCartes();
		Carta cartaARetornar = null;
		int max = Integer.MIN_VALUE;
		for(Carta carta:cartes){
			int h = getHeuristicaPas1(carta);
			if(h > max){
				max = h;
				cartaARetornar = carta;
			}
		}
		return cartaARetornar;
	}

	@Override
	public Carta getCartaSeleccionada() {
		ArrayList<Carta> cartes = getPossiblesCartes(jugador);
		if(cartes == null) return null;
		
		//Entre les solucions calculem la millor utilitzant la funció heurística
		Carta cartaARetornar = null;
		int max = Integer.MIN_VALUE;
		for(Carta c:cartes){
			int h = getHeuristicaPas2(c);
			if(h > max){
				max = h;
				cartaARetornar = c;
			}
		}
		return cartaARetornar;
	}

	@Override
	public PassejantsAMoure getPassejantDistricte() {
		ArrayList<PassejantsAMoure> possiblesPam = getPossiblesPassejantsAMoure();
		if(possiblesPam == null || possiblesPam.size() == 0)return null;
		PassejantsAMoure solucio = null;
		int max = Integer.MIN_VALUE;
		for(PassejantsAMoure pam:possiblesPam){
			int h = getHeuristicaPas3(pam);
			if(h > max){
				max = h;
				solucio = pam;
			}
		}
		return solucio;
	}

	@Override
	public int getBarallaPerRobarCarta() {
		Districte db1 = Partida.getInstance().getBaralla().getCartes().get(0).getDistricte();
		Districte db2 = Partida.getInstance().getBaralla2().getCartes().get(0).getDistricte();
		if(db1.getNumPassejants(jugador.getColor()) > db2.getNumPassejants(jugador.getColor()))return 1;
		else return 2;
	}
	
	private int getHeuristicaPas1(Carta carta){
		Districte[] districtes = Partida.getInstance().getDistrictes();
		Districte[] districtesModif = new Districte[districtes.length];
		Passejant p = new Passejant(jugador.getColor(), false);
		//Fem un còpia dels districtes per poder-la modificar sense perdre els valors originals
		int i = 0;
		for(Districte d:districtes){
			districtesModif[i] = new Districte(d);
			++i;
		}
		//Per cada carta de cada jugador afegim dos passejants al corresponent districte
		//En el cas del jugador actual afegim el valor real de la carta
		for(int j = 1; j <= 4; ++j){
			Jugador iJugador = Partida.getInstance().getJugador(j);
			if(j != jugador.getId()){
				for(Carta c:iJugador.getCartes()){
					if(!c.equals(carta)){
						afegeixPassejantTempDistricte(districtesModif, c.getDistricte(), new Passejant(iJugador.getColor(), true));
						afegeixPassejantTempDistricte(districtesModif, c.getDistricte(), new Passejant(iJugador.getColor(), true));
					}
				}
			}
			else{
				for(Carta c:iJugador.getCartes()){
					for(int k = 0; k < c.getValor(); ++k){
						afegeixPassejantTempDistricte(districtesModif, c.getDistricte(), new Passejant(iJugador.getColor(), true));
					}
				}
				//Afegim dos passejants a la possible carta a agafar
				afegeixPassejantTempDistricte(districtesModif, carta.getDistricte(), new Passejant(iJugador.getColor(), true));
				afegeixPassejantTempDistricte(districtesModif, carta.getDistricte(), new Passejant(iJugador.getColor(), true));
			}
		}
		ResultatsFinals res = Partida.getInstance().getPuntuacioFinal(districtesModif);
		return res.getPunts(jugador.getId())-res.getMaxPunts(jugador.getId());
	}
	
	private void afegeixPassejantTempDistricte(Districte[] districtes, Districte d, Passejant p){
		for(Districte districte:districtes){
			if(districte.getNom().equalsIgnoreCase(d.getNom())){
				districte.afegeixPassejant(p);
			}
		}
	}
	
	private int getHeuristicaPas2(Carta carta){
		Districte[] districtes = Partida.getInstance().getDistrictes();
		Districte[] districtesModif = new Districte[districtes.length];
		Passejant p = new Passejant(jugador.getColor(), false);
		int i = 0;
		for(Districte d:districtes){
			if(d.equals(carta.getDistricte())){
				Districte districteAAfegir = new Districte(d);
				districteAAfegir.afegeixPassejant(p);
				districtesModif[i] = districteAAfegir;
			}
			else {
				districtesModif[i] = d;
			}
			++i;
		}
		ResultatsFinals res = Partida.getInstance().getPuntuacioFinal(districtesModif);
		return res.getPunts(jugador.getId())-res.getMaxPunts(jugador.getId());
	}
	
	private int getHeuristicaPas3(PassejantsAMoure pam){
		Districte[] districtes = Partida.getInstance().getDistrictes();
		Districte[] districtesModif = new Districte[districtes.length];
		
		//Creem la solucio proposada
		Districte dist1 = new Districte(pam.districteOrigen);
		Districte dist2 = new Districte(pam.districteDesti);
		dist2.afegeixPassejant(dist1.removePassejant(pam.color));
		//Afegim els districtes modificats respecte a l'actual estat en la possible sol·lució
		int i = 0;
		for(Districte d:districtes){
			if(d.getNom().equalsIgnoreCase(dist1.getNom())){
				districtesModif[i] = dist1;
			}
			else if(d.getNom().equalsIgnoreCase(dist2.getNom())){
				districtesModif[i] = dist2;
			}
			else {
				districtesModif[i] = d;
			}
			++i;
		}
		ResultatsFinals res = Partida.getInstance().getPuntuacioFinal(districtesModif);
		return res.getPunts(jugador.getId())-res.getMaxPunts(jugador.getId());
	}

}
