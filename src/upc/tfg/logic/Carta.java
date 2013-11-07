package upc.tfg.logic;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import upc.tfg.utils.CartesBD;
import upc.tfg.utils.Constants;

/**
 * Classe que representa la entitat d'una carta
 * @author Marc
 *
 */
public class Carta {
	private String nom = "";
	private int valor;
	private boolean showing;
	private Image image;
	private Districte districte;
	
	/**
	 * Constructor de la classe
	 */
	public Carta() {
		
	}
	
	/**
	 * Constructor de la classe
	 * @param nom Nom de la carta (coincideix amb el nom del fitxer amb la imatge)
	 * @param valor Valor que té la carta
	 * @param nomcomplert Nom complet que es vol mostrar de la carta
	 */
	public Carta(String nom, int valor, String nomcomplert) {
		this.nom = nomcomplert;
		this.valor = valor;
		districte = Tauler.getDistricte(CartesBD.mapdDistrictes.get(nom.subSequence(0, 2)));
		URL urlImg = getClass().getResource(Constants.fileUrl+"cartes/"+ nom + ".png");
		try {
			this.image = ImageIO.read(urlImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Public Methods
	/**
	 * Acció que gira la carta de maner que si estava visible deixa de estar-ho i a l'inrevés.
	 */
	public void girar(){
		showing = !showing;
	}

	
	//Getters & Setters
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public boolean isShowing() {
		return showing;
	}

	public void setShowing(boolean showing) {
		this.showing = showing;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Districte getDistricte() {
		return districte;
	}

	public void setDistricte(Districte districte) {
		this.districte = districte;
	}
}
