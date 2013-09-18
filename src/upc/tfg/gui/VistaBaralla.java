package upc.tfg.gui;


import javax.swing.JPanel;

import upc.tfg.logic.Baralla;

public class VistaBaralla extends JPanel{
	Baralla baralla;
	VistaCarta vistaCarta;

	public VistaBaralla(Baralla baralla) {
		setLayout(null);
		setOpaque(false);
		this.baralla = baralla;
		vistaCarta = new VistaCarta(baralla.getCartes().get(1), 1);
		vistaCarta.setBounds(0, 0, VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT);
		add(vistaCarta);
	}
}
