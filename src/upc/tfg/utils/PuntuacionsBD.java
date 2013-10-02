package upc.tfg.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;

public class PuntuacionsBD {
	public PuntuacionsBD() {
		// TODO Auto-generated constructor stub
	}
	
	public void guardarPuntuacio(ResultatsFinals resultats){
		Jugador j = Partida.getInstance().getJugador(resultats.getIdJugadorGuanyador());
		int punts = 0;
		if(resultats.getIdJugadorGuanyador() == 1)punts = resultats.getPuntsJ1();
		if(resultats.getIdJugadorGuanyador() == 2)punts = resultats.getPuntsJ2();
		if(resultats.getIdJugadorGuanyador() == 3)punts = resultats.getPuntsJ3();
		if(resultats.getIdJugadorGuanyador() == 4)punts = resultats.getPuntsJ4();
		printInFile("", j.getNom(), punts);
	}
	
	public void getPuntuacio(){
		
	}
	
	public void printInFile(String filename, String name, int punts)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			writer.println(name +"," + punts);
	        writer.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void readMapMatrix()
	{
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("./src/txt/map_districtes.txt"));
		    String line = br.readLine();
		    int i = 0;
		    while (line != null) {
		    	++i;
		        line = br.readLine();
		    }
		    br.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
				e.printStackTrace();
		}
	}

}
