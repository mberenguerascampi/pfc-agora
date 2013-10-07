package upc.tfg.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;

public class VistaPopUp extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1452077690748690332L;
	private final static int boxWidth = 550;
	private final static int boxHeight = 450;
	private final static int imgWidth = 315;
	private final static int imgHeight = 171;
	private Image img = null;
	private Image boxImg = null;
	private JLabel textLabel;
	private JLabel imageLabel;
	
	public VistaPopUp() {
		setOpaque(false);
		setLayout(null);
		addBackgroundButton();
		addLabel();
		addImage();
	}
	
	private void addBackgroundButton() {
		JButton backgroundButton = new JButton();
		backgroundButton.setOpaque(false);
		backgroundButton.setContentAreaFilled(false);
		backgroundButton.setBorderPainted(false);
		backgroundButton.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		backgroundButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		add(backgroundButton);
	}
	
	private void addImage(){
		imageLabel = new JLabel();
	        
	    //Agafem la imatge i la posem a la mida que volem
	    URL urlImg = getClass().getResource(Constants.fileUrl+"info_img_pas1.png");
	    Image pasImg = null;
	    try {
	    	pasImg = ImageIO.read(urlImg);
	    	pasImg = pasImg.getScaledInstance(imgWidth, imgHeight,  java.awt.Image.SCALE_SMOOTH ) ; 
		} catch (IOException e) {
			e.printStackTrace();
		}
	    ImageIcon icon = new ImageIcon(pasImg);
		    
	    imageLabel.setBounds(Constants.centerX-imgWidth/2, Constants.centerY+boxHeight/2-imgHeight-50, imgWidth, imgHeight);
	    imageLabel.setIcon(icon);
	    add(imageLabel);
	}

	private void addLabel() {
		textLabel = new JLabel("HOLA");
		textLabel.setOpaque(false);
//		textLabel.setFont(Constants.fontAnnaVives);
		textLabel.setFont(Constants.fontKristen);
		textLabel.setBounds(Constants.centerX-boxWidth/2+40, Constants.centerY-boxHeight/2+40, boxWidth-100, boxHeight-imgHeight-50);
		
		add(textLabel);
	}

	public void paintComponent(Graphics page)
	{
	    super.paintComponent(page);
	    if(img == null){
	    	URL urlImg = getClass().getResource(Constants.fileUrl+"transparent_background.png");
	    	URL urlBoxImg = getClass().getResource(Constants.fileUrl+"whiteBox.png");
			try {
				img = ImageIO.read(urlImg);
				img = img.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,  java.awt.Image.SCALE_SMOOTH ) ; 
				boxImg = ImageIO.read(urlBoxImg);
				boxImg = boxImg.getScaledInstance(boxWidth, boxHeight,  java.awt.Image.SCALE_SMOOTH ) ; 
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    page.drawImage(img, 0, 0, null);
	    page.drawImage(boxImg, Constants.centerX-boxWidth/2, Constants.centerY-boxHeight/2, null);
	}
	
	public void setVisible(boolean aFlag){
		super.setVisible(aFlag);
		if(aFlag){
			Locale defaultLocale = Locale.getDefault();
			ResourceBundle bundle = ResourceBundle.getBundle("AgoraBundle", defaultLocale);
			String text = "info_pas" + Partida.getInstance().getPas();
			textLabel.setText("<html>" + bundle.getString(text) + "</html>");
		}
	}
}
