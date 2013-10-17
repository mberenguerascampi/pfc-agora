package upc.tfg.logic;

import java.util.Date;

import upc.tfg.agora.Agora;
import upc.tfg.utils.Constants;
import upc.tfg.utils.PassejantsAMoure;
import upc.tfg.utils.ResultatsFinals;

public class ControladorLogic {
	private Partida partida;
	private Agora agora;
	private ControladorIA controladorIA;
	private PassejantsAMoure lastPAM;
	
	public ControladorLogic() {
		
	}
	
	public ControladorLogic(Agora agora) {
		this.agora = agora;
	}
	
	public void comencarPartida(){
		Date date = new Date();
		partida = new Partida("hola",date,1,1);
		Jugador j1 = new Jugador("J1",1,Constants.BLAU);
		Jugador j2 = new Jugador("J2",2,Constants.VERMELL);
		Jugador j3 = new Jugador("J3",3,Constants.VERD);
		Jugador j4 = new Jugador("J4",4,Constants.GROC);
		partida.afegirJugador(j1);
		partida.afegirJugador(j2);
		partida.afegirJugador(j3);
		partida.afegirJugador(j4);
		partida.crear();
		controladorIA = new ControladorIA(this);
		controladorIA.setJugadors(partida.getJugadorsRobot());
	}
	
	public void carregarPartida(Partida partida){
		this.partida = partida;
		controladorIA = new ControladorIA(this);
		controladorIA.setJugadors(partida.getJugadorsRobot());
	}
	
	public void guardarPartida(String nom){
		partida.guardar(nom);
	}
	
	public void mouPassejantADistricte(String nomDistricte, int idJugador){
		Jugador j = partida.getJugador(idJugador);
		if(j == null || j.getTotalPassejants() == 0)return;
		Passejant p = j.getUnPassejant();
		if(p == null){
			//TODO: Descartar carta
			return;
		}
		Districte[] districtes = partida.getTauler().getDistrictes();
		for(Districte districte:districtes){
			if(districte.getNom().equalsIgnoreCase(nomDistricte)){
				districte.afegeixPassejant(p);
				if(partida.decrementaPassejantsAMoure()){
					if(idJugador == 1){
						agora.treureCartaSeleccionada();
						agora.updateView();
						treuCarta(idJugador, partida.getCartaSeleccionada());
						getProximMoviment();
					}
				}
				return;
			}
		}
		
	}
	
	public void mouPassejantsEntreDistrictes(String nomDistricteA, String nomDistricteB, int color, boolean desfent){
		Districte districteA = partida.getDistricte(nomDistricteA);
		Districte districteB = partida.getDistricte(nomDistricteB);
		
		if(districteA != null && districteB != null){
			while(agora.isAnimationOn());
			if(partida.getIdJugadorActual() != 1)agora.mouPassejant(districteA, districteB, color);
			else lastPAM = new PassejantsAMoure(color, districteA, districteB);
			while(agora.isAnimationOn());
			Passejant p = districteA.removePassejant(color);
			if(!desfent)p.bloquejar();
			else p.desbloquejar();
			districteB.afegeixPassejant(p);
			
			if(partida.decrementaPassejantsAMoure()){
				getProximMoviment();
			}
			else agora.updateView();
		}
	}
	
	public Carta[] getCartes(int numCartes){
		return partida.getBaralla().getCartes(numCartes);
	}
	
	public void cartaSeleccionada(Carta carta, int jugadorID){
		Jugador j = partida.getJugador(jugadorID);
		partida.setPassejantsAMoure(Math.min(j.getTotalPassejants(), carta.getValor()));
		if(partida.getPassejantsAMoure() == 0){
			if(jugadorID != 1){
				carta.girar();
				agora.seleccionaCartaiMouPassejants(jugadorID, carta);
			}
			treuCarta(jugadorID, carta);
			partida.avancarJugador();
			getProximMoviment();
		}
		else if(jugadorID == 1){
			partida.setCartaSeleccionada(carta);
			System.out.println("-----------------" + partida.getPassejantsAMoure());
		}
		else{
			//Actualitzam la capa de domini
			carta.girar();
			for(int i = 0; i < carta.getValor(); ++i){
				mouPassejantADistricte(carta.getDistricte().getNom(), partida.getIdJugadorActual());
			}
			treuCarta(jugadorID, carta);
			//Actualitzem la capa de presentacio
			agora.seleccionaCartaiMouPassejants(jugadorID, carta);
			getProximMoviment();
		}
	}
	
	public void cartaRobada(int jugadorID, Carta cartaEntity){
		System.out.println("LOGIC: Carta robada del jugador"+jugadorID);
		if(jugadorID != 1){
			agora.seleccionaCartaPerRobar(jugadorID, cartaEntity);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(partida.cartaRobada(jugadorID, cartaEntity)){
			agora.intercanviaCartes();
		}
		agora.updateView();
		getProximMoviment();
	}
	
	public void cartaAgafadaDeLaBaralla(int jugadorID, int barallaID){
		partida.avancarJugador();
		Carta cartaAgafada = null;
		if (barallaID == -1){
			getProximMoviment();
		}
		else{
			if(barallaID == 1) {
				cartaAgafada = partida.getBaralla().getCartes(1)[0];
			}
			else if (barallaID == 2){
				cartaAgafada = partida.getBaralla2().getCartes(1)[0];
			}
			afegeixCarta(jugadorID, cartaAgafada);
			agora.afegeixCartaAPosicioBuida(jugadorID, cartaAgafada);
			agora.updateView();
			getProximMoviment();
		}
	}
	
	public void divideixBaralla(){
		partida.divideixBaralla();
	}
	
	public void afegeixCarta(int jugadorID, Carta carta){
		partida.afegeixCarta(jugadorID, carta);
	}
	
	public void treuCarta(int jugadorID, Carta carta){
		partida.treuCarta(jugadorID, carta);
	}
	
	public void getProximMoviment(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//Si estem en un jugador diferent a l'1 li demenem a la inteligència artificial el següent moviment
				//Sino esperem a que l'usuari tiri
				System.out.println("Get Proxim moviment");
				agora.updateView();
				if (partida.finalitzarPartida()){
					ResultatsFinals resultats = partida.getPuntuacioFinal();
					agora.mostraFinalPartida(resultats);
					return;
				}
				if (partida.getIdJugadorActual() == 1) return;
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				controladorIA.getProximMoviment(partida.getIdJugadorActual(), partida.getPas());
			}
		});
		t.start();
	}
	
	public void desfesJugada(){
		int passejantsATreure = partida.desfesJugada();
		if(passejantsATreure > 0 && Partida.getInstance().getPas() == 3){
			mouPassejantsEntreDistrictes(lastPAM.districteDesti.getNom(), lastPAM.districteOrigen.getNom(), lastPAM.color, true);
		}
		else if (passejantsATreure > 0){
			for(int i = 0; i < passejantsATreure; ++i){
				Jugador jAux = partida.getJugador(partida.getIdJugadorActual());
				retornaPassejantAJugador(jAux, partida.getCartaSeleccionada().getDistricte(), jAux.getColor());
			}
			agora.deseleccionaCarta();
			agora.afegeixPassejants(partida.getIdJugadorActual());
			agora.updateView();
		}
	}
	
	public void retornaPassejantAJugador(Jugador j, Districte d, int color){
		Passejant p = d.removePassejant(color);
		j.afegeixUnPassejant(p);
	}
}
