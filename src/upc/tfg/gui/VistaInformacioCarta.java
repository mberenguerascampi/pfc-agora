package upc.tfg.gui;

import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.logic.Carta;
import upc.tfg.utils.Constants;

public class VistaInformacioCarta extends TransparentView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6927234034915993591L;
	private JLabel nomDistricte;
	private JLabel nomCarta;
	private JLabel imatgeCarta;
	private Carta carta;
	
	public VistaInformacioCarta() {
		setLayout(null);
		setOpaque(false);
		
		//Afegim els labels
		nomCarta = new JLabel();
		nomCarta.setLayout(null);
		nomCarta.setBounds(10, 10, VistaInformacio.INFORMATION_WIDTH-20, 15);
		nomCarta.setFont(Constants.fontBradleyBig);
		nomCarta.setForeground(Color.WHITE);
		nomCarta.setOpaque(false);
		add(nomCarta);
		
		nomDistricte = new JLabel();
		nomDistricte.setLayout(null);
		nomDistricte.setBounds(10, 30, VistaInformacio.INFORMATION_WIDTH-20, 15);
		nomDistricte.setFont(Constants.fontBradleyBig);
		nomDistricte.setForeground(Color.WHITE);
		nomDistricte.setOpaque(false);
		add(nomDistricte);
		
		imatgeCarta = new JLabel();
		imatgeCarta.setLayout(null);
		imatgeCarta.setBounds(20, 70, VistaTauler.CARTA_WIDTH+25, VistaTauler.CARTA_HEIGHT+30);
		imatgeCarta.setOpaque(false);
		add(imatgeCarta);
	}
	
	private void updateView(){
		nomCarta.setText(carta.getNom());
		nomDistricte.setText(carta.getDistricte().getNom());
	    ImageIcon icon = new ImageIcon(carta.getImage().getScaledInstance(VistaTauler.CARTA_WIDTH+25, VistaTauler.CARTA_HEIGHT+30,  java.awt.Image.SCALE_SMOOTH));
	    imatgeCarta.setIcon(icon);
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
		updateView();
	}

}
