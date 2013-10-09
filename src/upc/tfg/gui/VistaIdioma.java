package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JLabel;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomCheckBox;

public class VistaIdioma extends DefaultView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5968025674577972291L;
	private VistaAmbBotoTornarListener listener;
	private CustomCheckBox checkBoxCatala;
	private CustomCheckBox checkBoxCastella;
	private CustomCheckBox checkBoxAngles;
	
	public VistaIdioma(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		setSize(Constants.width, Constants.height);
		afegeixBarraSuperior(bundle.getString("idioma"), listener);
		addButtons();
		addLabels();
		addSkin("backgroundWithWhiteBox.png");
		seleccionaIdiomaInicial();
	}
	
	private void addLabels(){
		JLabel labelCatala = new JLabel(bundle.getString("idioma_cat"));
		JLabel labelCastella = new JLabel(bundle.getString("idioma_es"));
		JLabel labelAngles = new JLabel(bundle.getString("idioma_en"));
		
		labelCatala.setBounds(checkBoxCatala.getLocation().x+80, checkBoxCatala.getLocation().y, 200, 70);
		labelCastella.setBounds(checkBoxCastella.getLocation().x+80, checkBoxCastella.getLocation().y, 200, 70);
		labelAngles.setBounds(checkBoxAngles.getLocation().x+80, checkBoxAngles.getLocation().y, 200, 70);
		
		labelCatala.setFont(Constants.fontPlayersNames);
		labelCastella.setFont(Constants.fontPlayersNames);
		labelAngles.setFont(Constants.fontPlayersNames);
		
		add(labelCatala);
		add(labelCastella);
		add(labelAngles);
	}
	
	private void addButtons(){
		int originX = (int) (Constants.width*0.39 - 140);
		int originY = (int) (Constants.paddingY + Constants.height*0.36 + VistaBarraSuperior.HEIGHT);
		checkBoxCatala = new CustomCheckBox();
		checkBoxCatala.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				seleccionaIdioma(true, false, false);
			}
		});
		checkBoxCatala.setBounds(originX, originY, 70, 70);
		
		checkBoxCastella = new CustomCheckBox();
		checkBoxCastella.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				seleccionaIdioma(false, true, false);
			}
		});
		checkBoxCastella.setBounds(originX, originY+110, 70, 70);
		
		checkBoxAngles = new CustomCheckBox();
		checkBoxAngles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				seleccionaIdioma(false, false, true);
			}
		});
		checkBoxAngles.setBounds(originX, originY+220, 70, 70);
		
		add(checkBoxCatala);
		add(checkBoxCastella);
		add(checkBoxAngles);
	}
	
	private void seleccionaIdiomaInicial(){
		Locale defaultLoc = Locale.getDefault();
		if(defaultLoc.equals(new Locale("en", "EN"))){
			configuraCheckBox(false, false, true);
		}
		else if(defaultLoc.equals(new Locale("es", "ES"))){
			configuraCheckBox(false, true, false);
		}
		else{
			configuraCheckBox(true, false, false);
		}
	}
	
	private void seleccionaIdioma(boolean catala, boolean castella, boolean angles){
		configuraCheckBox(catala, castella, angles);
		if(angles){
			Locale catLocale = new Locale("en", "EN");
    		Locale.setDefault(catLocale);
		}
		else if(castella){
			Locale catLocale = new Locale("es", "ES");
    		Locale.setDefault(catLocale);
		}
		else{
			Locale catLocale = new Locale("ca", "CAT");
    		Locale.setDefault(catLocale);
		}
		
		listener.backButtonPressed();
	}
	
	private void configuraCheckBox(boolean catala, boolean castella, boolean angles){
		checkBoxCatala.setSelected(catala);
		checkBoxCastella.setSelected(castella);
		checkBoxAngles.setSelected(angles);
	}
}
