package upc.tfg.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import upc.tfg.interfaces.CellListener;
import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.DefaultDataBase;
import upc.tfg.utils.PuntuacionsBD;

public class VistaCarregarPartida extends DefaultView implements CellListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5465148974317814990L;
	private VistaAmbBotoTornarListener listener;
	private JLabel labelDescription;
	private JPanel partidesPanel = new JPanel();
	private static final int NUM_MAX_CELLS = 6;

	public VistaCarregarPartida(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		setSize(Constants.width, Constants.height);
		this.listener = listener;
	}
	
	private void afegirDescripcio(){
		labelDescription = new JLabel(bundle.getString("carregar_des"));
		labelDescription.setBounds((int) (Constants.width*0.39 - HighscoreCell.CELL_WIDTH/2)-70, Constants.height/2 - (HighscoreCell.CELL_HEIGHT+10)* PuntuacionsBD.NUM_MAX_SCORES/2, 500, 30);
		labelDescription.setFont(Constants.fontDescription);
		add(labelDescription);
	}
	
	private void afegirPartidesGuardades(){
		Map<String,String> noms = DefaultDataBase.getNomsPartides();
		Iterator<Entry<String, String>> it = noms.entrySet().iterator();
		int i = 0;
		int originX = (int) (Constants.width*0.39 - HighscoreCell.CELL_WIDTH/2);
		int originY = Constants.height/2 - (HighscoreCell.CELL_HEIGHT+10)* PuntuacionsBD.NUM_MAX_SCORES/2+90;
		int extraWidth = 2;
		if (noms.size() > NUM_MAX_CELLS){
			extraWidth = 20;
		}
		
		partidesPanel.setLayout(null);
		partidesPanel.setPreferredSize(new Dimension(HighscoreCell.CELL_WIDTH, HighscoreCell.CELL_HEIGHT*noms.size()));
		while (it.hasNext()) {
	        @SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry)it.next();
	        addCell(String.valueOf(pairs.getKey()), String.valueOf(pairs.getValue()), i);
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        it.remove(); 
	        ++i;
	    }
		JScrollPane scrollView = new JScrollPane(partidesPanel);
		scrollView.setBounds(originX, originY, HighscoreCell.CELL_WIDTH+extraWidth, HighscoreCell.CELL_HEIGHT*NUM_MAX_CELLS+15);
		scrollView.getVerticalScrollBar().setPreferredSize(new Dimension(18, 0));
		add(scrollView, BorderLayout.CENTER);
	}
	
	private void addCell(String nomJugador, String data, int num){
		HighscoreCell cell = new HighscoreCell(nomJugador, data, this);
		cell.setBounds(0, num*HighscoreCell.CELL_HEIGHT, HighscoreCell.CELL_WIDTH, HighscoreCell.CELL_HEIGHT);
		partidesPanel.add(cell);
	}
	
	public void setVisible(boolean aFlag){
		if(aFlag){
			partidesPanel.removeAll();
			removeAll();
			afegeixBarraSuperior(bundle.getString("carregar"), listener);
			afegirDescripcio();
			afegirPartidesGuardades();
			addSkin("backgroundWithWhiteBox.png");
			repaint();
		}
		super.setVisible(aFlag);
	}
	
	//FUNCIONS QUE IMPLEMENTEN EL LISTENER DE LA CEL·LA
	
	@Override
	public void cellPressed(String name) {
		listener.loadGame(DefaultDataBase.getPartida(name));
		setVisible(false);
	}
}
