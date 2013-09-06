package upc.tfg.utils;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageToNumberArray {
	public int pix[][];
	
	public ImageToNumberArray() throws IOException {
		URL urlTaulerImg = getClass().getResource(Constants.fileUrl+"tauler_img_border.png");
		BufferedImage img = ImageIO.read(urlTaulerImg);
		pix = new int[img.getHeight()][img.getWidth()];
		int rgb,value;
		for (int y = 0; y < img.getHeight(); ++y) {
		     for (int x = 0; x < img.getWidth(); ++x) {
		         rgb = img.getRGB(x,y);
		         if (rgb == 0xff000000){          //if black
		        	  value = 0;
                 }
                 else if (rgb == 0xffffffff){     //if white
                	 value = 1;
                 }
                 else value = 2;     
		         pix[y][x] = value;
		     }
		}
		
		printInFile("prova.txt");
        
        
     }
	
	public void printInFile(String filename)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			for(int i = 0; i < pix.length; ++i) {
	            for(int j = 0; j < pix[i].length; ++j) {
	            	writer.print(pix[i][j]);
	            }
	            writer.println();
	        }

	        writer.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
