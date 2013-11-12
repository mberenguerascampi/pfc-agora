package upc.tfg.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import upc.tfg.utils.Constants;

public class PlayerCell  extends JPanel{
	/**
	 * Vista que representa un cel·la en la que es mostra el nom d'un jugador i la seva puntuació
	 */
	private static final long serialVersionUID = -3510270781971405841L;
	public static final int CELL_WIDTH = 410;
	public static final int CELL_HEIGHT = 35;
	private JLabel nomJugador;
	private JLabel puntsJugador;
	
	/**
	 * Constructora de la classe
	 * @param strNomJugador Nom del jugador
	 * @param intPuntsJugador Punts obtinguts pel jugador
	 */
	public PlayerCell(String strNomJugador, int intPuntsJugador) {
		setLayout(null);
		setOpaque(false);
		nomJugador = new JLabel(strNomJugador);
		nomJugador.setLayout(null);
		nomJugador.setFont(Constants.fontPlayersNames);
		nomJugador.setBounds(0, 0, 230, CELL_HEIGHT);
		
		puntsJugador = new JLabel(String.valueOf(intPuntsJugador));
		puntsJugador.setLayout(null);
		puntsJugador.setFont(Constants.fontPlayersNames);
		puntsJugador.setBounds(240, 0, 170, CELL_HEIGHT);
		
		add(nomJugador);
		add(puntsJugador);
	}
}
