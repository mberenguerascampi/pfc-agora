package upc.tfg.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import upc.tfg.logic.Carta;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;
import upc.tfg.utils.Constants;

public class VistaInfoJugador extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5633074894544770591L;
	public int boxWidth = 225;
	public int boxHeight = 300;
	private Image img = null;
	private Image boxImg = null;
	private JLabel nomLabel;
	private VistaPassejant vistaPassejant;
	private Rectangle jBounds = null;
	private Image imgInfo = null;
	
	public VistaInfoJugador(){
		setOpaque(false);
		setLayout(null);
		setBounds(0, 0, Constants.width, Constants.height);
	}
	
	private void addNomJugador(String nom){
		nomLabel = new JLabel(nom);
		nomLabel.setOpaque(false);
		nomLabel.setFont(Constants.fontKristen);
		nomLabel.setBounds(Constants.centerX-boxWidth/2+30, Constants.centerY-boxHeight/2+30, boxWidth-60, 50);
		nomLabel.setHorizontalAlignment(JLabel.CENTER);
		
		add(nomLabel);
	}
	
	private void addVistaPassejant(Jugador j, boolean agora){
		String color = Partida.getInstance().getNomColor(j.getColor());
		vistaPassejant = new VistaPassejant(color, j.getTotalPassejants());
		if(agora){
			vistaPassejant.setBounds(Constants.centerX-VistaPassejant.PASSEJANT_WIDTH - 5, Constants.centerY-boxHeight/2+100, 
					VistaPassejant.PASSEJANT_WIDTH, VistaPassejant.PASSEJANT_HEIGHT);
		}
		else{
			vistaPassejant.setBounds(Constants.centerX-VistaPassejant.PASSEJANT_WIDTH/2, Constants.centerY-boxHeight/2+100, 
					VistaPassejant.PASSEJANT_WIDTH, VistaPassejant.PASSEJANT_HEIGHT);
		}
		add(vistaPassejant);
	}
	
	private void addCartaAgora(){
		Carta cartaEntity = new Carta("agora", 0, "Agora Barcelona");
		cartaEntity.setShowing(true);
		final VistaCarta carta = new VistaCarta(cartaEntity, 1, 6);
		carta.setBounds((int)(vistaPassejant.getLocation().x + vistaPassejant.getBounds().getWidth() + 15), 
				vistaPassejant.getLocation().y, VistaTauler.CARTA_WIDTH, VistaTauler.CARTA_HEIGHT);
		add(carta);
	}
	
	private void addCanviAgora(){
		JLabel text_agora = new JLabel();
		int x = Constants.centerX-boxWidth/2+15;
		int y = vistaPassejant.getLocation().y + vistaPassejant.getHeight() + 5;
		text_agora.setBounds(x, y, boxWidth-30, 75);
		//text_agora.setBounds(Constants.centerX-boxWidth/2+30, Constants.centerY-boxHeight/2+80, boxWidth-60, 50);
		text_agora.setFont(Constants.fontKristenSmall);
		Locale defaultLocale = Locale.getDefault();
		ResourceBundle bundle = ResourceBundle.getBundle("AgoraBundle", defaultLocale);
		text_agora.setText("<html>" + bundle.getString("canvi_agora") + "</html>");
		text_agora.setHorizontalAlignment(JLabel.CENTER);
		add(text_agora);
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
	    if(jBounds != null && imgInfo != null){
	    	page.drawImage(imgInfo, jBounds.x, jBounds.y, null);
	    }
	    page.drawImage(boxImg, Constants.centerX-boxWidth/2, Constants.centerY-boxHeight/2, null);
	}

	public void showJugador(int id, Rectangle jBounds, Icon icon, boolean nouJugadorAgora) {
		this.jBounds = jBounds;
		this.imgInfo = iconToImage(icon);
		Jugador j = Partida.getInstance().getJugador(id);
		removeAll();
		addNomJugador(j.getNom());
		if(Partida.getInstance().getIdJugadorInici() == id || nouJugadorAgora){
			addVistaPassejant(j, true);
			addCartaAgora();
		}
		else addVistaPassejant(j, false);
		
		if(id != 1 && Partida.getInstance().getIdJugadorInici() == 1 && Partida.getInstance().getPas() == 5){
			addCanviAgora();
		}
		
		super.setVisible(true);
	}
	
	private Image iconToImage(Icon icon)
	{
	    if(icon instanceof ImageIcon)
	    {
	        return ((ImageIcon) icon).getImage();
	    }
	    else
	    {
	        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
	        icon.paintIcon(null, image.getGraphics(), 0, 0);
	        return image;
	    }
	}
}
