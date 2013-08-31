package upc.tfg.gui;

import upc.tfg.interfaces.TaulerListener;
import upc.tfg.utils.Constants;

public class VistaTauler extends DefaultView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5693342904886251734L;
	TaulerListener listener;
	
	public VistaTauler(TaulerListener tListener) {
		listener = tListener;
		setLayout(null);
		setSize(Constants.width, Constants.height);
        
		addSkin(Constants.fileUrl+"prova2.jpg");
	}
}
