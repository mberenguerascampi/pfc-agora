package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import upc.tfg.interfaces.MenuBarListener;
import upc.tfg.utils.Constants;

public class CustomMenuBar extends JMenuBar{

	/**
	 * Menú superior 
	 */
	private static final long serialVersionUID = -4255834552318081441L;
	MenuBarListener listener;
	Locale defaultLocale = Locale.getDefault();
	public ResourceBundle bundle = ResourceBundle.getBundle("AgoraBundle", defaultLocale);

	/**
	 * Constructora de la classe
	 * @param listener Listener que s'encarregan d'escoltar les accions que l'usuari faci a la vista
	 */
	public CustomMenuBar(final MenuBarListener listener) {
		this.listener = listener;
		afegeixPestanyaPartida();
		afegeixPestanyaPuntuacio();
		afegeixPestanyaAjuda();
        setBackground(Constants.colorGreen);
        setFont(Constants.fontGillSansBold);
	}
	
	private void afegeixPestanyaPartida(){
		ImageIcon icon = new ImageIcon(getClass().getResource(Constants.fileUrl+"exit.png"));

        JMenu partida = new JMenu(bundle.getString("partida"));
        partida.setFont(Constants.fontGillSansBold);
        partida.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem(bundle.getString("sortir"), icon);
        eMenuItem.setBackground(Constants.colorGreen);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.exitApplication();
            }
        });
        
        ImageIcon icon2 = new ImageIcon(getClass().getResource(Constants.fileUrl+"icons/"+"menu_icon.png"));
        JMenuItem eMenuItem2 = new JMenuItem(bundle.getString("tornar"), icon2);
        eMenuItem2.setBackground(Constants.colorGreen);
        eMenuItem2.setMnemonic(KeyEvent.VK_R);
        eMenuItem2.setToolTipText("");
        eMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.acabarPartidaiTornarAlMenu();
            }
        });
        
        ImageIcon icon3 = new ImageIcon(getClass().getResource(Constants.fileUrl+"icons/"+"undo_icon.png"));
        JMenuItem eMenuItem3 = new JMenuItem(bundle.getString("desfer"), icon3);
        eMenuItem3.setBackground(Constants.colorGreen);
        eMenuItem3.setMnemonic(KeyEvent.VK_Z);
        eMenuItem3.setToolTipText("");
        eMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.desferJugada();
            }
        });
        
        ImageIcon icon4 = new ImageIcon(getClass().getResource(Constants.fileUrl+"icons/"+"save_icon.png"));
        JMenuItem eMenuItem4 = new JMenuItem(bundle.getString("guardar"), icon4);
        eMenuItem4.setBackground(Constants.colorGreen);
        eMenuItem4.setMnemonic(KeyEvent.VK_S);
        eMenuItem4.setToolTipText("");
        eMenuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.mostraPopupGuardar();
            }
        });
        
        ImageIcon icon5 = new ImageIcon(getClass().getResource(Constants.fileUrl+"icons/"+"open_icon.png"));
        JMenuItem eMenuItem5 = new JMenuItem(bundle.getString("carregar_menu"), icon5);
        eMenuItem5.setBackground(Constants.colorGreen);
        eMenuItem5.setMnemonic(KeyEvent.VK_O);
        eMenuItem5.setToolTipText("");
        eMenuItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.carregarPartida();
            }
        });
        
        ImageIcon icon6 = new ImageIcon(getClass().getResource(Constants.fileUrl+"icons/"+"new_icon.png"));
        JMenuItem eMenuItem6 = new JMenuItem(bundle.getString("crear"), icon6);
        eMenuItem6.setBackground(Constants.colorGreen);
        eMenuItem6.setMnemonic(KeyEvent.VK_S);
        eMenuItem6.setToolTipText("");
        eMenuItem6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.crearPartida();
            }
        });
       
        partida.add(eMenuItem6);
        partida.add(eMenuItem5);
        partida.add(eMenuItem4);
        partida.add(eMenuItem3);
        partida.add(eMenuItem2);
        partida.add(eMenuItem);

        add(partida);
	}
	
	private void afegeixPestanyaPuntuacio(){
		ImageIcon icon = new ImageIcon(getClass().getResource(Constants.fileUrl+"icons/"+"highscores_icon.png"));

        JMenu puntuacio = new JMenu(bundle.getString("puntuacio"));
        puntuacio.setFont(Constants.fontGillSansBold);
        puntuacio.setMnemonic(KeyEvent.VK_P);

        JMenuItem eMenuItem = new JMenuItem(bundle.getString("veure_puntuacio"), icon);
        eMenuItem.setBackground(Constants.colorGreen);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.mostraPuntuacionsTemporals();
            }
        });
        
        ImageIcon icon2 = new ImageIcon(getClass().getResource(Constants.fileUrl+"icons/"+"rules_icon.png"));

        JMenuItem eMenuItem2 = new JMenuItem(bundle.getString("veure_normesP"), icon2);
        eMenuItem2.setBackground(Constants.colorGreen);
        eMenuItem2.setMnemonic(KeyEvent.VK_R);
        eMenuItem2.setToolTipText("");
        eMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	listener.mostraCalculPuntuacions();
            }
        });

        puntuacio.add(eMenuItem);
        puntuacio.add(eMenuItem2);

        add(puntuacio);
		
	}
	
	private void afegeixPestanyaAjuda(){
		ImageIcon icon = new ImageIcon(getClass().getResource(Constants.fileUrl+"icons/"+"help_icon.png"));

        JMenu ajuda = new JMenu(bundle.getString("ajuda"));
        ajuda.setFont(Constants.fontGillSansBold);
        ajuda.setMnemonic(KeyEvent.VK_P);

        JMenuItem eMenuItem = new JMenuItem(bundle.getString("veure_ajuda"), icon);
        eMenuItem.setBackground(Constants.colorGreen);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.mostraAjuda();
            }
        });

        ajuda.add(eMenuItem);

        add(ajuda);
	}
}
