package upc.tfg.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import upc.tfg.logic.Partida;

public class PartidaBD {
	private static final String FILENAME = "Partides.txt";
	
	public PartidaBD() {
		
	}
	
	public void guardarPartida(Partida partida){

	}
	
	public void getPartides(){
		
	}
	
	private void printPartidaInFile(String filename, Partida partida)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			writer.println(partida.getNom() +",");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, Integer> readPuntuacions()
	{
		Map<String, Integer> puntuacio = new HashMap<String, Integer>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("./src/"+Constants.fileTextsUrl+FILENAME));
		    String line = br.readLine();
		    int i = 0;
		    while (line != null) {
		    	++i;
		        String[] nomPunts = line.split(",");
		        puntuacio.put(nomPunts[0], Integer.valueOf(nomPunts[1]));
		        line = br.readLine();
		    }
		    br.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
				e.printStackTrace();
		}
		
		return puntuacio;
	}
}
