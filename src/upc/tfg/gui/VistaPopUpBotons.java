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
	public int boxWidth2 = 700;
	public int boxHeight = 310;
	private Image img = null;
	private Image boxImg = null;
	private JLabel titolLabel;
	private JLabel textLabel;
	private JLabel errorLabel;
	private JTextField nameField;
	private PopupButtonsListener listener;
	private CustomDefaultButton saveButton;
	private CustomDefaultButton cancelButton;
	private CustomDefaultButton noSaveButton;
	public static final int TIPUS_GUARDAR = 1;
	public static final int TIPUS_PREGUNTA = 2;
	private int tipus = TIPUS_GUARDAR;
	private boolean typeChanged = false;
	private static VistaPopUpBotons instance;
	
	public VistaPopUpBotons(PopupButtonsListener listener) {
		setOpaque(false);
		setLayout(null);
		this.listener = listener;
		setBounds(0, 0, Constants.width, Constants.height);
		addLabel();
		addNameField();
		addLabelError();
		addButtons();
		addBackgroundButton();
		instance = this;
	}
	
	public static VistaPopUpBotons getInstance(){
		return instance;
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
		titolLabel.setVerticalAlignment(JLabel.CENTER);
		titolLabel.setHorizontalAlignment(JLabel.CENTER);
		
		add(titolLabel);
		
		textLabel = new JLabel("HOLA");
		textLabel.setOpaque(false);
//		textLabel.setFont(Constants.fontAnnaVives);
		textLabel.setFont(Constants.fontKristen);
		textLabel.setBounds(Constants.centerX-boxWidth/2+40, titolLabel.getLocation().y + titolLabel.getHeight()+10, boxWidth-100, boxHeight-275);
		
		add(textLabel);
	}
	
	private void addLabelError(){
		errorLabel = new JLabel("HOLA");
		errorLabel.setOpaque(false);
		errorLabel.setFont(Constants.fontKristen);
		errorLabel.setBounds(Constants.centerX-boxWidth/2+40, nameField.getLocation().y+nameField.getHeight()+5, boxWidth-100, 25);
		errorLabel.setVisible(false);
		errorLabel.setForeground(Color.RED);
		
		add(errorLabel);
	}
	
	private void addNameField(){
		nameField = new JTextField();
		nameField.setFont(Constants.fontKristen);
		nameField.setBounds(Constants.centerX-boxWidth/2+40, (int) (textLabel.getLocation().y+textLabel.getSize().getHeight())+15, boxWidth-100, 30);
		nameField.setText(Partida.getInstance().getNom());
		
		add(nameField);
	}
	
	private void addButtons(){
		saveButton = new CustomDefaultButton("GUARDAR");
		saveButton.setBounds(Constants.centerX+boxWidth/2-CustomDefaultButton.BUTTON_WIDTH-40, (int) (errorLabel.getLocation().y+errorLabel.getSize().getHeight())+10, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Si estem en el popup per guardar, intentem guardar
				if(tipus == TIPUS_GUARDAR){
					if(nameField.getText().equals("") || !listener.saveButtonPressed(nameField.getText())){
						nameField.setBorder(BorderFactory.createLineBorder(Color.RED));
					}
					else{
						nameField.setBorder(UIManager.getBorder("TextField.border"));
						setVisible(false);
					}
				}
				else if(tipus == TIPUS_PREGUNTA){
					tipus = TIPUS_GUARDAR;
					setVisible(true);
					repaint();
				}
				
			}
		});
		
		add(saveButton);
		
		cancelButton = new CustomDefaultButton("CANCELAR");
		cancelButton.setBounds(Constants.centerX-boxWidth2/2+40, (int) (errorLabel.getLocation().y+errorLabel.getSize().getHeight())+10, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		add(cancelButton);
		
		noSaveButton = new CustomDefaultButton("NO GUARDAR");
		noSaveButton.setBounds(Constants.centerX-CustomDefaultButton.BUTTON_WIDTH/2, (int) (errorLabel.getLocation().y+errorLabel.getSize().getHeight())+10, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
		noSaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		add(noSaveButton);
	}
	
	public void setNoSaveActionListener(ActionListener al){
		for(ActionListener l: noSaveButton.getActionListeners()) noSaveButton.removeActionListener(l);
		noSaveButton.addActionListener(al);
	}
	
	public void paintComponent(Graphics page)
	{
	    super.paintComponent(page);
	    if(img == null || typeChanged){
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
			typeChanged = false;
	    }
	    page.drawImage(img, 0, 0, null);
	    page.drawImage(boxImg, Constants.centerX-boxWidth/2, Constants.centerY-boxHeight/2, null);
	}
	
	public void setVisible(boolean aFlag){
		super.setVisible(aFlag);
		if(aFlag){
			Locale defaultLocale = Locale.getDefault();
			ResourceBundle bundle = ResourceBundle.getBundle("AgoraBundle", defaultLocale);
			if(tipus == TIPUS_GUARDAR){
				boxWidth = 550;
				textLabel.setBounds(Constants.centerX-boxWidth/2+40, titolLabel.getLocation().y + titolLabel.getHeight()+10, boxWidth-100, boxHeight-275);
				textLabel.setText("<html>" + bundle.getString("text_guardar") + "</html>");
				titolLabel.setText(bundle.getString("titol_guardar"));
				cancelButton.setVisible(false);
				noSaveButton.setVisible(false);
				nameField.setVisible(true);
			}
			else{
				boxWidth = boxWidth2;
				textLabel.setBounds(Constants.centerX-boxWidth/2+40, titolLabel.getLocation().y + titolLabel.getHeight()+20, boxWidth-100, boxHeight-235);
				textLabel.setText("<html>" + bundle.getString("text_noGuardar") + "</html>");
				titolLabel.setText(bundle.getString("titol_noGuardar"));
				cancelButton.setVisible(true);
				noSaveButton.setVisible(true);
				nameField.setVisible(false);
			}
			errorLabel.setVisible(false);
			saveButton.setBounds(Constants.centerX+boxWidth/2-CustomDefaultButton.BUTTON_WIDTH-40, (int) (errorLabel.getLocation().y+errorLabel.getSize().getHeight())+10, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
			typeChanged = true;
			repaint();
		}
	}

	public int getTipus() {
		return tipus;
	}

	public void setTipus(int tipus) {
		this.tipus = tipus;
	}
	
	public void showError(String error){
		errorLabel.setText(error);
		errorLabel.setVisible(true);;
	}
}
