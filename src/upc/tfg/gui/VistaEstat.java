package upc.tfg.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.utils.Constants;

public class VistaEstat extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7692685726322664996L;
	public static final int ESTAT_WIDTH = 250;
	public static final int ESTAT_HEIGHT = 150;
	private int numTorn;
	private int numPas;
	private JLabel tornLabel;
	private JLabel pasLabel;
	private String pasText = "";
	
	public VistaEstat() {
		setLayout(null);
		//setOpaque(false);
		setFocusable(false);
		setCursor(null);
		setBackground(new Color(0,0,0,94));
		
		numTorn = 1;
		
		tornLabel = new JLabel("");
		tornLabel.setLayout(null);
		tornLabel.setText("TORN " + String.valueOf(numTorn));
		tornLabel.setBounds(10, 10, ESTAT_WIDTH, 30);
		tornLabel.setFont(Constants.fontKristen);
		tornLabel.setForeground(Color.YELLOW);
		//tornLabel.setAlignmentX(0.5f);
		tornLabel.setAlignmentY(0.5f);
		add(tornLabel);
		
		numPas = 1;
		
		pasLabel = new JLabel("");
		pasLabel.setText("<html> PAS " + String.valueOf(numPas) + "<br>El jugador 1 ha de robar <br> una carta el jugador 2 </html>");
		pasLabel.setBounds(10, 45, ESTAT_WIDTH, 80);
		pasLabel.setFont(Constants.fontKristenSmall);
		pasLabel.setForeground(Color.WHITE);
		//pasLabel.setAlignmentX(0.5f);
		pasLabel.setAlignmentY(0.5f);
		pasLabel.setOpaque(false);
		add(pasLabel);
		
	}
	
	public void actualitzaEstat(){
		tornLabel.setText("TORN " + String.valueOf(numTorn));
		pasLabel.setText("<html> PAS " + String.valueOf(numPas) + pasText + " </html>");
	}
	
	//Getters & setters

	public int getNumTorn() {
		return numTorn;
	}

	public void setNumTorn(int numTorn) {
		this.numTorn = numTorn;
	}

	public int getNumPas() {
		return numPas;
	}

	public void setNumPas(int numPas) {
		this.numPas = numPas;
	}

	public String getPasText() {
		return pasText;
	}

	public void setPasText(String pasText) {
		this.pasText = pasText;
	}
	
	
}
