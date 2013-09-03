package upc.tfg.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

public class Constants {
	//Paths
	public static final String fileUrl = "/img/";
	
	//Colors
	public static final Color colorGreen = new Color(51, 255, 0);
	
	//Fonts
	public static final Font fontGillSansBold = new Font("Gill Sans MT", Font.BOLD, 12);
	public static final Font fontButton = new Font("Verdana", Font.BOLD, 13);
	
	//Sizes
	public static final int width = Toolkit.getDefaultToolkit().getScreenSize().width;//1024;
	public static final int height = Toolkit.getDefaultToolkit().getScreenSize().height;//768;
	
	//Bounds
	public static int paddingX = (Toolkit.getDefaultToolkit().getScreenSize().width - width)/2;
	public static int paddingY = (Toolkit.getDefaultToolkit().getScreenSize().height - height)/2;
}
