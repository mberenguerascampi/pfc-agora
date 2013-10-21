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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import upc.tfg.interfaces.PopupButtonsListener;
import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;

public class VistaPopUpBotons extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 939723317900635307L;
	public int boxWidth = 550;
	public int boxHeight = 300;
	private Image img = null;
	private Image boxImg = null;
	private JLabel titolLabel;
	private JLabel textLabel;
	private JTextField nameField;
	private PopupButtonsListener listener;
	
	public VistaPopUpBotons(PopupButtonsListener listener) {
		setOpaque(false);
		setLayout(null);
		this.listener = listener;
		setBounds(0, 0, Constants.width, Constants.height);
		addLabel();
		addNameField();
		addButtons();
		addBackgroundButton();
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
	
	private void addLabel() {
		titolLabel = new JLabel("HOLA");
		titolLabel.setOpaque(false);
//		textLabel.setFont(Constants.fontAnnaVives);
		titolLabel.setFont(Constants.fontTitle);
		titolLabel.setBounds(Constants.centerX-boxWidth/2+40, Constants.centerY-boxHeight/2+40, boxWidth-100, 40);
		
		add(titolLabel);
		
		textLabel = new JLabel("HOLA");
		textLabel.setOpaque(false);
//		textLabel.setFont(Constants.fontAnnaVives);
		textLabel.setFont(Constants.fontKristen);
		textLabel.setBounds(Constants.centerX-boxWidth/2+40, Constants.centerY-boxHeight/2+100, boxWidth-100, boxHeight-265);
		
		add(textLabel);
	}
	
	private void addNameField(){
		nameField = new JTextField();
		nameField.setFont(Constants.fontKristen);
		nameField.setBounds(Constants.centerX-boxWidth/2+40, (int) (textLabel.getLocation().y+textLabel.getSize().getHeight())+15, boxWidth-100, 30);
		nameField.setText(Partida.getInstance().getNom());
		
		add(nameField);
	}
	
	private void addButtons(){
		CustomDefaultButton saveButton = new CustomDefaultButton("GUARDAR");
		saveButton.setBounds(Constants.centerX+boxWidth/2-CustomDefaultButton.BUTTON_WIDTH-40, (int) (nameField.getLocation().y+nameField.getSize().getHeight())+15, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(nameField.getText().equals("") || !listener.saveButtonPressed(nameField.getText())){
					nameField.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else{
					nameField.setBorder(UIManager.getBorder("TextField.border"));
					setVisible(false);
				}
			}
		});
		
		add(saveButton);
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
			textLabel.setText("<html>" + bundle.getString("text_guardar") + "</html>");
			titolLabel.setText(bundle.getString("titol_guardar"));
		}
	}
	
}
