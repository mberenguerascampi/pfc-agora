package upc.tfg.gui;

import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import upc.tfg.utils.Constants;

public class VistaAlertes extends TransparentView{

	/**
	 * Vista que mostra les alertes que es produeixen mentre es juga, ja sigui per alguna acció que no es 
	 * realitzar, per advertir d'algun succes o simplement per donar feedback al usuari sobre una acció
	 * realitzada
	 */
	private static final long serialVersionUID = 9174551117712932495L;
	public static final int WARNING_WIDTH = 250;
	public static final int WARNING_HEIGHT = 235;
	private JLabel warningLabel;
	private JLabel iconLabel;
	private JLabel titleLabel;
	static VistaAlertes instance;
	
	/**
	 * Constructora de la classe
	 */
	public VistaAlertes(){
		setLayout(null);
		setOpaque(false);
		setFocusable(false);
		setCursor(null);
		setBounds(Constants.paddingX, Constants.paddingY+Constants.height-WARNING_HEIGHT, WARNING_WIDTH, WARNING_HEIGHT);
		
		iconLabel = new JLabel();
		iconLabel.setBounds(5, 5, 40, 40);
		URL url = getClass().getResource(Constants.fileUrl+"icons/warning_icon.png");
		ImageIcon icon = new ImageIcon(url);
		iconLabel.setIcon(icon);
		add(iconLabel);
		
		titleLabel = new JLabel("ALERTA!");
		titleLabel.setLayout(null);
		titleLabel.setBounds(0, 0, WARNING_WIDTH, 40);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(Constants.fontKristen);
		titleLabel.setForeground(Color.WHITE);
		add(titleLabel);
		
		warningLabel = new JLabel();
		warningLabel.setLayout(null);
		warningLabel.setBounds(5, 50, WARNING_WIDTH-10, WARNING_HEIGHT-80);
		warningLabel.setFont(Constants.fontKristenSmall);
		warningLabel.setForeground(Color.WHITE);
		warningLabel.setVerticalAlignment(JLabel.TOP);
		add(warningLabel);
		
		instance = this;
	}
	
	/**
	 * Funció per obtenir la instància de la classe
	 * @return
	 */
	public static VistaAlertes getInstance(){
		return instance;
	}

	/**
	 * Acció pe mostrar per pantalla un determinat text com a una alerta
	 * @param text Text que es vol mostrar
	 */
	public void setWarningText(String text){
		URL url = getClass().getResource(Constants.fileUrl+"icons/warning_icon.png");
		ImageIcon icon = new ImageIcon(url);
		iconLabel.setIcon(icon);
		titleLabel.setText("ALERTA!");
		warningLabel.setText("<html>"+text+"</html>");
		setVisible(true);
	}
	
	/**
	 * Acció pe mostrar per pantalla un determinat text com a resultat d'una acció realitzada correctament
	 * @param text Text que es vol mostrar
	 */
	public void setOkText(String text){
		URL url = getClass().getResource(Constants.fileUrl+"icons/ok_icon.png");
		ImageIcon icon = new ImageIcon(url);
		iconLabel.setIcon(icon);
		titleLabel.setText("OK");
		warningLabel.setText("<html>"+text+"</html>");
		setVisible(true);
	}
	
	public String getText(){
		if(warningLabel.getText().contains("<html>"))return warningLabel.getText().substring(6, warningLabel.getText().length()-7);
		else return warningLabel.getText();
	}
}
