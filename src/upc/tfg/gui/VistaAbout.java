package upc.tfg.gui;

import javax.swing.JLabel;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;

public class VistaAbout extends DefaultView {
	
	/**
	 * Vista que mostra la informació de l'aplicació
	 */
	private static final long serialVersionUID = 6434182862743054982L;

	/**
	 * Constructora de la classe
	 * @param listener Listener que escolta quan es prem el botó per tornar enderrere
	 */
	public VistaAbout(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		setSize(Constants.width, Constants.height);
		afegeixBarraSuperior(bundle.getString("sobre"), listener);
		afegirLabels();
		addSkin("backgroundWithWhiteBox.png");
	}
	
	/**
	 * Funció privada per afegir els labels en la vista
	 */
	private void afegirLabels(){
		int originX = Constants.paddingX + 100;
		int originY = (int) (Constants.paddingY + VistaBarraSuperior.BAR_HEIGHT + Constants.height*0.16);
		JLabel label_versio = new JLabel(bundle.getString("versio_text"));
		JLabel label_autor = new JLabel(bundle.getString("autor_text"));
		JLabel label_textExtra = new JLabel("<html>"+bundle.getString("about_text")+"</html>");
		
		label_versio.setFont(Constants.fontPlayersNames);
		label_autor.setFont(Constants.fontPlayersNames);
		label_textExtra.setFont(Constants.fontPlayersNames);
		
		label_versio.setBounds(originX, originY, 300, 25);
		label_autor.setBounds(originX, originY+85, 800, 25);
		label_textExtra.setBounds(originX, originY+160, 800, 60);
		
		add(label_textExtra);
		add(label_versio);
		add(label_autor);
	}
}
