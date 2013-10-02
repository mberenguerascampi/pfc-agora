package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;
import upc.tfg.utils.PuntuacionsBD;

public class VistaPuntuacions extends DefaultView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6788437263725078792L;
	private VistaAmbBotoTornarListener listener;

	public VistaPuntuacions(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		setSize(Constants.width, Constants.height);
		afegirPuntuacions();
		afegirBotoTornar();
		addSkin("imatgePortada.jpg");
	}
	
	private void afegirPuntuacions(){
		PuntuacionsBD bd = new PuntuacionsBD();
		Map<String, Integer> puntuacions = bd.getPuntuacio();
		int i = 0;
		for(String key:puntuacions.keySet()){
			int punts = puntuacions.get(key);
			String[] strNoms = key.split("_");
			String nomJugador = strNoms[0];
			String data = strNoms[1];
			addCell(nomJugador, punts, data, i);
			++i;
		}
	}
	
	private void addCell(String nomJugador, int punts, String data, int num){
		//TODO
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
