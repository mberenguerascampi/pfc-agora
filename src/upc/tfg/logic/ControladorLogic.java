package upc.tfg.logic;

import upc.tfg.agora.Agora;
import upc.tfg.utils.Constants;

public class ControladorLogic {
	private Partida partida;
	private Agora agora;
	private ControladorIA controladorIA;
	
	public ControladorLogic() {
		
	}
	
	public ControladorLogic(Agora agora) {
		this.agora = agora;
		controladorIA = new ControladorIA(this);
	}
	
	public void comencarPartida(){
		partida = new Partida("hola",null,1,2, this);
		Jugador j1 = new Jugador("J1",1,Constants.BLAU);
		Jugador j2 = new Jugador("J2",2,Constants.VERMELL);
		Jugador j3 = new Jugador("J3",3,Constants.VERD);
		Jugador j4 = new Jugador("J4",4,Constants.GROC);
		partida.afegirJugador(j1);
		partida.afegirJugador(j2);
		partida.afegirJugador(j3);
		partida.afegirJugador(j4);
		partida.crear();
		controladorIA.setJugadors(partida.getJugadorsRobot());
	}
	
	public void mouPassejantADistricte(String nomDistricte, int idJugador){
		Jugador j = partida.getJugador(idJugador);
		if(j == null)return;
		Passejant p = j.getUnPassejant();
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
	
	public void mouPassejantsEntreDistrictes(String nomDistricteA, String nomDistricteB, int color){
		Districte districteA = partida.getDistricte(nomDistricteA);
		Districte districteB = partida.getDistricte(nomDistricteB);
		
		if(districteA != null && districteB != null){
			Passejant p = districteA.removePassejant(color);
			p.bloquejar();
			districteB.afegeixPassejant(p);
			
			if(partida.decrementaPassejantsAMoure()){
				agora.updateView();
				getProximMoviment();
			}
		}
	}
	
	public Carta[] getCartes(int numCartes){
		return partida.getBaralla().getCartes(numCartes);
	}
	
	public void cartaSeleccionada(Carta carta, int jugadorID){
		partida.setPassejantsAMoure(carta.getValor());
		if(jugadorID == 1){
			partida.setCartaSeleccionada(carta);
		}
		else{
			//Actualitzam la capa de domini
			carta.girar();
			for(int i = 0; i < carta.getValor(); ++i){
				mouPassejantADistricte(carta.getDistricte().getNom(), partida.getIdJugadorActual());
			}
			//Actualitzem la capa de presentacio
			agora.seleccionaCartaiMouPassejants(jugadorID, carta);
			getProximMoviment();
		}
	}
	
	public void cartaRobada(int jugadorID, Carta cartaEntity){
		if(partida.cartaRobada(jugadorID, cartaEntity)){
			agora.intercanviaCartes();
		}
		agora.updateView();
	}
	
	public void cartaAgafadaDeLaBaralla(int jugadorID, int barallaID){
		if(barallaID == 1) {
			Carta cartaAgafada = partida.getBaralla().getCartes(1)[0];
			agora.afegeixCartaAPosicioBuida(jugadorID, cartaAgafada);
			afegeixCarta(jugadorID, cartaAgafada);
		}
		else {
			Carta cartaAgafada = partida.getBaralla2().getCartes(1)[0];
			agora.afegeixCartaAPosicioBuida(jugadorID, cartaAgafada);
			afegeixCarta(jugadorID, cartaAgafada);
		}
		partida.avancarJugador();
		agora.updateView();
		getProximMoviment();
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
		//Si estem en un jugador diferent a l'1 li demenem a la inteligència artificial el següent moviment
		//Sino esperem a que l'usuari tiri
		agora.updateView();
		if (partida.getIdJugadorActual() == 1) return;
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		controladorIA.getProximMoviment(partida.getIdJugadorActual(), partida.getPas());
	}
}
