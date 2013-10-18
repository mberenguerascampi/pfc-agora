package upc.tfg.gui;

import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import upc.tfg.utils.Constants;

public class VistaAlertes extends TransparentView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9174551117712932495L;
	public static final int WARNING_WIDTH = 250;
	public static final int WARNING_HEIGHT = 210;
	private JLabel warningLabel;
	private JLabel iconLabel;
	private JLabel titleLabel;
	static VistaAlertes instance;
	
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
	
	public static VistaAlertes getInstance(){
		return instance;
	}

	public void setWarningText(String text){
		URL url = getClass().getResource(Constants.fileUrl+"icons/warning_icon.png");
		ImageIcon icon = new ImageIcon(url);
		iconLabel.setIcon(icon);
		titleLabel.setText("ALERTA!");
		warningLabel.setText("<html>"+text+"</html>");
		setVisible(true);
	}
	
	public void setOkText(String text){
		URL url = getClass().getResource(Constants.fileUrl+"icons/ok_icon.png");
		ImageIcon icon = new ImageIcon(url);
		iconLabel.setIcon(icon);
		titleLabel.setText("OK");
		warningLabel.setText("<html>"+text+"</html>");
		setVisible(true);
	}
}
