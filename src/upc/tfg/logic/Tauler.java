package upc.tfg.logic;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import upc.tfg.agora.Agora;
import upc.tfg.utils.Constants;

/**
 * Classe que representa l'entitat del tauler
 * @author Marc
 *
 */
public class Tauler {
	private static Districte[] districtes = null;
	
	/**
	 * Constructora de la classe
	 */
	public Tauler() {
		districtes = creaDistrictes();
	}
	
	/**
	 * Funci� per obtenir els districtes per primera vegada
	 * @return Array amb tots els districtes
	 */
	public static Districte[] creaDistrictes(){
		Districte districtes[] = new Districte[10];
		String[] noms = {"Les Corts", "Sarri� Sant Gervasi", "Gr�cia", "Horta Guinardo", "Nou Barris", "Sant Andreu", "Sants Montjuic", "Eixample", "Sant Mart�", "Ciutat Vella"};
		int valors[] = {7,8,7,9,6,7,7,10,8,7};
		String[] nomsImatges = {"lesCorts", "sarriaSantGervasi", "gracia", "horta-guinardo", "nouBarris", "santAndreu", "sant_marti", "eixample", "sant_marti", "ciutat_vella"};
		int[] districtesID = {Constants.LES_CORTS, Constants.SARRIA_SANT_GERVASI, Constants.GRACIA, Constants.HORTA_GUINARDO, Constants.NOU_BARIS, Constants.SANT_ANDREU, Constants.SANTS_MONTJUIC, Constants.EIXAMPLE, Constants.SANT_MARTI, Constants.CIUTAT_VELLA};
		
		for (int i = 0; i < 10; ++i){
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
	
	/**
	 * Funci� per obtenir un determinat districte
	 * @param nom Nom de districte que volem obtenir
	 * @return El Districte a obtenir
	 */
	public static Districte getDistricte(String nom){
		if(districtes == null)districtes = creaDistrictes();
		for(Districte districte:districtes){
			if(districte.getNom().equalsIgnoreCase(nom)){
				return districte;
			}
		}
		return null;
	}
	
	//Getters & setters

	public Districte[] getDistrictes() {
		return districtes;
	}

	public static void setDistrictes(Districte[] districtes) {
		Tauler.districtes = districtes;
	}
}
