package upc.tfg.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.utils.Constants;

public class InformationView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7414583561006916256L;
	public static final int INFORMATION_WIDTH = 210;
	public static final int INFORMATION_HEIGHT = 210;
	
	private JLabel nomDistricte;
	
	public InformationView() {
		setLayout(null);
		//setOpaque(false);
		setBackground(new Color(0,0,0,64));
		
		//Afegim els labels
		nomDistricte = new JLabel();
		nomDistricte.setLayout(null);
		nomDistricte.setBounds(10, 10, INFORMATION_WIDTH-20, 15);
		nomDistricte.setFont(Constants.fontCooper);
		nomDistricte.setForeground(Color.WHITE);
		add(nomDistricte);
	}
	
	public void setNomDistricte(String nom) {
		nomDistricte.setText(nom);
	}
}
