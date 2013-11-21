package upc.tfg.gui;

import java.awt.Color;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import upc.tfg.logic.Carta;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;

public class VistaInformacioCarta extends TransparentView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6927234034915993591L;
	private static final int CARTA_WIDTH = VistaTauler.CARTA_WIDTH+25;
	public static final int originCartaX = VistaInformacio.INFORMATION_WIDTH/2 - CARTA_WIDTH/2;
	private JLabel nomDistricte;
	private JLabel nomCarta;
	private JLabel imatgeCarta;
	private Carta carta;
	public JButton selectButton;
	
	public VistaInformacioCarta() {
		setLayout(null);
		setOpaque(false);
		
		//Afegim els labels
		nomCarta = new JLabel();
		nomCarta.setLayout(null);
		nomCarta.setBounds(10, 10, VistaInformacio.INFORMATION_WIDTH-20, 40);
		nomCarta.setFont(Constants.fontBradleyBig);
		nomCarta.setForeground(Color.WHITE);
		nomCarta.setOpaque(false);
		nomCarta.setHorizontalAlignment(JLabel.CENTER);
		nomCarta.setVerticalAlignment(JLabel.CENTER);
		add(nomCarta);
		
		nomDistricte = new JLabel();
		nomDistricte.setLayout(null);
		nomDistricte.setBounds(10, 60, VistaInformacio.INFORMATION_WIDTH-20, 40);
		nomDistricte.setFont(Constants.fontBradleyBig);
		nomDistricte.setForeground(Color.WHITE);
		nomDistricte.setOpaque(false);
		nomDistricte.setHorizontalAlignment(JLabel.CENTER);
		nomDistricte.setVerticalAlignment(JLabel.CENTER);
		add(nomDistricte);
		
		imatgeCarta = new JLabel();
		imatgeCarta.setLayout(null);
		imatgeCarta.setBounds(originCartaX, 130, VistaTauler.CARTA_WIDTH+25, VistaTauler.CARTA_HEIGHT+30);
		imatgeCarta.setOpaque(false);
		add(imatgeCarta);
		
		Locale defaultLocale = Locale.getDefault();
		ResourceBundle bundle = ResourceBundle.getBundle("AgoraBundle", defaultLocale);
		selectButton = new CustomDefaultButton(bundle.getString("seleccionar_carta"), 111, 37, Color.BLACK);
		selectButton.setBounds(imatgeCarta.getLocation().x-3, imatgeCarta.getLocation().y+imatgeCarta.getBounds().height+30, 
				111, 37);
		selectButton.setVisible(false);
		add(selectButton);
	}
	
	private void updateView(){
		nomCarta.setText("<html>"+carta.getNom()+"</html>");
		nomDistricte.setText("<html>"+carta.getDistricte().getNom()+"</html>");
	    ImageIcon icon = null;
	    if(carta.isShowing())icon = new ImageIcon(carta.getImage().getScaledInstance(VistaTauler.CARTA_WIDTH+25, VistaTauler.CARTA_HEIGHT+30,  java.awt.Image.SCALE_SMOOTH));
	    else icon = new ImageIcon(carta.getDistricte().getImage().getScaledInstance(VistaTauler.CARTA_WIDTH+25, VistaTauler.CARTA_HEIGHT+30,  java.awt.Image.SCALE_SMOOTH));
	    imatgeCarta.setIcon(icon);
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
		updateView();
	}
	
	public void setSelectVisible(boolean visibility){
		selectButton.setVisible(visibility);
		nomCarta.setVisible(!visibility);
	}
	
	public void showOnlyName(){
		selectButton.setVisible(false);
		nomCarta.setVisible(false);
	}

}
