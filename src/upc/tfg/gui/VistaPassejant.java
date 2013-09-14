package upc.tfg.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import upc.tfg.utils.Constants;

public class VistaPassejant extends JButton {
	public static final String PASSEJANT_BLAU 		= "Blau";
	public static final String PASSEJANT_VERMELL 	= "Vermell";
	public static final String PASSEJANT_GROC 		= "Groc";
	public static final String PASSEJANT_VERD	 	= "Verd";
	public static final int PASSEJANT_WIDTH	 		= 95;
	public static final int PASSEJANT_HEIGHT	 	= 102;
	public static final int NUM_WIDTH	 			= 40;
	public static final int NUM_HEIGHT	 			= 40;
	
	private String color;
	private int num;
	private JLabel numLabel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5239720982932512754L;

	public VistaPassejant(String color, int num) {
		setLayout(null);
		setOpaque(false);
		setFocusPainted(false); 
		setContentAreaFilled(false); 
		setBorderPainted(false);
		this.color = color;
		this.num = num;
		
		numLabel = new JLabel("");
		numLabel.setLayout(null);
		numLabel.setText(String.valueOf(num));
		numLabel.setBounds(PASSEJANT_WIDTH/2 - NUM_WIDTH + 30, PASSEJANT_HEIGHT/2 - NUM_HEIGHT + 25, NUM_WIDTH, NUM_HEIGHT);
		numLabel.setFont(Constants.fontPassejants);
		//numLabel.setAlignmentX(0.5f);
		numLabel.setAlignmentY(0.5f);
		numLabel.setForeground(Color.YELLOW);
		add(numLabel);
	}
	
	public void decrementaNumPassejants(){
		setNum(num-1);
	}
	
	private void updateText(){
		numLabel.setText(String.valueOf(num));
		if (num == 0) numLabel.setVisible(false);
		else numLabel.setVisible(true);
		if (num < 0) setVisible(false);
	}
	
	public void paintComponent(Graphics graphics)
	{
	    super.paintComponent(graphics);
	    URL urlImg = getClass().getResource(Constants.fileUrl+"passejants/passejant"+ color + ".png");
	    Image img = null;
		try {
			img = ImageIO.read(urlImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    graphics.drawImage(img, 0, 0, null);
	}
	
	//Getter & setters
		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
			updateText();
		}
}
