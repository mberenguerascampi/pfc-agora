package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import upc.tfg.interfaces.MenuPrincipalListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;

public class VistaMenuPrincipal extends DefaultView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MenuPrincipalListener listener;
	int buttons_paddingX = Constants.width/2 - CustomDefaultButton.BUTTON_WIDTH/2;
	int buttons_paddingY = 200;
	CustomDefaultButton[] buttons;
	
	public VistaMenuPrincipal(MenuPrincipalListener aListener) {
		listener = aListener;
		setLayout(null);
		setSize(Constants.width, Constants.height);
		addButtons();
		setBackgroundName("PortadaGif.gif");
		//addSkin("imatgePortada.jpg");
	}
	
	private void addButtons()
	{
		buttons = new CustomDefaultButton[6];
		for (int i = 0; i < buttons.length; ++i) {
			CustomDefaultButton button = new CustomDefaultButton(getButtonText(i));
			button.setLayout(null);
			int x = Constants.paddingX + buttons_paddingX;
			int y = Constants.paddingY + buttons_paddingY + i*(CustomDefaultButton.BUTTON_HEIGHT+15);
			button.setBounds(x, y, CustomDefaultButton.BUTTON_WIDTH, CustomDefaultButton.BUTTON_HEIGHT);
			final int position = i;
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonPressed(position);
				}
			});  
			buttons[i] = button;
			add(button);
		}
	}
	
	private String getButtonText(int position)
	{
		switch (position) {
			case 0:
				return bundle.getString("començar");
			case 1:
				return bundle.getString("carregar");
			case 2:
				return bundle.getString("puntuacions");
			case 3:
				return bundle.getString("idioma");
			case 4:
				return bundle.getString("instruccions");
			case 5:
				return bundle.getString("sobre");
			default:
				return "";
		}
	}
	
	private void buttonPressed(int position)
	{
		switch (position) {
			case 0:
				listener.playPressed();
				break;
			case 1:
				listener.loadButtonPressed();
				break;
			case 2:
				listener.highscoresButtonPressed();
				break;
			case 3:
				listener.languageButtonPressed();
				break;
			case 4:
				listener.instructionsButtonPressed();
				break;
			case 5:
				listener.aboutButtonPressed();
				break;
			default:
				break;
		}
	}
	
	public void updateView(){
		updateBundle();
		for (int i = 0; i < buttons.length; ++i){
			buttons[i].setText(getButtonText(i));
		}
	}
}
