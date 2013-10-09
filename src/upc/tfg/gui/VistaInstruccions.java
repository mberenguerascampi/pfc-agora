package upc.tfg.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import upc.tfg.interfaces.VistaAmbBotoTornarListener;
import upc.tfg.utils.Constants;
import upc.tfg.utils.CustomDefaultButton;

public class VistaInstruccions extends DefaultView{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1101453571450219200L;
	private static final int MARGIN_LEFT = 60;
	private static final int MARGIN_TOP = (int) (Constants.paddingY + VistaBarraSuperior.BAR_HEIGHT + Constants.height*0.16);
	private VistaAmbBotoTornarListener listener;

	public VistaInstruccions(VistaAmbBotoTornarListener listener) {
		setLayout(null);
		this.listener = listener;
		setSize(Constants.width, Constants.height);
		afegeixBarraSuperior(bundle.getString("instruccions"), listener);
		afegirTextInstruccions();
		addSkin("backgroundWithWhiteBox.png");
	}
	
	private void afegirTextInstruccions(){
		JTextArea textArea = new JTextArea(5, 30);
		String text = bundle.getString("instruccions_text")+ "\n\n" + 
					bundle.getString("instruccions_pas1")+ "\n\n" +
					bundle.getString("instruccions_pas2")+ "\n\n" +
					bundle.getString("instruccions_pas3")+ "\n\n" +
					bundle.getString("instruccions_pas4")+ "\n\n" +
					bundle.getString("instruccions_text2");
		textArea.setText(text);
		textArea.setFont(Constants.fontKristen);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollView = new JScrollPane(textArea);
		scrollView.setBounds(MARGIN_LEFT, MARGIN_TOP, (int) (Constants.width*0.65), 450);
		add(scrollView, BorderLayout.CENTER);
//		JLabel instruccions_text_label = new JLabel("<html>"+bundle.getString("instruccions_text")+"</html>"); 
//		instruccions_text_label.setFont(Constants.fontKristen);
//		instruccions_text_label.setBounds(MARGIN_LEFT, MARGIN_TOP, Constants.width-MARGIN_LEFT*2, 40);
//		
//		JLabel instruccions_pas1_label = new JLabel("<html>"+bundle.getString("instruccions_pas1")+"</html>"); 
//		instruccions_pas1_label.setFont(Constants.fontKristen);
//		instruccions_pas1_label.setBounds(MARGIN_LEFT, MARGIN_TOP+60, Constants.width-MARGIN_LEFT*2, 90);
//		
//		JLabel instruccions_pas2_label = new JLabel("<html>"+bundle.getString("instruccions_pas2")+"</html>"); 
//		instruccions_pas2_label.setFont(Constants.fontKristen);
//		instruccions_pas2_label.setBounds(MARGIN_LEFT, MARGIN_TOP+150, Constants.width-MARGIN_LEFT*2, 105);
//		
//		JLabel instruccions_pas3_label = new JLabel("<html>"+bundle.getString("instruccions_pas3")+"</html>"); 
//		instruccions_pas3_label.setFont(Constants.fontKristen);
//		instruccions_pas3_label.setBounds(MARGIN_LEFT, MARGIN_TOP+280, Constants.width-MARGIN_LEFT*2, 80);
//		
//		JLabel instruccions_pas4_label = new JLabel("<html>"+bundle.getString("instruccions_pas4")+"</html>"); 
//		instruccions_pas4_label.setFont(Constants.fontKristen);
//		instruccions_pas4_label.setBounds(MARGIN_LEFT, MARGIN_TOP+360, Constants.width-MARGIN_LEFT*2, 80);
//
//		JLabel instruccions_text_label2 = new JLabel("<html>"+bundle.getString("instruccions_text2")+"</html>"); 
//		instruccions_text_label2.setFont(Constants.fontKristen);
//		instruccions_text_label2.setBounds(MARGIN_LEFT, MARGIN_TOP+440, Constants.width-MARGIN_LEFT*2, 80);
//		
//		add(instruccions_text_label);
//		add(instruccions_pas1_label);
//		add(instruccions_pas2_label);
//		add(instruccions_pas3_label);
//		add(instruccions_pas4_label);
//		add(instruccions_text_label2);
	}
}
