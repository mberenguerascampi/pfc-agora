package upc.tfg.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import upc.tfg.interfaces.CellListener;
import upc.tfg.utils.Constants;

public class HighscoreCell extends JPanel{

	/**
	 * Vista que mostra una cel·la on es mostra una puntuació
	 */
	private static final long serialVersionUID = 3804869214193693532L;
	public static final int CELL_WIDTH = 600;
	public static final int CELL_HEIGHT = 60;
	public String nomPartida = "";
	
	/**
	 * Constructora de la classe
	 * @param nomJugador Nom del jugador
	 * @param punts Número de punts que ha aconseguit el jugador
	 * @param data Data del moment en que el jugador va aconseguir la puntuació
	 */
	public HighscoreCell(String nomJugador, String punts, String data){
		setLayout(null);
		setOpaque(false);
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		setBorder(border);
		JLabel nomLabel = new JLabel(nomJugador);
		JLabel puntuacioLabel = new JLabel(punts);
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
	
	/**
	 * 
	 * @param nomPartida
	 * @param data
	 * @param listener
	 */
	public HighscoreCell(final String nomPartida, String data, final CellListener listener){
		this.nomPartida = nomPartida;
		setLayout(null);
		setOpaque(false);
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		setBorder(border);
		JLabel nomLabel = new JLabel(nomPartida);
		JLabel dataLabel = new JLabel(data);
		JButton button = new JButton();
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setBounds(0, 0, CELL_WIDTH, CELL_HEIGHT);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.cellPressed(nomPartida);
				setSelected(true);
			}
		});
		
		nomLabel.setFont(Constants.fontPlayersNames);
		nomLabel.setBounds(20, 15, 280, 20);
		nomLabel.setAlignmentY(CENTER_ALIGNMENT);
		
		dataLabel.setFont(Constants.fontPlayersNames);
		dataLabel.setBounds(330, 15, 230, 20);
		dataLabel.setAlignmentY(CENTER_ALIGNMENT);
		
		add(button);
		add(nomLabel);
		add(dataLabel);
	}
	
	//Getter & Setters
	public String getNomPartida(){
		return nomPartida;
	}
	
	public void setSelected(boolean select){
		if(select){
			setOpaque(true);
			setBackground(Color.YELLOW);
		}
		else{
			setOpaque(false);
		}
	}
}
