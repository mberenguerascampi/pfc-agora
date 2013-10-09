package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;

public class VistaAbout extends DefaultView {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6434182862743054982L;
	private VistaAmbBotoTornarListener listener;

	public VistaAbout(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		setSize(Constants.width, Constants.height);
		afegeixBarraSuperior(bundle.getString("sobre"), listener);
		afegirLabels();
		addSkin("backgroundWithWhiteBox.png");
	}
	
	private void afegirLabels(){
		int originX = Constants.paddingX + 100;
		int originY = (int) (Constants.paddingY + VistaBarraSuperior.BAR_HEIGHT + Constants.height*0.16);
		JLabel label_versio = new JLabel(bundle.getString("versio_text"));
		JLabel label_autor = new JLabel(bundle.getString("autor_text"));
		
		label_versio.setFont(Constants.fontPlayersNames);
		label_autor.setFont(Constants.fontPlayersNames);
		
		label_versio.setBounds(originX, originY, 300, 25);
		label_autor.setBounds(originX, originY+85, 800, 25);
		
		add(label_versio);
		add(label_autor);
	}
}
