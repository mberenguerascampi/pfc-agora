package upc.tfg.utils;

import upc.tfg.logic.Districte;

public class PassejantsAMoure {
	public int color;
	public Districte districteOrigen;
	public Districte districteDesti;
	
	public PassejantsAMoure(int color, Districte districteOrigen, Districte districteDesti) {
		this.color = color;
		this.districteOrigen = districteOrigen;
		this.districteDesti = districteDesti;
	}
}
