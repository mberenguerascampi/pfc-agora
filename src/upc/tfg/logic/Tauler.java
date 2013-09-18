package upc.tfg.logic;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import upc.tfg.utils.Constants;

public class Tauler {
	private Districte[] districtes;
	
	public Tauler() {
		districtes = new Districte[10];
		String[] noms = {"Les Corts", "Sarri� Sant Gervasi", "Gr�cia", "Horta Guinardo", "Nou Barris", "Sant Andreu", "Sants Montjuic", "Eixample", "Sant Mart�", "Ciutat Vella"};
		int valors[] = {7,8,7,9,6,7,7,10,7,8};
		String[] nomsImatges = {"LesCorts", "sarriaSantGervasi", "gracia", "horta-guinardo", "nouBarris", "santAndreu", "sant_marti", "eixample", "sant_marti", "ciutat_vella"};
		int[] districtesID = {Constants.LES_CORTS, Constants.SARRIA_SANT_GERVASI, Constants.GRACIA, Constants.HORTA_GUINARDO, Constants.NOU_BARIS, Constants.SANT_ANDREU, Constants.SANTS_MONTJUIC, Constants.EIXAMPLE, Constants.SANT_MARTI, Constants.CIUTAT_VELLA};
		
		for (int i = 0; i < 10; ++i){
			//TODO: Afegir imatge districte
			URL urlImg = getClass().getResource(Constants.fileUrl+"cartes/"+ nomsImatges[i] + ".png");
			Image img = null;
			try {
				img = ImageIO.read(urlImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			districtes[i] = new Districte(noms[i], valors[i], img, districtesID[i]);
		}
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
