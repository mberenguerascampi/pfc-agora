package upc.tfg.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.logic.ControladorLogic;
import upc.tfg.utils.Constants;

public class PlayerCell  extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3510270781971405841L;
	public static final int CELL_WIDTH = 410;
	public static final int CELL_HEIGHT = 35;
	private JLabel nomJugador;
	private JLabel puntsJugador;
	
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
