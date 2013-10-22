package upc.tfg.gui;

import java.util.Map;

import javax.swing.JLabel;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.PuntuacionsBD;

public class VistaPuntuacions extends DefaultView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6788437263725078792L;
	private VistaAmbBotoTornarListener listener;
	private JLabel labelDescription;

	public VistaPuntuacions(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		setSize(Constants.width, Constants.height);
		afegeixBarraSuperior(bundle.getString("puntuacions"), listener);
		afegirDescripcio();
		afegirPuntuacions();
		addSkin("backgroundWithWhiteBox.png");
	}
	
	private void afegirDescripcio(){
		labelDescription = new JLabel(bundle.getString("puntuacions_des"));
		labelDescription.setBounds((int) (Constants.width*0.39 - HighscoreCell.CELL_WIDTH/2)-70, Constants.height/2 - (HighscoreCell.CELL_HEIGHT+10)* PuntuacionsBD.NUM_MAX_SCORES/2, 500, 30);
		labelDescription.setFont(Constants.fontDescription);
		add(labelDescription);
	}
	
	private void afegirPuntuacions(){
		PuntuacionsBD bd = new PuntuacionsBD();
		Map<String, Integer> puntuacions = bd.getPuntuacio();
		addCell("NOM JUGADOR", "PUNTS", "DATA", 0);
		int i = 1;
		for(String key:puntuacions.keySet()){
			int punts = puntuacions.get(key);
			String[] strNoms = key.split("_");
			String nomJugador = strNoms[0];
			String data = "";
			String dataToAdd = "";
			if(strNoms.length > 1){
				data = strNoms[1];
				dataToAdd = data.substring(0, 10);
			}
			addCell(nomJugador, String.valueOf(punts), dataToAdd, i);
			++i;
		}
	}
	
	private void addCell(String nomJugador, String punts, String data, int num){
		int originX = (int) (Constants.width*0.39 - HighscoreCell.CELL_WIDTH/2);
		int originY = Constants.height/2 - (HighscoreCell.CELL_HEIGHT+10)* PuntuacionsBD.NUM_MAX_SCORES/2+90;
		HighscoreCell cell = new HighscoreCell(nomJugador, punts, data);
		cell.setBounds(originX, originY+num*HighscoreCell.CELL_HEIGHT, HighscoreCell.CELL_WIDTH, HighscoreCell.CELL_HEIGHT);
		add(cell);
	}
	
	public void setVisible(boolean aFlag){
		super.setVisible(aFlag);
		if(aFlag){
			removeAll();
			afegeixBarraSuperior(bundle.getString("puntuacions"), listener);
			afegirPuntuacions();
			afegirDescripcio();
			addSkin("backgroundWithWhiteBox.png");
		}
	}
}
