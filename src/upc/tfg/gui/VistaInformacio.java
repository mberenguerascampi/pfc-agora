package upc.tfg.gui;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.logic.Districte;
import upc.tfg.utils.Constants;

public class VistaInformacio extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7414583561006916256L;
	public static final int INFORMATION_WIDTH = 210;
	public static final int INFORMATION_HEIGHT = 210;
	
	private JLabel nomDistricte;
	private JLabel passejantsVermells;
	
	public VistaInformacio() {
		setLayout(null);
		//setOpaque(false);
		setBackground(new Color(0,0,0,64));
		
		//Afegim els labels
		nomDistricte = new JLabel();
		nomDistricte.setLayout(null);
		nomDistricte.setBounds(10, 10, INFORMATION_WIDTH-20, 15);
		nomDistricte.setFont(Constants.fontBradleyBig);
		nomDistricte.setForeground(Color.WHITE);
		add(nomDistricte);
		
		//Afegim els passejants
		passejantsVermells = new JLabel();
		passejantsVermells.setLayout(null);
		passejantsVermells.setBounds(10, 30, 20, 20);
	}
	
	public void setDistricte(Districte districte){
		setNomDistricte(districte.getNom());
	}
	
	public void setNomDistricte(String nom) {
		nomDistricte.setText(nom);
	}
}
