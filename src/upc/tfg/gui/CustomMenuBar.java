package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

	public CustomMenuBar(final MenuBarListener listener) {
		ImageIcon icon = new ImageIcon(getClass().getResource(Constants.fileUrl+"exit.png"));

        JMenu file = new JMenu("File");
        file.setFont(Constants.fontGillSansBold);
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit", icon);
        eMenuItem.setBackground(Constants.colorGreen);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        JMenuItem eMenuItem2 = new JMenuItem("Tornar al menú", icon);
        eMenuItem2.setBackground(Constants.colorGreen);
        eMenuItem2.setMnemonic(KeyEvent.VK_R);
        eMenuItem2.setToolTipText("");
        eMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.tornarAlMenu();
            }
        });
        
        ImageIcon icon3 = new ImageIcon(getClass().getResource(Constants.fileUrl+"icons/"+"undo_icon.png"));
        JMenuItem eMenuItem3 = new JMenuItem("Desfer jugada", icon3);
        eMenuItem3.setBackground(Constants.colorGreen);
        eMenuItem3.setMnemonic(KeyEvent.VK_Z);
        eMenuItem3.setToolTipText("");
        eMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.desferJugada();
            }
        });
        
        file.add(eMenuItem3);
        file.add(eMenuItem2);
        file.add(eMenuItem);

        add(file);
        setBackground(Constants.colorGreen);
        setFont(Constants.fontGillSansBold);
	}
}
