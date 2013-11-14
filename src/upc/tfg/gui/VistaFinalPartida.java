package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;
import upc.tfg.utils.PuntuacionsBD;
import upc.tfg.utils.ResultatsFinals;

public class VistaFinalPartida extends DefaultView {

	/**
	 * Vista per mostrar les puntuacions en una partida
	 */
	private static final long serialVersionUID = -2510642941389668576L;
	private JLabel nomGuanyador;
	private PlayerCell playerCell1;
	private PlayerCell playerCell2;
	private PlayerCell playerCell3;
	private PlayerCell playerCell4;
	private CustomDefaultButton tornar;
	private VistaAmbBotoTornarListener listener;

	/**
	 * Constructora de la classe
	 * @param listener Listener de la classe
	 */
	public VistaFinalPartida(final VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		setSize(Constants.width, Constants.height);
	}
	
	/**
	 * Mostra per pantalla els resultats que se li passen com a paràmetre
	 * @param resultats Les puntuacions de cada jugador
	 * @param finalPartida Boolean que indica si són els resultats provisionals o finals
	 */
	public void setResultats(ResultatsFinals resultats, boolean finalPartida){
		removeAll();
		if(!finalPartida){
			afegeixBarraSuperior("Resultats provisionals", listener);
		}
		else{
			savePuntacio(resultats);
			tornar = new CustomDefaultButton("Tornar al menú principal");
			tornar.setBounds(110, Constants.height-200, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_WIDTH);
			tornar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					listener.backButtonPressed();
				}
			});
			add(tornar);
			
			CustomDefaultButton veureTauler = new CustomDefaultButton("Veure tauler");
			veureTauler.setBounds((int)(Constants.width*0.75-110-CustomDefaultButton.BUTTON_WIDTH), Constants.height-200, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_WIDTH);
			veureTauler.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					listener.mostraSituacioFinalTauler();
				}
			});
			add(veureTauler);
		}
		nomGuanyador = new JLabel();
		nomGuanyador.setText("<html>GUANYADOR: "+Partida.getInstance().getNomJugador(resultats.getIdJugadorGuanyador()) + "!!!</html>");
		nomGuanyador.setLayout(null);
		int originX = Constants.width/2-Constants.width/10-PlayerCell.CELL_WIDTH/2; 
		int originY = Constants.height/2-70/2;
		nomGuanyador.setFont(Constants.fontPlayerWinner);
		nomGuanyador.setBounds(originX-50, originY-80, PlayerCell.CELL_WIDTH+100, 80);
		nomGuanyador.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel nomJugadors = new JLabel("NOM");
		JLabel puntuacio = new JLabel("PUNTUACIÓ");
		nomJugadors.setBounds(originX, originY+10, PlayerCell.CELL_WIDTH, 35);
		puntuacio.setBounds(originX+240, originY+10, PlayerCell.CELL_WIDTH, 35);
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
		
		addSkin("backgroundWithWhiteBox.png");
		
	}
	
	private void savePuntacio(ResultatsFinals resultats){
		PuntuacionsBD bd = new PuntuacionsBD();
		bd.guardarPuntuacio(resultats);
	}
}
