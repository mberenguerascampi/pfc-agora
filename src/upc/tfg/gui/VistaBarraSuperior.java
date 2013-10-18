package upc.tfg.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;

public class VistaBarraSuperior extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6596321216452950051L;
	public static final int BAR_WIDTH = Constants.width;
	public static final int BAR_HEIGHT = 80;
	private VistaAmbBotoTornarListener listener;
	JButton backButton = null;

	public VistaBarraSuperior(String title, VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		setBackground(Color.LIGHT_GRAY);
		afegeixTitol(title);
		afegeixBotoEnderrere();
	}
	
	private void afegeixTitol(String title){
		JLabel titleLabel = new JLabel(title);
		titleLabel.setBounds(0, 0, BAR_WIDTH, BAR_HEIGHT);
		titleLabel.setFont(Constants.fontPlayerWinner);
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		add(titleLabel);
	}
	
	private void afegeixBotoEnderrere(){
		backButton = new JButton();
		
		backButton.setOpaque(false);
		backButton.setBounds(15, 0, BAR_HEIGHT+30, BAR_HEIGHT);
		backButton.setFocusPainted(false); 
		backButton.setContentAreaFilled(false); 
		backButton.setBorderPainted(false);
		
		//Configurem el fons del butó per els tres possibles estats que té
		URL urlImg = getClass().getResource(Constants.fileUrl+"backButton.png");
		Image img = null;
		try {
			img = ImageIO.read(urlImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		img = img.getScaledInstance(BAR_HEIGHT, BAR_HEIGHT-25,  java.awt.Image.SCALE_SMOOTH);
		
		ImageIcon icon = new ImageIcon(img);
			    
		URL urlRolloverImg = getClass().getResource(Constants.fileUrl+"backButton.png");
		ImageIcon rolloverIcon = new ImageIcon(img);
			    
		URL urlPressedImg = getClass().getResource(Constants.fileUrl+"backButton.png");
		ImageIcon pressedIcon = new ImageIcon(img);
			    
		backButton.setIcon(icon);
		backButton.setRolloverIcon(rolloverIcon);
		backButton.setPressedIcon(pressedIcon);
		
		addActionListener(backButton);
		
		add(backButton);
	}
	
	private void addActionListener(JButton backButton){
		for(ActionListener l:backButton.getActionListeners())backButton.removeActionListener(l);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getParent().setVisible(false);
				listener.backButtonPressed();
			}
		});
	}
}
