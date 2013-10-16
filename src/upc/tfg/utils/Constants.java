package upc.tfg.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {
	//Paths
	public static final String fileUrl = "/resources/images/";
	public static final String fileTextsUrl = "/resources/text/";
	public static final String fileAudioUrl = "/resources/audio/";
	public static final String fileFontUrl = "/resources/font/";
	
	//Colors
	public static final Color colorGreen = new Color(51, 255, 0);
	
	//Fonts
	public static final Font fontGillSansBold = new Font("Gill Sans MT", Font.BOLD, 12);
	public static final Font fontButton = new Font("Kristen ITC", Font.CENTER_BASELINE, 11);//("Verdana", Font.BOLD, 13);
	public static final Font fontPassejants = new Font("Kristen ITC", Font.BOLD, 32);
	public static final Font fontPassejantsBig = new Font("Kristen ITC", Font.BOLD, 35);
	public static final Font fontCooper = new Font("Cooper Black", Font.BOLD, 14);
	public static final Font fontBradleyBig = new Font("Bradley Hand ITC", Font.BOLD, 18);
	public static final Font fontArialRoundedBig = new Font("Arial Rounded MT Bold", Font.BOLD, 18);
	public static final Font fontKristen = new Font("Kristen ITC", Font.BOLD, 16);
	public static final Font fontKristenSmall = new Font("Kristen ITC", Font.CENTER_BASELINE, 14);
	public static final Font fontPlayerWinner = new Font("Kristen ITC", Font.BOLD, 38);
	public static final Font fontPlayersNames = new Font("Kristen ITC", Font.BOLD, 22);
	public static final Font fontDescription = new Font("Kristen ITC", Font.ITALIC, 25);
	public static final Font fontAnnaVives = new Font("Anna", Font.BOLD, 20);
	
	//Sizes
	public static final int width = Toolkit.getDefaultToolkit().getScreenSize().width;//1024;
	public static final int height = Toolkit.getDefaultToolkit().getScreenSize().height;//768;
	
	//Bounds
	public static int paddingX = (Toolkit.getDefaultToolkit().getScreenSize().width - width)/2;
	public static int paddingY = (Toolkit.getDefaultToolkit().getScreenSize().height - height)/2;
	
	//Center
	public static final int centerX = width/2 + paddingX;
	public static final int centerY = height/2 + paddingY;
	
	//Identificadors dels districtes
	public static final int LES_CORTS 				= 3;
	public static final int SARRIA_SANT_GERVASI 	= 4;
	public static final int GRACIA 					= 5;
	public static final int HORTA_GUINARDO 			= 6;
	public static final int NOU_BARIS 				= 7;
	public static final int SANT_ANDREU 			= 8;
	public static final int SANTS_MONTJUIC 			= 9;
	public static final int EIXAMPLE 				= 10;
	public static final int SANT_MARTI 				= 11;
	public static final int CIUTAT_VELLA 			= 12;
	
	//Identificadors colors
	public static final int BLAU 		= 1;
	public static final int VERMELL 	= 2;
	public static final int GROC	 	= 3;
	public static final int VERD	 	= 4;
	public static final int[] COLORS    = {BLAU, VERMELL, VERD, GROC};
	
	//Graf dels districtes adjacents
	private static final List<Integer> valuesCorts = new ArrayList<Integer>(){{
		add(SANTS_MONTJUIC);
		add(SARRIA_SANT_GERVASI);
		add(EIXAMPLE);
	}};
	private static final List<Integer> valuesSants = new ArrayList<Integer>(){{
		add(LES_CORTS);
		add(EIXAMPLE);
		add(CIUTAT_VELLA);
	}};
	private static final List<Integer> valuesSarria = new ArrayList<Integer>(){{
		add(LES_CORTS);
		add(EIXAMPLE);
		add(GRACIA);
		add(HORTA_GUINARDO);
	}};
	private static final List<Integer> valuesEixample = new ArrayList<Integer>(){{
		add(SANTS_MONTJUIC);
		add(LES_CORTS);
		add(SARRIA_SANT_GERVASI);
		add(GRACIA);
		add(HORTA_GUINARDO);
		add(SANT_MARTI);
		add(CIUTAT_VELLA);
	}};
	private static final List<Integer> valuesCiutatVella = new ArrayList<Integer>(){{
		add(SANTS_MONTJUIC);
		add(EIXAMPLE);
		add(SANT_MARTI);
	}};
	private static final List<Integer> valuesGracia = new ArrayList<Integer>(){{
		add(SARRIA_SANT_GERVASI);
		add(HORTA_GUINARDO);
		add(EIXAMPLE);
	}};
	private static final List<Integer> valuesHorta = new ArrayList<Integer>(){{
		add(SARRIA_SANT_GERVASI);
		add(GRACIA);
		add(NOU_BARIS);
		add(SANT_ANDREU);
		add(EIXAMPLE);
		add(SANT_MARTI);
	}};
	private static final List<Integer> valuesSantMarti = new ArrayList<Integer>(){{
		add(CIUTAT_VELLA);
		add(EIXAMPLE);
		add(HORTA_GUINARDO);
		add(SANT_ANDREU);
	}};
	private static final List<Integer> valuesNouBarris = new ArrayList<Integer>(){{
		add(HORTA_GUINARDO);
		add(SANT_ANDREU);
	}};
	private static final List<Integer> valuesSantAndreu = new ArrayList<Integer>(){{
		add(NOU_BARIS);
		add(HORTA_GUINARDO);
		add(SANT_MARTI);
	}};
	public static final HashMap<Integer,List<Integer>> grafDistrictes  = new HashMap<Integer, List<Integer>>(){{
        put(LES_CORTS, valuesCorts);
        put(SANTS_MONTJUIC, valuesSants);
        put(SARRIA_SANT_GERVASI, valuesSarria);
        put(EIXAMPLE, valuesEixample);
        put(CIUTAT_VELLA, valuesCiutatVella);
        put(GRACIA, valuesGracia);
        put(HORTA_GUINARDO, valuesHorta);
        put(SANT_MARTI, valuesSantMarti);
        put(NOU_BARIS, valuesNouBarris);
        put(SANT_ANDREU, valuesSantAndreu);
    }};
	
}
