package upc.tfg.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

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
	final JButton colorJ1 = new JButton();
	final JButton colorJ2 = new JButton();
	final JButton colorJ3 = new JButton();
	final JButton colorJ4 = new JButton();
	private JButton[] labelsColors = {colorJ1, colorJ2, colorJ3, colorJ4};
	final JButton iaJ1 = new JButton();
	final JButton iaJ2 = new JButton();
	final JButton iaJ3 = new JButton();
	final JButton iaJ4 = new JButton();
	private JButton[] buttonsIA = {iaJ1, iaJ2, iaJ3, iaJ4};
	private int[] indexColors = new int[4];
	private int[] indexIA = new int[4];
	private VistaAmbBotoTornarListener listener;
	
	public VistaComençarPartida(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		setBounds(Constants.paddingX, Constants.paddingY, Constants.width, Constants.height);
		this.listener = listener;
	}
	
	private void afegirDescripcio(){
		labelDescription = new JLabel("<html>"+bundle.getString("començar_des")+"</html>");
		labelDescription.setBounds((int) (Constants.width*0.39 - HighscoreCell.CELL_WIDTH/2)-100, Constants.height/2 - (HighscoreCell.CELL_HEIGHT+10)* PuntuacionsBD.NUM_MAX_SCORES/2, 800, 60);
		labelDescription.setFont(Constants.fontDescription);
		add(labelDescription);
	}
	
	private void afegeixLabelsJugador(){
		for(int i = 0; i < 4; ++i){
			JLabel nom = new JLabel(bundle.getString("nomCPU")+i);
			if(i == 0)nom.setText(bundle.getString("nomJugador"));
			nom.setBounds(textFields[i].getLocation().x-230, textFields[i].getLocation().y, 210, 30);
			nom.setFont(Constants.fontDescription);
			add(nom);
		}
	}
	
	private void afegeixTextFields(){
		int i = 0;
		for(JTextField itextField:textFields){
			itextField.setText("J"+(i+1));
			itextField.setFont(Constants.fontKristen);
			itextField.setBounds(labelDescription.getLocation().x+labelDescription.getWidth()/2-100,
					labelDescription.getLocation().y+130+i*55, 200, 30);
			//itextField.setText(Partida.getInstance().getNom());
			
			add(itextField);
			++i;
		}
	}
	
	private void afegeixLabelsColors(){
		final URL[] colors ={getClass().getResource(Constants.fileUrl+"colors/blau.png"),
				getClass().getResource(Constants.fileUrl+"colors/vermell.png"),
				getClass().getResource(Constants.fileUrl+"colors/verd.png"),
				getClass().getResource(Constants.fileUrl+"colors/groc.png")};
		
		int i = 0;
		for(final JButton colorJ:labelsColors){
			colorJ.setBounds(textFields[i].getLocation().x + textFields[i].getWidth() + 20, textFields[i].getLocation().y, 30, 30);
			colorJ.setBackground(Color.RED);
			colorJ.setFocusPainted(false); 
			colorJ.setContentAreaFilled(false); 
			colorJ.setBorderPainted(false);
			colorJ.setOpaque(false);
			colorJ.setIcon(new ImageIcon(colors[i]));
			final int index = i;
			indexColors[index] = i;
		    ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	  int nextColor = (indexColors[index]+1)%4;
		    	  indexColors[index] = nextColor;
		    	  colorJ.setIcon(new ImageIcon(colors[nextColor]));
		      }
		    };
		    colorJ.addActionListener(actionListener);
		    add(colorJ);
		    ++i;
		}
	}
	
	private void afegeixButtonsIA(){
		final URL[] iaSources ={
				getClass().getResource(Constants.fileUrl+"ia/orange_icon.png"),
				getClass().getResource(Constants.fileUrl+"ia/red_icon.png"),
				getClass().getResource(Constants.fileUrl+"ia/green_icon.png")};
		URL humanIcon = getClass().getResource(Constants.fileUrl+"ia/human_icon.png");
		
		int i = 0;
		for(final JButton iaJ:buttonsIA){
			iaJ.setLayout(null);
			iaJ.setBounds(labelsColors[i].getLocation().x + labelsColors[i].getWidth()+ 20,
						textFields[i].getLocation().y, 30, 30);
			iaJ.setFocusPainted(false); 
			iaJ.setContentAreaFilled(false); 
			iaJ.setBorderPainted(true);
			iaJ.setOpaque(false);
			iaJ.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			if(i == 0)iaJ.setIcon(new ImageIcon(humanIcon));
			else {
				iaJ.setIcon(new ImageIcon(iaSources[i-1]));
				final int index = i-1;
				indexIA[index] = i-1;
			    ActionListener actionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			    	  int nextColor = (indexIA[index]+1)%3;
			    	  indexIA[index] = nextColor;
			    	  iaJ.setIcon(new ImageIcon(iaSources[nextColor]));
			      }
			    };
			    iaJ.addActionListener(actionListener);
			}
		    add(iaJ);
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
				if(checkInputs()){
					listener.començarPartida(jugador1.getText(), jugador2.getText(), jugador3.getText(), jugador4.getText(), indexColors);
					setVisible(false);
				}
			}
		});
		
		add(cancelButton);
		add(acceptButton);
	}
	
	private boolean checkInputs(){
		boolean valid = true;
		String errorText = "";
		ArrayList<String>noms = new ArrayList<String>();
		for(JTextField nomField:textFields){
			if(noms.contains((nomField.getText()))){
				nomField.setBorder(BorderFactory.createLineBorder(Color.RED));
				errorText = "Nom repetit";
				valid = false;
			}
			else if(nomField.getText().equals("") || nomField.getText().startsWith(" ")){
				nomField.setBorder(BorderFactory.createLineBorder(Color.RED));
				errorText = "Nom buit";
				valid = false;
			}
			else{
				nomField.setBorder(UIManager.getBorder("TextField.border"));
			}
			noms.add(nomField.getText());
		}
		return valid;
	}
	
	public void setVisible(boolean aFlag){
		if(aFlag){
			removeAll();
			afegeixBarraSuperior(bundle.getString("començar"), listener);
			afegirDescripcio();
			afegeixTextFields();
			afegeixLabelsJugador();
			afegeixLabelsColors();
			afegeixButtonsIA();
			afegeixBotons();
			addSkin("backgroundWithWhiteBox.png");
			repaint();
		}
		super.setVisible(aFlag);
	}
}
