package upc.tfg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;

public class VistaInstruccions extends DefaultView{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1101453571450219200L;
	private static final int MARGIN_LEFT = 85;
	private static final int MARGIN_TOP = (int) (Constants.paddingY + VistaBarraSuperior.BAR_HEIGHT + Constants.height*0.16);
	private VistaAmbBotoTornarListener listener;
	private JLabel instruccions_text_label;
	private JLabel image_label;
	private int pasActual;

	public VistaInstruccions(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		pasActual = 0;
		setSize(Constants.width, Constants.height);
		afegeixBarraSuperior(bundle.getString("instruccions"), listener);
		afegirTextInstruccions();
		afegeixButtons();
		addSkin("backgroundWithWhiteBox.png");
	}
	
	private void afegirTextInstruccions(){
//		JTextArea textArea = new JTextArea(5, 30);
//		String text = bundle.getString("instruccions_text")+ "\n\n" + 
//					bundle.getString("instruccions_pas1")+ "\n\n" +
//					bundle.getString("instruccions_pas2")+ "\n\n" +
//					bundle.getString("instruccions_pas3")+ "\n\n" +
//					bundle.getString("instruccions_pas4")+ "\n\n" +
//					bundle.getString("instruccions_text2");
//		textArea.setText(text);
//		textArea.setFont(Constants.fontKristen);
//		textArea.setLineWrap(true);
//		textArea.setWrapStyleWord(true);
//		JScrollPane scrollView = new JScrollPane(textArea);
//		scrollView.setBounds(MARGIN_LEFT, MARGIN_TOP, (int) (Constants.width*0.65), 450);
//		add(scrollView, BorderLayout.CENTER);
		instruccions_text_label = new JLabel("<html>"+bundle.getString("instruccions_text")+"</html>"); 
		instruccions_text_label.setFont(Constants.fontKristen);
		instruccions_text_label.setBounds(MARGIN_LEFT, MARGIN_TOP, (int)(Constants.width*0.60), 210);
		
		image_label = new JLabel(); 
		image_label.setBounds(MARGIN_LEFT, MARGIN_TOP+instruccions_text_label.getHeight()+25,
				(int)(Constants.width*0.60), 175);
		image_label.setHorizontalAlignment(JLabel.CENTER);

		add(instruccions_text_label);
		add(image_label);
	}
	
	private void afegeixButtons(){
		JButton endevant = new JButton();
		endevant.setBounds((int)(Constants.width*0.60)-70,Constants.paddingY+Constants.height-140,50,50);
		endevant.setFocusPainted(false); 
		endevant.setContentAreaFilled(false); 
		endevant.setBorderPainted(false);
		endevant.setOpaque(false);
		endevant.setIcon(new ImageIcon(getClass().getResource(Constants.fileUrl + "icons/arrow_right_icon.png")));
		endevant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				avancarPag();
			}
		});
		add(endevant);
		
		JButton enderrere = new JButton();
		enderrere.setBounds(MARGIN_LEFT+20,Constants.paddingY+Constants.height-140,50,50);
		enderrere.setFocusPainted(false); 
		enderrere.setContentAreaFilled(false); 
		enderrere.setBorderPainted(false);
		enderrere.setOpaque(false);
		enderrere.setIcon(new ImageIcon(getClass().getResource(Constants.fileUrl + "icons/arrow_left_icon.png")));
		enderrere.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				retPag();
			}
		});
		add(enderrere);
	}
	
	private void setText(int pas){
		image_label.setIcon(null);
		if(pas == 0) instruccions_text_label.setText("<html>"+bundle.getString("instruccions_text")+"</html>");
		else if (pas == 5) instruccions_text_label.setText("<html>"+bundle.getString("instruccions_text2")+"</html>");
		else if (pas == 6) instruccions_text_label.setText("<html>"+bundle.getString("text_calcular_puntuacions")+"</html>");
		else {
			image_label.setIcon(new ImageIcon(getClass().getResource(Constants.fileUrl+"info_img_pas" + pas + ".png")));
			instruccions_text_label.setText("<html>"+bundle.getString("instruccions_pas"+pas)+"</html>");
		}
	}
	
	private void avancarPag(){
		if(pasActual < 6){
			++pasActual;
			setText(pasActual);
		}
	}
	
	private void retPag(){
		if(pasActual > 0){
			--pasActual;
			setText(pasActual);
		}
	}
	
	public void showCalcularPuntacionsText(){
		pasActual = 6;
		setText(pasActual);
	}
	
	public void setVisible(boolean aFlag){
		super.setVisible(aFlag);
		if(aFlag){
			pasActual = 0;
			setText(pasActual);
		}
	}
}
