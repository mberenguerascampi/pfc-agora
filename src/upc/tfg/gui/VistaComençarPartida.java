package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;
import upc.tfg.utils.PuntuacionsBD;

public class VistaComençarPartida extends DefaultView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896990833751676989L;
	private JLabel labelDescription;
	private JTextField jugador1 = new JTextField();
	private JTextField jugador2 = new JTextField();
	private JTextField jugador3 = new JTextField();
	private JTextField jugador4 = new JTextField();
	private JTextField[] textFields = {jugador1, jugador2, jugador3, jugador4};
	private VistaAmbBotoTornarListener listener;
	
	public VistaComençarPartida(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		setBounds(Constants.paddingX, Constants.paddingY, Constants.width, Constants.height);
		this.listener = listener;
	}
	
	private void afegirDescripcio(){
		labelDescription = new JLabel(bundle.getString("començar_des"));
		labelDescription.setBounds((int) (Constants.width*0.39 - HighscoreCell.CELL_WIDTH/2)-70, Constants.height/2 - (HighscoreCell.CELL_HEIGHT+10)* PuntuacionsBD.NUM_MAX_SCORES/2, 500, 30);
		labelDescription.setFont(Constants.fontDescription);
		add(labelDescription);
	}
	
	private void afegeixLabelsJugador(){
		for(int i = 0; i < 4; ++i){
			JLabel nom = new JLabel(bundle.getString("nom")+(i+1));
			nom.setBounds(labelDescription.getLocation().x, textFields[i].getLocation().y, 100, 30);
			nom.setFont(Constants.fontDescription);
			add(nom);
		}
	}
	
	private void afegeixTextFields(){
		int i = 0;
		for(JTextField itextField:textFields){
			itextField.setText("J"+(i+1));
			itextField.setFont(Constants.fontKristen);
			itextField.setBounds(labelDescription.getLocation().x+120, labelDescription.getLocation().y+60+i*55, 200, 30);
			//itextField.setText(Partida.getInstance().getNom());
			
			add(itextField);
			++i;
		}
	}
	
	private void afegeixBotons(){
		int centreX = (jugador4.getLocation().x + jugador4.getWidth() + 300) / 2;
		CustomDefaultButton cancelButton = new CustomDefaultButton("CANCELAR");
		cancelButton.setBounds(centreX-60-CustomDefaultButton.BUTTON_WIDTH, jugador4.getLocation().y+100, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.backButtonPressed();
				setVisible(false);
			}
		});
		
		CustomDefaultButton acceptButton = new CustomDefaultButton("ACCEPTAR");
		acceptButton.setBounds(centreX+60, jugador4.getLocation().y+100, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.començarPartida(jugador1.getText(), jugador2.getText(), jugador3.getText(), jugador4.getText());
				setVisible(false);
			}
		});
		
		add(cancelButton);
		add(acceptButton);
	}
	
	public void setVisible(boolean aFlag){
		if(aFlag){
			removeAll();
			afegeixBarraSuperior(bundle.getString("començar"), listener);
			afegirDescripcio();
			afegeixTextFields();
			afegeixLabelsJugador();
			afegeixBotons();
			addSkin("backgroundWithWhiteBox.png");
			repaint();
		}
		super.setVisible(aFlag);
	}
}
