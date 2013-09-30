package upc.tfg.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerCell  extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3510270781971405841L;
	private JLabel nomJugador;
	private JLabel puntsJugador;
	
	public PlayerCell(String strNomJugador, int intPuntsJugador) {
		setLayout(null);
		nomJugador = new JLabel(strNomJugador);
		nomJugador.setLayout(null);
		nomJugador.setBounds(0, 0, 100, 10);
		
		puntsJugador = new JLabel(String.valueOf(intPuntsJugador));
		puntsJugador.setLayout(null);
		puntsJugador.setBounds(110, 0, 100, 10);
		
		add(nomJugador);
		add(puntsJugador);
	}
}
