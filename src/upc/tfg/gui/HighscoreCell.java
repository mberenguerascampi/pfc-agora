package upc.tfg.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import upc.tfg.utils.Constants;

public class HighscoreCell extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3804869214193693532L;
	public static final int CELL_WIDTH = 600;
	public static final int CELL_HEIGHT = 60;

	public HighscoreCell(String nomJugador, int punts, String data){
		setLayout(null);
		setOpaque(false);
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		setBorder(border);
		JLabel nomLabel = new JLabel(nomJugador);
		JLabel puntuacioLabel = new JLabel(String.valueOf(punts));
		JLabel dataLabel = new JLabel(data);
		
		nomLabel.setFont(Constants.fontPlayersNames);
		nomLabel.setBounds(20, 15, 280, 20);
		nomLabel.setAlignmentY(CENTER_ALIGNMENT);
		
		puntuacioLabel.setFont(Constants.fontPlayersNames);
		puntuacioLabel.setBounds(305, 15, 120, 20);
		puntuacioLabel.setAlignmentY(CENTER_ALIGNMENT);
		
		dataLabel.setFont(Constants.fontPlayersNames);
		dataLabel.setBounds(430, 15, 130, 20);
		dataLabel.setAlignmentY(CENTER_ALIGNMENT);
		
		add(nomLabel);
		add(puntuacioLabel);
		add(dataLabel);
	}
}
