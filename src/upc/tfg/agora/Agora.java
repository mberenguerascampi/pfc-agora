package upc.tfg.agora;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import upc.tfg.gui.VistaMenuPrincipal;
import upc.tfg.gui.VistaTauler;
import upc.tfg.interfaces.MenuPrincipalListener;
import upc.tfg.interfaces.TaulerListener;
import upc.tfg.utils.Constants;

public class Agora extends JFrame implements MenuPrincipalListener, TaulerListener, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9046011155156686371L;
	Container contentPane;
	CardLayout cardLayout;
	VistaMenuPrincipal menuPrincipal;
	VistaTauler tauler;
	
	public Agora() {
		contentPane = getContentPane();
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);
		
		initFrame();
		initViews();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// playPressed();
	}
	
	private void initFrame(){
		setTitle("Àgora Barcelona");
		setVisible(true);
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
		setSize(768, 1024);
		setBounds(0, 0, 768, 1024);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setBackground(Color.BLACK);
		
		//Posem una icona a la aplicació
		URL urlIconImg = getClass().getResource(Constants.fileUrl+"bb.png");
		Image icon =  new ImageIcon(urlIconImg).getImage();
		setIconImage(icon);
	}
	
	private void initViews(){
		menuPrincipal = new VistaMenuPrincipal(this);
		menuPrincipal.setVisible(true);
		tauler = new VistaTauler(this);
		tauler.setVisible(false);
		
		contentPane.add(menuPrincipal);
		contentPane.add(tauler);
	}

	@Override
	public void playPressed() {
		System.out.println("You clicked the button");
		 menuPrincipal.setVisible(false);
		 tauler.setVisible(true);
		 //cardLayout.next(contentPane);
	}
}

