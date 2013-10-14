package upc.tfg.gui;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;

import upc.tfg.logic.Districte;
import upc.tfg.utils.Constants;

public class VistaInformacio extends TransparentView {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7414583561006916256L;
	public static final int INFORMATION_WIDTH = 160;
	public static final int INFORMATION_HEIGHT = Constants.height - VistaEstat.ESTAT_HEIGHT;
	public static final int originPassejantX = INFORMATION_WIDTH/2 - 95/2;
	
	private JLabel nomDistricte;
	private Districte districte;
	private Districte draggingDistrict;
	public VistaPassejant vpBlau;
	public VistaPassejant vpVermell;
	public VistaPassejant vpGroc;
	public VistaPassejant vpVerd;
	private VistaPassejant[] passejants = {vpBlau, vpVermell, vpVerd, vpGroc};
	
	public VistaPassejant vpBlauDinamic;
	public VistaPassejant vpVermellDinamic;
	public VistaPassejant vpGrocDinamic;
	public VistaPassejant vpVerdDinamic;
	private VistaPassejant[] passejantsDinamics = {vpBlauDinamic, vpVermellDinamic, vpVerdDinamic, vpGrocDinamic};
	private boolean draggingPassejant = false;
	
	public VistaInformacio() {
		setLayout(null);
		setOpaque(false);
		//setBackground(new Color(0,0,0,94));
		
		//Afegim els labels
		nomDistricte = new JLabel();
		nomDistricte.setLayout(null);
		nomDistricte.setBounds(5, 5, INFORMATION_WIDTH-10, 40);
		nomDistricte.setFont(Constants.fontBradleyBig);
		nomDistricte.setForeground(Color.WHITE);
		nomDistricte.setOpaque(false);
		nomDistricte.setHorizontalAlignment(JLabel.CENTER);
		nomDistricte.setVerticalAlignment(JLabel.CENTER);
		nomDistricte.setAlignmentY((float) 0.5);
		add(nomDistricte);
		
		//Afegim els passejants
		addPassejants();
		repaint();
	}
	
	public void setDistricte(Districte districte){
		this.districte = districte;
		setNomDistricte(districte.getNom());
		update();
	}
	
	public void setNomDistricte(String nom) {
		nomDistricte.setText("<html>"+nom+"</html>");
	}
	
	public void update(){
		vpBlau.setNum(districte.getNumPassejantsBlaus());
		vpVermell.setNum(districte.getNumPassejantsVermells());
		vpVerd.setNum(districte.getNumPassejantsVerds());
		vpGroc.setNum(districte.getNumPassejantsGrocs());
		
		//Si no estem arrossegant el passejant li posem el numero de passejants del districte, sino si estem
		//arrossegant i estem en el districte que estem arrossegant li restem un.
		if(!vpBlauDinamic.isDraggingPassejant()){
			vpBlauDinamic.setNum(districte.getNumPassejantsBlaus());
			vpBlauDinamic.setBloquejat(!districte.tePassejantsDisponibles(Constants.BLAU));
		}
		else if (districte == draggingDistrict){
			vpBlau.setNum(districte.getNumPassejantsBlaus()-1);
		}
		
		if(!vpVermellDinamic.isDraggingPassejant()){
			vpVermellDinamic.setBloquejat(!districte.tePassejantsDisponibles(Constants.VERMELL));
			vpVermellDinamic.setNum(districte.getNumPassejantsVermells());
		}
		else if (districte == draggingDistrict){
			vpVermell.setNum(districte.getNumPassejantsVermells()-1);
		}
		
		if(!vpVerdDinamic.isDraggingPassejant()){
			vpVerdDinamic.setBloquejat(!districte.tePassejantsDisponibles(Constants.VERD));
			vpVerdDinamic.setNum(districte.getNumPassejantsVerds());
		}
		else if (districte == draggingDistrict){
			vpVerd.setNum(districte.getNumPassejantsVerds()-1);
		}
		
		if(!vpGrocDinamic.isDraggingPassejant()){
			vpGrocDinamic.setNum(districte.getNumPassejantsGrocs());
			vpGrocDinamic.setBloquejat(!districte.tePassejantsDisponibles(Constants.GROC));
		}
		else if (districte == draggingDistrict){
			vpGroc.setNum(districte.getNumPassejantsGrocs()-1);
		}
		
		vpBlau.setBloquejat(!districte.tePassejantsDisponibles(Constants.BLAU));
		vpVerd.setBloquejat(!districte.tePassejantsDisponibles(Constants.VERD));
		vpVermell.setBloquejat(!districte.tePassejantsDisponibles(Constants.VERMELL));
		vpGroc.setBloquejat(!districte.tePassejantsDisponibles(Constants.GROC));
		repaint();
	}
	
	private void addPassejants(){
		vpBlau = new VistaPassejant(VistaPassejant.PASSEJANT_BLAU, 0);
		vpBlau.setEnabled(false);
		Rectangle frame = new Rectangle(originPassejantX, 45, 95, 102);
		vpBlau.setBounds(frame);
		vpBlau.setShowZero(true);
		add(vpBlau);
		
		vpBlauDinamic = new VistaPassejant(VistaPassejant.PASSEJANT_BLAU, 0);
		vpBlauDinamic.setEnabled(true);
		vpBlauDinamic.setShowZero(true);
		
		vpVermell = new VistaPassejant(VistaPassejant.PASSEJANT_VERMELL, 0);
		vpVermell.setEnabled(false);
		frame = new Rectangle(originPassejantX, 145, 95, 102);
		vpVermell.setBounds(frame);
		vpVermell.setShowZero(true);
		add(vpVermell);
		
		vpVermellDinamic = new VistaPassejant(VistaPassejant.PASSEJANT_VERMELL, 0);
		vpVermellDinamic.setEnabled(true);
		vpVermellDinamic.setShowZero(true);
		
		vpGroc = new VistaPassejant(VistaPassejant.PASSEJANT_GROC, 0);
		vpGroc.setEnabled(false);
		frame = new Rectangle(originPassejantX, 245, 95, 102);
		vpGroc.setShowZero(true);
		vpGroc.setBounds(frame);
		add(vpGroc);
		
		vpGrocDinamic = new VistaPassejant(VistaPassejant.PASSEJANT_GROC, 0);
		vpGrocDinamic.setEnabled(true);
		vpGrocDinamic.setShowZero(true);
		
		vpVerd = new VistaPassejant(VistaPassejant.PASSEJANT_VERD, 0);
		vpVerd.setEnabled(false);
		frame = new Rectangle(originPassejantX, 345, 95, 102);
		vpVerd.setShowZero(true);
		vpVerd.setBounds(frame);
		add(vpVerd);
		
		vpVerdDinamic = new VistaPassejant(VistaPassejant.PASSEJANT_VERD, 0);
		vpVerdDinamic.setEnabled(true);
		vpVerdDinamic.setShowZero(true);
	}
	
	public void setVisible(boolean visibility){
		super.setVisible(visibility);
		vpBlauDinamic.setVisible(visibility);
		vpVermellDinamic.setVisible(visibility);
		vpVerdDinamic.setVisible(visibility);
		vpGrocDinamic.setVisible(visibility);
	}

	public boolean isDraggingPassejant() {
		return draggingPassejant;
	}

	public void setDraggingPassejant(boolean draggingPassejant, int color) {
		this.draggingPassejant = draggingPassejant;
		VistaPassejant temp = getVistaPassejant(color);
		temp.setDraggingPassejant(draggingPassejant);
		if(draggingPassejant){
			draggingDistrict = districte;
			temp.setShowZero(false);
		}
		else{
			temp.setShowZero(true);
			draggingDistrict = null;
		}
	}
	
	public VistaPassejant getVistaPassejant(int color){
		switch(color){
			case Constants.BLAU:
				return vpBlauDinamic;
			case Constants.VERMELL:
				return vpVermellDinamic;
			case Constants.VERD:
				return vpVerdDinamic;
			case Constants.GROC:
				return vpGrocDinamic;
			default:
				return null;
		}
	}
	
	public VistaPassejant getVistaPassejantEstatic(int color){
		switch(color){
			case Constants.BLAU:
				return vpBlau;
			case Constants.VERMELL:
				return vpVermell;
			case Constants.VERD:
				return vpVerd;
			case Constants.GROC:
				return vpGroc;
			default:
				return null;
		}
	}

	public void updateView() {
		if(districte != null)update();
	}
}
