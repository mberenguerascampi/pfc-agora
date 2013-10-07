package upc.tfg.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import upc.tfg.interfaces.VistaEstatListener;
import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;

public class VistaEstat extends TransparentView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7692685726322664996L;
	public static final int ESTAT_WIDTH = 250;
	public static final int ESTAT_HEIGHT = 170;
	private int numTorn;
	private int numPas;
	private JLabel tornLabel;
	private JLabel pasLabel;
	private String pasText = "";
	private JButton info_btn;
	private VistaEstatListener listener;
	
	public VistaEstat(VistaEstatListener listener) {
		setLayout(null);
		setOpaque(false);
		setFocusable(false);
		setCursor(null);
		
		this.listener = listener;
		
		numTorn = 1;
		
		tornLabel = new JLabel("");
		tornLabel.setLayout(null);
		tornLabel.setText("TORN " + String.valueOf(Partida.getInstance().getTorn()));
		tornLabel.setBounds(10, 10, ESTAT_WIDTH, 30);
		tornLabel.setFont(Constants.fontKristen);
		tornLabel.setForeground(Color.YELLOW);
		//tornLabel.setAlignmentX(0.5f);
		tornLabel.setAlignmentY(0.5f);
		add(tornLabel);
		
		numPas = 1;
		createPasLabel();
		
		addInfoButton();
	}
	
	private void addInfoButton() {
		info_btn = new JButton();
		info_btn.setOpaque(false);
		info_btn.setBounds(ESTAT_WIDTH-60, 10, 40, 40);
		info_btn.setFocusPainted(false); 
		info_btn.setContentAreaFilled(false); 
		info_btn.setBorderPainted(false);
		
		//Configurem el fons del butó per els tres possibles estats que té
		Image img = null;
		URL urlImg = getClass().getResource(Constants.fileUrl+"icon_info_on.png");
		try {
			img = ImageIO.read(urlImg);
			img = img.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(img);
		
		info_btn.setIcon(icon);
		info_btn.setRolloverIcon(icon);
		info_btn.setPressedIcon(icon);
		
		info_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.infoButtonPressed();
			}
		});
		
		add(info_btn);
	}

	private void createPasLabel(){
		if (pasLabel != null)remove(pasLabel);
		pasLabel = new JLabel("");
		pasText = Partida.getInstance().getTextPas();
		pasLabel.setText(pasText);
		pasLabel.setBounds(10, 45, ESTAT_WIDTH, 125);
		pasLabel.setFont(Constants.fontKristenSmall);
		pasLabel.setForeground(Color.WHITE);
		//pasLabel.setAlignmentX(0.5f);
		pasLabel.setAlignmentY(0.5f);
		pasLabel.setOpaque(false);
		add(pasLabel);
	}
	
	public void actualitzaEstat(){
		pasText = Partida.getInstance().getTextPas();
		pasLabel.setText(pasText);
		tornLabel.setText("TORN " + String.valueOf(Partida.getInstance().getTorn()));
	}
	
	//Getters & setters

	public int getNumTorn() {
		return numTorn;
	}

	public void setNumTorn(int numTorn) {
		this.numTorn = numTorn;
	}

	public int getNumPas() {
		return numPas;
	}

	public void setNumPas(int numPas) {
		this.numPas = numPas;
	}

	public String getPasText() {
		return pasText;
	}

	public void setPasText(String pasText) {
		this.pasText = pasText;
	}
}
