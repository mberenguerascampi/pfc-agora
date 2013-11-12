package upc.tfg.ia;

import java.util.ArrayList;
import java.util.Random;

import upc.tfg.interfaces.JugadorRobot;
import upc.tfg.logic.Carta;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;
import upc.tfg.utils.PassejantsAMoure;

public class JugadorRegles extends DefaultJugador implements JugadorRobot{
	private Jugador jugador;
	private final int NUM_REGLES_PAS1 = 5;
	private final int NUM_REGLES_PAS2 = 4;
	private final int NUM_REGLES_PAS3 = 7;
	
	public JugadorRegles(Jugador jugador) {
		this.jugador = jugador;
	}

	@Override
	public Carta getCartaRival() {
		int idJugadorAnterior = jugador.getId()-1;
		if(idJugadorAnterior == 0)idJugadorAnterior = 4;
		ArrayList<Carta> cartes = Partida.getInstance().getJugador(idJugadorAnterior).getCartes();
		Carta cartaARetornar = null;
		ArrayList<Districte>districtesGuanyats = Partida.getInstance().getDistrictesGuanyats(jugador.getId());
		ArrayList<Districte>districtesGuanyatsJugEsq = Partida.getInstance().getDistrictesGuanyats(idJugadorAnterior);
		int numRegla = 1;
		while(numRegla <= NUM_REGLES_PAS1 && cartaARetornar == null){
			for (Carta c:cartes){
				switch(numRegla){
					case 1:
						if(districtesGuanyats != null && districtesGuanyats.contains(c.getDistricte()) &&
								c.getDistricte().equals(getDistrictMaxValue(districtesGuanyats))){
							cartaARetornar = c;
						}
						break;
					case 2:
						if(districtesGuanyats != null && districtesGuanyats.contains(c.getDistricte())){
							cartaARetornar = c;
						}
						break;
					case 3:
						if(districtesGuanyatsJugEsq != null && 
								districtesGuanyatsJugEsq.contains(c.getDistricte()) &&
								c.getDistricte().equals(getDistrictMaxValue(districtesGuanyatsJugEsq))){
							cartaARetornar = c;
						}
						break;
					case 4:
						if(districtesGuanyatsJugEsq != null && 
								districtesGuanyatsJugEsq.contains(c.getDistricte())){
							cartaARetornar = c;
						}
						break;
					case 5:
						if(c.getDistricte().equals(getDistrictMaxValue(Partida.getInstance().getDistrictes()))){
							cartaARetornar = c;
						}
						break;
				}
			}
			++numRegla;
		}
		return cartaARetornar;
	}

	@Override
	public Carta getCartaSeleccionada() {
		ArrayList<Carta> cartes = getPossiblesCartes(jugador);
		if(cartes == null) return null;
		
		//Entre les solucions calculem la millor a partir del sistema de regles establert
		Carta cartaARetornar = null;
		ArrayList<Districte>districtesGuanyats = Partida.getInstance().getDistrictesGuanyats(jugador.getId());
		int numRegla = 1;
		while(numRegla <= NUM_REGLES_PAS2 && cartaARetornar == null){
			if(numRegla == 1 && Partida.getInstance().getTorn() <= 2){
				cartaARetornar = getCartaMaxDistrictValue(cartes);
				return cartaARetornar;
			}
			if(numRegla == 4){
				cartaARetornar = getCartaMinValue(cartes);
				return cartaARetornar;
			}
			for (Carta c:cartes){
				switch(numRegla){
					case 2:
						if(districtesGuanyats != null && districtesGuanyats.contains(c.getDistricte()) && 
								difRespecteGuanyador(c.getDistricte()) < 3 &&
								(Partida.getInstance().getTorn() > 7 || c.getValor() < 4)){
							cartaARetornar = c;
						}
						break;
					case 3:
						if(esGuanyador(c.getDistricte(), c.getValor()) &&
								(Partida.getInstance().getTorn() > 7 || c.getValor() < 4)){
							cartaARetornar = c;
						}
						break;
				}
			}
			++numRegla;
		}
		return cartaARetornar;
	}

	@Override
	public PassejantsAMoure getPassejantDistricte() {
		ArrayList<PassejantsAMoure> possiblesPam = getPossiblesPassejantsAMoure();
		if(possiblesPam == null || possiblesPam.size() == 0)return null;
		PassejantsAMoure solucio = null;
		ArrayList<Districte>districtesGuanyats = Partida.getInstance().getDistrictesGuanyats(jugador.getId());
		int idJugadorGuanyador = Partida.getInstance().getPuntuacioFinal().getIdJugadorGuanyador(jugador.getId());
		ArrayList<Districte>districtesGuanyatsPrimerJugador = Partida.getInstance().getDistrictesGuanyats(idJugadorGuanyador);
		int numRegla = 1;
		//TODO: Falta implementar regla 6
		while(numRegla <= NUM_REGLES_PAS3-1 && solucio == null){
			if(numRegla == NUM_REGLES_PAS3-1){
				Random rand = new Random(System.currentTimeMillis());
				return possiblesPam.get(rand.nextInt(possiblesPam.size()));
			}
			for (PassejantsAMoure pam:possiblesPam){
				switch(numRegla){
					case 1:
						if(Partida.getInstance().getTorn() <= 2 &&
								pam.districteDesti.getValor() < pam.districteOrigen.getValor() &&
								pam.color != jugador.getColor()){
							solucio = pam;
							return pam;
						}
						break;
					case 2:
						if(districtesGuanyats != null && districtesGuanyats.contains(pam.districteDesti) &&
								difRespecteGuanyador(pam.districteDesti) < 3 &&
								!districtesGuanyats.contains(pam.districteOrigen) &&
								pam.color == jugador.getColor()){
							
							solucio = pam;
						}
						break;
					case 3:
						if(districtesGuanyats != null && districtesGuanyats.contains(pam.districteOrigen) &&
								difRespecteGuanyador(pam.districteOrigen) < 3 &&
								!districtesGuanyats.contains(pam.districteDesti) &&
								pam.color != jugador.getColor()){
							
							solucio = pam;
						}
						break;
					case 4:
						if(districtesGuanyats != null && !districtesGuanyats.contains(pam.districteOrigen) &&
								(esGuanyador(pam.districteDesti, 1) || empat(pam.districteDesti, 1)) &&
								pam.color == jugador.getColor()){
							
							solucio = pam;
						}
						break;
					case 5:
						if(districtesGuanyatsPrimerJugador != null && 
								districtesGuanyatsPrimerJugador.contains(pam.districteOrigen) &&
								!districtesGuanyatsPrimerJugador.contains(pam.districteDesti) &&
								pam.color == Partida.getInstance().getJugador(idJugadorGuanyador).getColor()){
							
							solucio = pam;
						}
						break;
				}
			}
			++numRegla;
		}
		return solucio;
	}

	@Override
	public int getBarallaPerRobarCarta() {
		ArrayList<Districte>districtesGuanyats = Partida.getInstance().getDistrictesGuanyats(jugador.getId());
		Districte db1 = Partida.getInstance().getBaralla().getCartes().get(0).getDistricte();
		Districte db2 = Partida.getInstance().getBaralla2().getCartes().get(0).getDistricte();
		if(Partida.getInstance().getTorn() <= 2 && db1.getValor() >= db2.getValor()){
			return 1;
		}
		if(Partida.getInstance().getTorn() <= 2 && db1.getValor() < db2.getValor()){
			return 2;
		}
		if(districtesGuanyats.contains(db1) && difRespecteGuanyador(db1) < 3){
			return 1;
		}
		if(districtesGuanyats.contains(db2) && difRespecteGuanyador(db2) < 3){
			return 2;
		}
		if(db1.getNumPassejants(jugador.getColor()) >= db2.getNumPassejants(jugador.getColor())){
			return 1;
		}
		if(db1.getNumPassejants(jugador.getColor()) < db2.getNumPassejants(jugador.getColor())){
			return 2;
		}
		return 0;
	}
	
	private Districte getDistrictMaxValue(ArrayList<Districte>districtes){
		int maxValue = Integer.MIN_VALUE;
		Districte distARetornar = null;
		for(Districte d:districtes){
			if(d.getValor() > maxValue){
				maxValue = d.getValor();
				distARetornar = d;
			}
		}
		return distARetornar;
	}
	
	private Districte getDistrictMaxValue(Districte[] districtes){
		ArrayList<Districte>districtesList = new ArrayList<Districte>();
		for(Districte d:districtes){
			districtesList.add(d);
		}
		return getDistrictMaxValue(districtesList);
	}
	
	private Carta getCartaMaxDistrictValue(ArrayList<Carta> cartes) {
		Carta cartaRet = null;
		int max = Integer.MIN_VALUE;
		//Busquem la carta del districte amb més valor i amb menys de 4 com a valor
		for(Carta c:cartes){
			if(c.getValor() < 4){
				if(c.getDistricte().getValor() > max){
					max = c.getDistricte().getValor();
					cartaRet = c;
				}
			}
		}
		//Si no hem trobat cap carta doncs tornem la carta amb el districte amb més puntuació sense 
		//tenir en compte le valor de la carta
		if(cartaRet == null){
			for(Carta c:cartes){
				if(c.getDistricte().getValor() > max){
					max = c.getDistricte().getValor();
					cartaRet = c;
				}
			}
		}
		return cartaRet;
	}
	
	private int difRespecteGuanyador(Districte districte) {
		return districte.getDiferenciaRespecteGuanyador();
	}
	
	private boolean esGuanyador(Districte districte, int valor) {
		Districte dCopy = new Districte(districte);
		dCopy.inicialitzaIAfefeixPassejants(jugador.getColor(), valor, 0);
		return (dCopy.getColorGuanyador() == jugador.getColor());
	}
	
	private boolean empat(Districte districte, int valor) {
		Districte dCopy = new Districte(districte);
		dCopy.inicialitzaIAfefeixPassejants(jugador.getColor(), valor, 0);
		return(dCopy.getNumPassejants(dCopy.getColorGuanyador()) - dCopy.getNumPassejants(jugador.getColor()) == 0);
	}
	
	private Carta getCartaMinValue(ArrayList<Carta> cartes) {
		int max = Integer.MIN_VALUE;
		Carta cartaRet = null;
		for(Carta c:cartes){
			if(c.getValor() > max){
				max = c.getValor();
				cartaRet = c;
			}
		}
		return cartaRet;
	}

}
