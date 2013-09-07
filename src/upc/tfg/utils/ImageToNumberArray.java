package upc.tfg.utils;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class ImageToNumberArray {
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	public static final int OTHER = 2;
	
	private static int TOTAL_DISTRICTS = 10;
	
	public int pix[][];
	
	public ImageToNumberArray() throws IOException {
		URL urlTaulerImg = getClass().getResource(Constants.fileUrl+"Barcelona_districtes_dividits.png");
		BufferedImage img = ImageIO.read(urlTaulerImg);
		pix = new int[img.getHeight()][img.getWidth()];
		int rgb,value;
		for (int y = 0; y < img.getHeight(); ++y) {
		     for (int x = 0; x < img.getWidth(); ++x) {
		         rgb = img.getRGB(x,y);
		         if (rgb == 0xff000000){          //if black
		        	  value = BLACK;
                 }
                 else if (rgb == 0xffffffff){     //if white
                	 value = WHITE;
                 }
                 else value = OTHER;     
		         pix[y][x] = value;
		     }
		}
		
		//Modifiquem la matriu de manera que per cada districte ens quedi un numero diferent assignat
		splitDistricts();
		
		printInFile("map_districtes.txt");
        
        
     }
	
	private void splitDistricts()
	{
		int districtNum = OTHER + 1;
		for (int i = 0; i < pix.length; ++i){
			for (int j = 0; j < pix[0].length; ++j){
				if(pix[i][j] == WHITE){
					fillDistrict(new Posicio(i,j), districtNum);
					++districtNum;
					if(districtNum > TOTAL_DISTRICTS+OTHER) return;
				}
			}
		}
	}
	
	private void fillDistrict(Posicio pos, int districtNum)
	{
		//Fem un recorregut en amplada per ompli tot el districte amb el número corresponent
		Queue<Posicio> cells = new ArrayDeque<Posicio>();
		cells.add(pos);
		pix[pos.x][pos.y] = districtNum;
		while(!cells.isEmpty()) {
			//System.out.println(cells.size());
			pos = cells.remove();
			if(pos.x - 1 >= 0 && pix[pos.x-1][pos.y] == OTHER){
				pix[pos.x-1][pos.y] = districtNum;
				cells.add(new Posicio(pos.x-1, pos.y));
			}
			if(pos.x + 1 < pix.length && pix[pos.x+1][pos.y] == OTHER){
				pix[pos.x+1][pos.y] = districtNum;
				cells.add(new Posicio(pos.x+1, pos.y));
			}
			if(pos.y - 1 >= 0 && pix[pos.x][pos.y-1] == OTHER){
				pix[pos.x][pos.y-1] = districtNum;
				cells.add(new Posicio(pos.x, pos.y-1));
			}
			if(pos.y + 1 < pix[0].length && pix[pos.x][pos.y+1] == OTHER){
				pix[pos.x][pos.y+1] = districtNum;
				cells.add(new Posicio(pos.x, pos.y+1));
			}
		}
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
