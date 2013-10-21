package upc.tfg.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import upc.tfg.interfaces.CellListener;
import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;
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
	private static final int NUM_MAX_CELLS = 4;
	private CustomDefaultButton loadButton;
	private CustomDefaultButton deleteButton;
	private JScrollPane scrollView;
	private String gameName = "";
	private int totalGames = 0;

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
		totalGames = noms.size();
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
		scrollView = new JScrollPane(partidesPanel);
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
			addButtons();
			addSkin("backgroundWithWhiteBox.png");
			repaint();
		}
		super.setVisible(aFlag);
	}
	
	private void addButtons(){
		deleteButton = new CustomDefaultButton("BORRAR");
		loadButton = new CustomDefaultButton("CARREGAR");
		
		deleteButton.setBounds(scrollView.getLocation().x, scrollView.getLocation().y+scrollView.getHeight()+55,
									CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
		loadButton.setBounds(scrollView.getLocation().x + scrollView.getWidth()-CustomDefaultButton.BUTTON_WIDTH, 
				scrollView.getLocation().y+scrollView.getHeight()+55,
				CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.deleteGame(gameName);
				reloadGames();
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.loadGame(DefaultDataBase.getPartida(gameName));
				setVisible(false);
			}
		});
		
		deleteButton.setVisible(false);
		loadButton.setVisible(false);
		
		add(deleteButton);
		add(loadButton);
	}
	
	private void reloadGames(){
		--totalGames;
		boolean trobat = false;
		Component[] components = partidesPanel.getComponents();
		for(int i = 0; i < components.length; ++i){
			HighscoreCell cell = (HighscoreCell)components[i];
			if(trobat){
				cell.setLocation(cell.getLocation().x, cell.getLocation().y-HighscoreCell.CELL_HEIGHT);
			}
			else if(cell.getNomPartida().equals(gameName)){
				partidesPanel.remove(cell);
				trobat = true;
			}
		}
		partidesPanel.setPreferredSize(new Dimension(HighscoreCell.CELL_WIDTH, partidesPanel.getPreferredSize().height-HighscoreCell.CELL_HEIGHT));
		
		int extraWidth = 2;
		if (totalGames > NUM_MAX_CELLS){
			extraWidth = 20;
		}
		Rectangle r = scrollView.getBounds();
		scrollView.setBounds(r.x, r.y, HighscoreCell.CELL_WIDTH+extraWidth, r.height);
		
		deleteButton.setVisible(false);
		loadButton.setVisible(false);
		repaint();
	}
	
	private void setButtons(){
		boolean trobat = false;
		Component[] components = partidesPanel.getComponents();
		for(int i = 0; i < components.length && !trobat; ++i){
			HighscoreCell cell = (HighscoreCell)components[i];
			if(cell.getNomPartida().equals(gameName)){
				cell.setSelected(false);
				trobat = true;
			}
		}
		deleteButton.setVisible(true);
		loadButton.setVisible(true);
		repaint();
	}
	
	//FUNCIONS QUE IMPLEMENTEN EL LISTENER DE LA CEL·LA
	
	@Override
	public void cellPressed(String name) {
		setButtons();
		gameName = name;
	}
}
