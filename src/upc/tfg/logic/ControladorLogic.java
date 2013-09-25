package upc.tfg.logic;

import upc.tfg.agora.Agora;
import upc.tfg.utils.Constants;

public class ControladorLogic {
	private Partida partida;
	private Agora agora;
	
	public ControladorLogic() {
		
	}
	
	public ControladorLogic(Agora agora) {
		this.agora = agora;
	}
	
	public void comencarPartida(){
		partida = new Partida("hola",null,1,2);
		Jugador j1 = new Jugador("J1",1,Constants.BLAU);
		Jugador j2 = new Jugador("J2",2,Constants.VERMELL);
		Jugador j3 = new Jugador("J3",3,Constants.VERD);
		Jugador j4 = new Jugador("J4",4,Constants.GROC);
		partida.afegirJugador(j1);
		partida.afegirJugador(j2);
		partida.afegirJugador(j3);
		partida.afegirJugador(j4);
		partida.crear();
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
					agora.treureCartaSeleccionada();
					agora.updateView();
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
			districteB.afegeixPassejant(p);
			
			if(partida.decrementaPassejantsAMoure()){
				//agora.treureCartaSeleccionada();
				agora.updateView();
			}
		}
	}
	
	public Carta[] getCartes(int numCartes){
		return partida.getBaralla().getCartes(numCartes);
	}
	
	public void cartaSeleccionada(Carta carta){
		partida.setPassejantsAMoure(carta.getValor());
		partida.setCartaSeleccionada(carta);
	}
	
	public void cartaRobada(int jugadorID, Carta cartaEntity){
		if(partida.cartaRobada(jugadorID, cartaEntity)){
			agora.intercanviaCartes();
		}
		agora.updateView();
	}
	
	public void cartaAgafadaDeLaBaralla(int jugadorID, int barallaID){
		if(barallaID == 1) agora.afegeixCartaAPosicioBuida(jugadorID, partida.getBaralla().getCartes(1)[0]);
		else agora.afegeixCartaAPosicioBuida(jugadorID, partida.getBaralla2().getCartes(1)[0]);
		partida.avancarJugador();
		agora.updateView();
	}
	
	public void divideixBaralla(){
		partida.divideixBaralla();
	}
}
