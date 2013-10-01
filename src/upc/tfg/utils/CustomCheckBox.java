package upc.tfg.utils;

import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class CustomCheckBox extends JCheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5752083464376990418L;

	public CustomCheckBox(){
		setOpaque(false);
		URL urlUnchecked = getClass().getResource(Constants.fileUrl + "checkboxUnchecked.png");
		URL urlChecked = getClass().getResource(Constants.fileUrl + "checkboxChecked.png");
		
		// Set default icon for checkbox
	    setIcon(new ImageIcon(urlUnchecked));
	    // Set selected icon when checkbox state is selected
	    setSelectedIcon(new ImageIcon(urlChecked));
	    // Set disabled icon for checkbox
	    setDisabledIcon(new ImageIcon(urlUnchecked));
	    // Set disabled-selected icon for checkbox
	    setDisabledSelectedIcon(new ImageIcon(urlChecked));
	    // Set checkbox icon when checkbox is pressed
	    setPressedIcon(new ImageIcon(urlUnchecked));
	    // Set icon when a mouse is over the checkbox
	    setRolloverIcon(new ImageIcon(urlUnchecked));
	    // Set icon when a mouse is over a selected checkbox
	    setRolloverSelectedIcon(new ImageIcon(urlChecked));
	}
}
