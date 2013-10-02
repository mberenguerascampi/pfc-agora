package upc.tfg.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;

public class PuntuacionsBD {
	private static final String FILENAME = "Puntuacions.txt";
	private static final int NUM_MAX_SCORES = 5;
	
	public PuntuacionsBD() {
	}
	
	public void guardarPuntuacio(ResultatsFinals resultats){
		Jugador j = Partida.getInstance().getJugador(resultats.getIdJugadorGuanyador());
		int punts = 0;
		if(resultats.getIdJugadorGuanyador() == 1)punts = resultats.getPuntsJ1();
		if(resultats.getIdJugadorGuanyador() == 2)punts = resultats.getPuntsJ2();
		if(resultats.getIdJugadorGuanyador() == 3)punts = resultats.getPuntsJ3();
		if(resultats.getIdJugadorGuanyador() == 4)punts = resultats.getPuntsJ4();
		Map<String, Integer> highscores = getMaxScores(resultats);
		printInFile("./src/"+Constants.fileTextsUrl+FILENAME, highscores);
	}
	
	public Map<String, Integer> getPuntuacio(){
		return readPuntuacions();
	}
	
	private Map<String, Integer> getMaxScores(ResultatsFinals resultats){
		Map<String, Integer> scores = getPuntuacio();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = "_" + dateFormat.format(Partida.getInstance().getData());
		scores.put(Partida.getInstance().getNomJugador(1) + date, resultats.getPuntsJ1());
		scores.put(Partida.getInstance().getNomJugador(2) + date, resultats.getPuntsJ2());
		scores.put(Partida.getInstance().getNomJugador(3) + date, resultats.getPuntsJ3());
		scores.put(Partida.getInstance().getNomJugador(4) + date, resultats.getPuntsJ4());
		Map<String, Integer> highscores = sortPuntuacions(scores);
		return highscores;
	}
	
	private void printInFile(String filename, Map<String, Integer> highscores)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			for(String name:highscores.keySet()){
				writer.println(name +"," + highscores.get(name));
			}
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
	
	private Map<String, Integer> sortPuntuacions(Map<String, Integer> puntuacio){
			Map<String, Integer> tempMap = new HashMap<String, Integer>();
		    for (String wsState : puntuacio.keySet()){
		        tempMap.put(wsState,puntuacio.get(wsState));
		    }

		    List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
		    List<Integer> mapValues = new ArrayList<Integer>(tempMap.values());
		    HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		    TreeSet<Integer> sortedSet = new TreeSet<Integer>(mapValues);
		    Object[] sortedArray = sortedSet.toArray();
		    int size = sortedArray.length;
		    for (int i=size-1; i>=0 && i > size-1-NUM_MAX_SCORES; --i){
		        sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), 
		                      (Integer)sortedArray[i]);
		    }
		    return sortedMap;
	}

}
