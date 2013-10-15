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
	 * 
	 */
	private static final long serialVersionUID = -4255834552318081441L;
	MenuBarListener listener;
	Locale defaultLocale = Locale.getDefault();
	public ResourceBundle bundle = ResourceBundle.getBundle("AgoraBundle", defaultLocale);

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
                System.exit(0);
            }
        });
        JMenuItem eMenuItem2 = new JMenuItem(bundle.getString("tornar"), icon);
        eMenuItem2.setBackground(Constants.colorGreen);
        eMenuItem2.setMnemonic(KeyEvent.VK_R);
        eMenuItem2.setToolTipText("");
        eMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.tornarAlMenu();
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
        
        partida.add(eMenuItem3);
        partida.add(eMenuItem2);
        partida.add(eMenuItem);

        add(partida);
	}
	
	private void afegeixPestanyaPuntuacio(){
		ImageIcon icon = new ImageIcon(getClass().getResource(Constants.fileUrl+"exit.png"));

        JMenu puntuacio = new JMenu(bundle.getString("puntuacio"));
        puntuacio.setFont(Constants.fontGillSansBold);
        puntuacio.setMnemonic(KeyEvent.VK_P);

        JMenuItem eMenuItem = new JMenuItem(bundle.getString("veure_puntuacio"), icon);
        eMenuItem.setBackground(Constants.colorGreen);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        JMenuItem eMenuItem2 = new JMenuItem(bundle.getString("veure_normesP"), icon);
        eMenuItem2.setBackground(Constants.colorGreen);
        eMenuItem2.setMnemonic(KeyEvent.VK_R);
        eMenuItem2.setToolTipText("");
        eMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.tornarAlMenu();
            }
        });

        puntuacio.add(eMenuItem);
        puntuacio.add(eMenuItem2);

        add(puntuacio);
		
	}
	
	private void afegeixPestanyaAjuda(){
		ImageIcon icon = new ImageIcon(getClass().getResource(Constants.fileUrl+"exit.png"));

        JMenu ajuda = new JMenu(bundle.getString("ajuda"));
        ajuda.setFont(Constants.fontGillSansBold);
        ajuda.setMnemonic(KeyEvent.VK_P);

        JMenuItem eMenuItem = new JMenuItem(bundle.getString("veure_ajuda"), icon);
        eMenuItem.setBackground(Constants.colorGreen);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        ajuda.add(eMenuItem);

        add(ajuda);
	}
}
