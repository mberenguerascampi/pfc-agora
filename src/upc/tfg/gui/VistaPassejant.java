package upc.tfg.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
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
	public static final int NUM_WIDTH	 			= 95;
	public static final int NUM_HEIGHT	 			= 40;
	
	private String color;
	private int iColor;
	private int num;
	private JLabel numLabel;
	private boolean showZero;
	private boolean draggingPassejant;
	private boolean bloquejat = false;
	private Image img = null;
	private Image imgBloquejat = null;
	private int minValue = 0;
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
		setRolloverEnabled(false);
		repaint();
		this.color = color;
		this.num = num;
		
		numLabel = new JLabel("");
		numLabel.setLayout(null);
		numLabel.setText(String.valueOf(num));
		numLabel.setBounds(2, PASSEJANT_HEIGHT/2 - NUM_HEIGHT + 25, NUM_WIDTH, NUM_HEIGHT);
		numLabel.setFont(Constants.fontPassejants);
		//numLabel.setAlignmentX(0.5f);
		numLabel.setAlignmentY(0.5f);
		numLabel.setVerticalAlignment(CENTER);
		numLabel.setHorizontalAlignment(CENTER);
		Color foreground = null;
		if (color.equals(PASSEJANT_BLAU)){
			foreground = Color.YELLOW;
			iColor = Constants.BLAU;
		}
		else if (color.equals(PASSEJANT_VERMELL)){
			foreground = Color.GREEN;
			iColor = Constants.VERMELL;
		}
		else if (color.equals(PASSEJANT_GROC)){
			foreground = Color.BLUE;
			iColor = Constants.GROC;
		}
		else {
			foreground = Color.RED;
			iColor = Constants.VERD;
		}
		numLabel.setForeground(foreground);
		updateText();
		add(numLabel);
	}
	
	public void decrementaNumPassejants(){
		setNum(num-1);
	}
	
	private void updateText(){
		numLabel.setText(String.valueOf(num));
		if (num < 10)numLabel.setFont(Constants.fontPassejantsBig);
		else numLabel.setFont(Constants.fontPassejants);
		if (num == 0 && !showZero) numLabel.setVisible(false);
		else if (minValue > 0 && num == minValue && showZero) numLabel.setVisible(false);
		else numLabel.setVisible(true);
		if (num < 0) setVisible(false);
		if (minValue > 0 && num < minValue)setVisible(false);
		else if (minValue > 0)setVisible(true);
	}
	
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		if (img == null){
			URL urlImg = getClass().getResource(Constants.fileUrl+"passejants/passejant"+ color + ".png");
			try {
				img = ImageIO.read(urlImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    graphics.drawImage(img, 0, 0, null);
	    if(bloquejat){
			if (imgBloquejat == null){
				URL urlImg = getClass().getResource(Constants.fileUrl+"passejants/passejantBloquejat.png");
				try {
					imgBloquejat = ImageIO.read(urlImg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			graphics.drawImage(imgBloquejat, 0, 0, null);
		}
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

		public boolean isShowZero() {
			return showZero;
		}

		public void setShowZero(boolean showZero) {
			this.showZero = showZero;
			numLabel.setVisible(showZero);
		}

		public boolean isDraggingPassejant() {
			return draggingPassejant;
		}

		public void setDraggingPassejant(boolean draggingPassejant) {
			this.draggingPassejant = draggingPassejant;
		}

		public int getiColor() {
			return iColor;
		}

		public void setiColor(int iColor) {
			this.iColor = iColor;
		}

		public boolean isBloquejat() {
			return bloquejat;
		}

		public void setBloquejat(boolean bloquejat) {
			this.bloquejat = bloquejat;
		}

		public int getMinValue() {
			return minValue;
		}

		public void setMinValue(int minValue) {
			this.minValue = minValue;
		}
		
		public static String getStrColor(int color){
			if (color == Constants.BLAU){
				return PASSEJANT_BLAU;
			}
			else if (color == Constants.VERMELL){
				return PASSEJANT_VERMELL;
			}
			else if (color == Constants.VERD){
				return PASSEJANT_VERD;
			}
			else {
				return PASSEJANT_GROC;
			}
		}
}
