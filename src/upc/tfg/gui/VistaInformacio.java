package upc.tfg.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

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
	Districte districte;
	VistaPassejant vp;
	public int tempNum = 2;
	
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
		vp = new VistaPassejant(VistaPassejant.PASSEJANT_BLAU, 2);
		Rectangle frame = new Rectangle(10, 30, 95, 102);
		vp.setBounds(frame);
		add(vp);
	}
	
	public void setDistricte(Districte districte){
		this.districte = districte;
		setNomDistricte(districte.getNom());
	}
	
	public void setNomDistricte(String nom) {
		nomDistricte.setText(nom);
	}
	
	public void updatePassejants(int num){
		vp.setNum(num);
		tempNum = num;
	}
}
