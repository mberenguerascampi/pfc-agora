package upc.tfg.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

public class Constants {
	//Paths
	public static final String fileUrl = "/resources/images/";
	public static final String fileTextsUrl = "/resources/text/";
	
	//Colors
	public static final Color colorGreen = new Color(51, 255, 0);
	
	//Fonts
	public static final Font fontGillSansBold = new Font("Gill Sans MT", Font.BOLD, 12);
	public static final Font fontButton = new Font("Verdana", Font.BOLD, 13);
	public static final Font fontCooper = new Font("Cooper Black", Font.BOLD, 14);
	
	//Sizes
	public static final int width = Toolkit.getDefaultToolkit().getScreenSize().width;//1024;
	public static final int height = Toolkit.getDefaultToolkit().getScreenSize().height;//768;
	
	//Bounds
	public static int paddingX = (Toolkit.getDefaultToolkit().getScreenSize().width - width)/2;
	public static int paddingY = (Toolkit.getDefaultToolkit().getScreenSize().height - height)/2;
	
	//Identificadors dels districtes
	public static final int LES_CORTS 			= 3;
	public static final int SARRIA_SANT_GERVASI 	= 4;
	public static final int GRACIA 				= 5;
	public static final int HORTA_GUINARDO 		= 6;
	public static final int NOU_BARIS 			= 7;
	public static final int SANT_ANDREU 			= 8;
	public static final int SANTS_MONTJUIC 		= 9;
	public static final int EIXAMPLE 				= 10;
	public static final int SANT_MARTI 			= 11;
	public static final int CIUTAT_VELLA 			= 12;
	
}
