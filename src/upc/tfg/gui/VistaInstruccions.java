package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;

public class VistaInstruccions extends DefaultView{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1101453571450219200L;
	private VistaAmbBotoTornarListener listener;

	public VistaInstruccions(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		setSize(Constants.width, Constants.height);
		afegirBotoTornar();
		addSkin("imatgePortada.jpg");
	}
	
	private void afegirBotoTornar(){
		CustomDefaultButton tornar = new CustomDefaultButton("Tornar al menú principal");
		tornar.setBounds(180, Constants.height-210, 195, 80);
		tornar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.backButtonPressed();
			}
		});
		add(tornar);
	}
}
