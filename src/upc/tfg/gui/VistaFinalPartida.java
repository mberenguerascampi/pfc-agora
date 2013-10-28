package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;
import upc.tfg.utils.PuntuacionsBD;
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
	private CustomDefaultButton tornar;
	private VistaAmbBotoTornarListener listener;

	public VistaFinalPartida(final VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		setSize(Constants.width, Constants.height);
		tornar = new CustomDefaultButton("Tornar al menú principal");
		tornar.setBounds(200, Constants.height-200, 400, 200);
		tornar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.backButtonPressed();
			}
		});
		add(tornar);
	}
	
	public void setResultats(ResultatsFinals resultats, boolean finalPartida){
		removeAll();
		if(!finalPartida){
			afegeixBarraSuperior("Resultats provisionals", listener);
		}
		else{
			savePuntacio(resultats);
		}
		nomGuanyador = new JLabel();
		nomGuanyador.setText("<html> EL GUANYADOR HA ESTAT EL JUGADOR "+Partida.getInstance().getNomJugador(resultats.getIdJugadorGuanyador()) + "!!!</html>");
		nomGuanyador.setLayout(null);
		int originX = Constants.width/2-PlayerCell.CELL_WIDTH/2; 
		int originY = Constants.height/2-100/2;
		nomGuanyador.setFont(Constants.fontPlayerWinner);
		nomGuanyador.setBounds(originX, originY-80, PlayerCell.CELL_WIDTH, 40);
		nomGuanyador.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel nomJugadors = new JLabel("NOM");
		JLabel puntuacio = new JLabel("PUNTUACIÓ");
		nomJugadors.setBounds(originX, originY, PlayerCell.CELL_WIDTH, 35);
		puntuacio.setBounds(originX+240, originY, PlayerCell.CELL_WIDTH, 35);
		nomJugadors.setFont(Constants.fontPlayersNames);
		puntuacio.setFont(Constants.fontPlayersNames);
		add(nomJugadors);
		add(puntuacio);
		
		playerCell1 = new PlayerCell(Partida.getInstance().getNomJugador(1), resultats.getPuntsJ1());
		playerCell2 = new PlayerCell(Partida.getInstance().getNomJugador(2), resultats.getPuntsJ2());
		playerCell3 = new PlayerCell(Partida.getInstance().getNomJugador(3), resultats.getPuntsJ3());
		playerCell4 = new PlayerCell(Partida.getInstance().getNomJugador(4), resultats.getPuntsJ4());
		
		playerCell1.setBounds(originX, originY+PlayerCell.CELL_HEIGHT*1+30, PlayerCell.CELL_WIDTH, PlayerCell.CELL_HEIGHT);
		playerCell2.setBounds(originX, originY+PlayerCell.CELL_HEIGHT*2+30, PlayerCell.CELL_WIDTH, PlayerCell.CELL_HEIGHT);
		playerCell3.setBounds(originX, originY+PlayerCell.CELL_HEIGHT*3+30, PlayerCell.CELL_WIDTH, PlayerCell.CELL_HEIGHT);
		playerCell4.setBounds(originX, originY+PlayerCell.CELL_HEIGHT*4+30, PlayerCell.CELL_WIDTH, PlayerCell.CELL_HEIGHT);
		
		add(nomGuanyador);
		add(playerCell1);
		add(playerCell2);
		add(playerCell3);
		add(playerCell4);
		
		addSkin("imatgePortada.jpg");
		
	}
	
	private void savePuntacio(ResultatsFinals resultats){
		PuntuacionsBD bd = new PuntuacionsBD();
		bd.guardarPuntuacio(resultats);
	}
}
