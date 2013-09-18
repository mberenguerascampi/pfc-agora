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
	public static final int INFORMATION_WIDTH = 150;
	public static final int INFORMATION_HEIGHT = 435;
	
	private JLabel nomDistricte;
	Districte districte;
	VistaPassejant vpBlau;
	VistaPassejant vpVermell;
	VistaPassejant vpGroc;
	VistaPassejant vpVerd;
	
	public VistaInformacio() {
		setLayout(null);
		//setOpaque(false);
		setBackground(new Color(0,0,0,94));
		
		//Afegim els labels
		nomDistricte = new JLabel();
		nomDistricte.setLayout(null);
		nomDistricte.setBounds(10, 10, INFORMATION_WIDTH-20, 15);
		nomDistricte.setFont(Constants.fontBradleyBig);
		nomDistricte.setForeground(Color.WHITE);
		nomDistricte.setOpaque(false);
		add(nomDistricte);
		
		//Afegim els passejants
		addPassejants();
		repaint();
	}
	
	public void setDistricte(Districte districte){
		this.districte = districte;
		setNomDistricte(districte.getNom());
		vpBlau.setNum(districte.getNumPassejantsBlaus());
		vpVermell.setNum(districte.getNumPassejantsVermells());
		vpVerd.setNum(districte.getNumPassejantsVerds());
		vpGroc.setNum(districte.getNumPassejantsGrocs());
	}
	
	public void setNomDistricte(String nom) {
		nomDistricte.setText(nom);
	}
	
	public void updatePassejants(int num){
		vpBlau.setNum(num);
	}
	
	public void update(){
		vpBlau.setNum(districte.getNumPassejantsBlaus());
		vpVermell.setNum(districte.getNumPassejantsVermells());
		vpVerd.setNum(districte.getNumPassejantsVerds());
		vpGroc.setNum(districte.getNumPassejantsGrocs());
	}
	
	private void addPassejants(){
		vpBlau = new VistaPassejant(VistaPassejant.PASSEJANT_BLAU, 0);
		vpBlau.setEnabled(false);
		Rectangle frame = new Rectangle(10, 30, 95, 102);
		vpBlau.setBounds(frame);
		vpBlau.setShowZero(true);
		add(vpBlau);
		
		vpVermell = new VistaPassejant(VistaPassejant.PASSEJANT_VERMELL, 0);
		vpVermell.setEnabled(false);
		frame = new Rectangle(10, 130, 95, 102);
		vpVermell.setBounds(frame);
		vpVermell.setShowZero(true);
		add(vpVermell);
		
		vpGroc = new VistaPassejant(VistaPassejant.PASSEJANT_GROC, 0);
		vpGroc.setEnabled(false);
		frame = new Rectangle(10, 230, 95, 102);
		vpGroc.setShowZero(true);
		vpGroc.setBounds(frame);
		add(vpGroc);
		
		vpVerd = new VistaPassejant(VistaPassejant.PASSEJANT_VERD, 0);
		vpVerd.setEnabled(false);
		frame = new Rectangle(10, 330, 95, 102);
		vpVerd.setShowZero(true);
		vpVerd.setBounds(frame);
		add(vpVerd);
	}
}
