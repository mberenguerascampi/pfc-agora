package upc.tfg.agora;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import upc.tfg.utils.Constants;

public class PantallaPrincipalGui extends JFrame {
	
	private TaulerGui tauler;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PantallaPrincipalGui() {
		this.initFrame();
		this.initPanel();
		this.initMenu();
	}
	
	private void initFrame(){
		setTitle("Àgora Barcelona");
		setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(768, 1024);
		
		URL urlIconImg = getClass().getResource(Constants.fileUrl+"bb.png");
		Image icon =  new ImageIcon(urlIconImg).getImage();
		setIconImage(icon);
	}
	
	private void initPanel(){
		tauler = new TaulerGui();
		
		//this.add(this.tauler);
		this.setContentPane(this.tauler);
	}
	
	private void initMenu(){
		JMenuBar menubar = new JMenuBar();
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

        file.add(eMenuItem);

        menubar.add(file);
        menubar.setBackground(Constants.colorGreen);
        menubar.setFont(Constants.fontGillSansBold);

        setJMenuBar(menubar);
	}

	public static void main(String[] args) {
		new PantallaPrincipalGui();
	}
}
