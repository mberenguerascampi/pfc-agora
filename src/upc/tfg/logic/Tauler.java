package upc.tfg.logic;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import upc.tfg.agora.Agora;
import upc.tfg.utils.Constants;

public class Tauler {
	private Districte[] districtes;
	
	public Tauler() {
		districtes = creaDistrictes();
	}
	
	public static Districte[] creaDistrictes(){
		Districte districtes[] = new Districte[10];
		String[] noms = {"Les Corts", "Sarrià Sant Gervasi", "Gràcia", "Horta Guinardo", "Nou Barris", "Sant Andreu", "Sants Montjuic", "Eixample", "Sant Martí", "Ciutat Vella"};
		int valors[] = {7,8,7,9,6,7,7,10,8,7};
		String[] nomsImatges = {"lesCorts", "sarriaSantGervasi", "gracia", "horta-guinardo", "nouBarris", "santAndreu", "sant_marti", "eixample", "sant_marti", "ciutat_vella"};
		int[] districtesID = {Constants.LES_CORTS, Constants.SARRIA_SANT_GERVASI, Constants.GRACIA, Constants.HORTA_GUINARDO, Constants.NOU_BARIS, Constants.SANT_ANDREU, Constants.SANTS_MONTJUIC, Constants.EIXAMPLE, Constants.SANT_MARTI, Constants.CIUTAT_VELLA};
		
		for (int i = 0; i < 10; ++i){
			//TODO: Afegir imatge districte
			URL urlImg = Agora.class.getResource(Constants.fileUrl+"cartes/"+ nomsImatges[i] + ".png");
			Image img = null;
			try {
				//System.out.println(nomsImatges[i]);
				img = ImageIO.read(urlImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			districtes[i] = new Districte(noms[i], valors[i], img, districtesID[i]);
		}
		return districtes;
	}
	
	public static Districte getDistricte(String nom){
		Districte[] districtes = creaDistrictes();
		for(Districte districte:districtes){
			if(districte.getNom().equalsIgnoreCase(nom)){
				return districte;
			}
		}
		return null;
	}
	
	public void reiniciar(){
		
	}
	
	//Getters & setters

	public Districte[] getDistrictes() {
		return districtes;
	}

	public void setDistrictes(Districte[] districtes) {
		this.districtes = districtes;
	}
}
