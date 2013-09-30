package upc.tfg.gui;

import javax.swing.JLabel;

import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;
import upc.tfg.utils.ResultatsFinals;

public class VistaFinalPartida extends DefaultView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2510642941389668576L;
	private JLabel nomGuanyador;
	private PlayerCell playerCell1;
	private PlayerCell playerCell2;
	private PlayerCell playerCell3;
	private PlayerCell playerCell4;

	public VistaFinalPartida() {
		setLayout(null);
		setSize(Constants.width, Constants.height);
	}
	
	public void setResultats(ResultatsFinals resultats){
		nomGuanyador = new JLabel();
		nomGuanyador.setText("Guanyador!!!");
		nomGuanyador.setLayout(null);
		int originX = Constants.width/2-100/2; 
		int originY = Constants.height/2-100/2;
		nomGuanyador.setBounds(originX, 0, 100, 10);
		
		playerCell1 = new PlayerCell(Partida.getInstance().getNomJugador(1), resultats.getPuntsJ1());
		playerCell2 = new PlayerCell(Partida.getInstance().getNomJugador(2), resultats.getPuntsJ2());
		playerCell3 = new PlayerCell(Partida.getInstance().getNomJugador(3), resultats.getPuntsJ3());
		playerCell4 = new PlayerCell(Partida.getInstance().getNomJugador(4), resultats.getPuntsJ4());
		
		playerCell1.setBounds(originX, originY+10, 100, 20);
		playerCell2.setBounds(originX, originY+30, 100, 20);
		playerCell3.setBounds(originX, originY+50, 100, 20);
		playerCell4.setBounds(originX, originY+70, 100, 20);
		
		add(nomGuanyador);
		add(playerCell1);
		add(playerCell2);
		add(playerCell3);
		add(playerCell4);
		
		addSkin("imatgePortada.jpg");
	}
}
