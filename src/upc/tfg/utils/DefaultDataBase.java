package upc.tfg.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import upc.tfg.logic.Carta;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;

public class DefaultDataBase {
	   
	   public static void createDataBase()
	   {
		   Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      String sql = "CREATE TABLE IF NOT EXISTS HIGHSCORES" +
                  "(score INTEGER not NULL, " +
                  " name VARCHAR(255), " + 
                  " date VARCHAR(255), " + 
                  " PRIMARY KEY ( name, date ))"; 
		      stmt.executeUpdate(sql);
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Table created successfully");
	   }
	   
	   public static void insertHighScores(Map<String, Integer> highscores)
	   {
		     Connection c = null;
		     Statement stmt = null;
		     try {
		       Class.forName("org.sqlite.JDBC");
		       c = DriverManager.getConnection("jdbc:sqlite:test.db");
		       c.setAutoCommit(false);
		       System.out.println("Opened database successfully");
	
		       stmt = c.createStatement();
		       String sql = "DELETE from HIGHSCORES;";
		       stmt.executeUpdate(sql);
		       c.commit();
		       
		       for(String name:highscores.keySet()){
		    	   	int punts = highscores.get(name);
		    	   	String[] strNoms = name.split("_");
					String nomJugador = strNoms[0];
					String data = strNoms[1];
					sql = "INSERT INTO HIGHSCORES (score,name,date) " +
		                    "VALUES ("+ punts +", '"+ nomJugador +"', '" + data + "');"; 
					stmt.executeUpdate(sql);
				}
	
		       stmt.close();
		       c.commit();
		       c.close();
		     } catch ( Exception e ) {
		       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		       System.exit(0);
		     }
		     System.out.println("Records created successfully");
	   }
	   
	   public static Map<String, Integer> getHighscores()
	   {
		   	 Map<String, Integer> puntuacio = new HashMap<String, Integer>();
		     Connection c = null;
		     Statement stmt = null;
		     try {
		       Class.forName("org.sqlite.JDBC");
		       c = DriverManager.getConnection("jdbc:sqlite:test.db");
		       c.setAutoCommit(false);
		       System.out.println("Opened database successfully");
	
		       stmt = c.createStatement();
		       ResultSet rs = stmt.executeQuery( "SELECT * FROM HIGHSCORES;" );
		       while ( rs.next() ) {
		          int score = rs.getInt("score");
		          String  name = rs.getString("name");
		          String date  = rs.getString("date");
		          puntuacio.put(name+"_"+date, score);
		       }
		       rs.close();
		       stmt.close();
		       c.close();
		     } catch ( Exception e ) {
		       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		       System.exit(0);
		     }
		     System.out.println("Operation done successfully");
		     return puntuacio;
	   }
	   
	   public static void createPartidaDataBase()
	   {
		   Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      //Fem la taula partida
		      String sql = "CREATE TABLE IF NOT EXISTS PARTIDA" +
                  "(nom VARCHAR(255), " +
                  " data VARCHAR(255), " + 
                  " PRIMARY KEY (nom))"; 
		      stmt.executeUpdate(sql);
		      
		      //Fem la taula jugador
		      sql = "CREATE TABLE IF NOT EXISTS JUGADOR" +
	                  "(nom VARCHAR(255), " +
	                  " id INTEGER, " + 
	                  " numPassejants INTEGER, " +
	                  " nomPartida VARCHAR(255), " +
	                  " PRIMARY KEY (nom, nomPartida))"; 
			  stmt.executeUpdate(sql);
			  
			  //Fem la taula districte
			  sql = "CREATE TABLE IF NOT EXISTS DISTRICTE" +
	                  "(nom VARCHAR(255), " +
	                  " p_blaus INTEGER, " + 
	                  " p_vermells INTEGER, " + 
	                  " p_verds INTEGER, " + 
	                  " p_grocs INTEGER, " + 
	                  " nomPartida VARCHAR(255), " + 
	                  " PRIMARY KEY (nom, nomPartida))"; 
			  stmt.executeUpdate(sql);
			  
			//Fem la taula carta
			  sql = "CREATE TABLE IF NOT EXISTS CARTA" +
	                  "(nom VARCHAR(255), " +
	                  " nomPartida VARCHAR(255), " + 
	                  " idJugador INTEGER, " + 
	                  " PRIMARY KEY (nom, nomPartida, idJugador))"; 
			  stmt.executeUpdate(sql);
			      
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Table created successfully");
	   }
	   
	   public static void guardarPartida(Partida partida)
	   {
		   Connection c = null;
		     Statement stmt = null;
		     try {
		       Class.forName("org.sqlite.JDBC");
		       c = DriverManager.getConnection("jdbc:sqlite:test.db");
		       c.setAutoCommit(false);
		       System.out.println("Opened database successfully");
	
		       stmt = c.createStatement();
		       c.commit();
		       String sql = "INSERT INTO PARTIDA (nom,data) " +
	                    "VALUES ('"+ partida.getNom() +"', '"+ partida.getData() +"');"; 
				stmt.executeUpdate(sql);
				
			//Guardem els jugadors	
		       for(int i = 0; i < 4; ++i){
		    	   Jugador j = partida.getJugador(i+1);
					sql = "INSERT INTO JUGADOR (nom,id,numPassejants,nomPartida) " +
		                    "VALUES ('"+ j.getNom() +"', "+ j.getId() +", " + j.getTotalPassejants() + ", '"+ partida.getNom() +"');"; 
					stmt.executeUpdate(sql);
					//Guardem les cartes del jugador
					for(Carta carta:j.getCartes()){
						sql = "INSERT INTO CARTA (nom,nomPartida,idJugador) " +
			                    "VALUES ('"+ carta.getNom() +"', '"+ partida.getNom() +"', " + j.getId() + ");"; 
						stmt.executeUpdate(sql);
					}
		       }
		       
		       for(Districte d:partida.getTauler().getDistrictes()){
		    	   sql = "INSERT INTO DISTRICTE (nom,p_blaus,p_vermells,p_verds,p_grocs,nomPartida) " +
		                    "VALUES ('"+ d.getNom() +"', "+ d.getNumPassejantsBlaus() +", " + d.getNumPassejantsVermells() + 
		                    ", "+ d.getNumPassejantsVerds() +", " + d.getNumPassejantsGrocs() + ", '"+ partida.getNom() +"');"; 
					stmt.executeUpdate(sql);
		       }
	
		       stmt.close();
		       c.commit();
		       c.close();
		     } catch ( Exception e ) {
		       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		       System.exit(0);
		     }
		     System.out.println("Records created successfully");
	   }
	   
	   public static ArrayList<String> getNomsPartides()
	   {
		   ArrayList<String> noms = new ArrayList<String>();
		     Connection c = null;
		     Statement stmt = null;
		     try {
		       Class.forName("org.sqlite.JDBC");
		       c = DriverManager.getConnection("jdbc:sqlite:test.db");
		       c.setAutoCommit(false);
		       System.out.println("Opened database successfully");
	
		       stmt = c.createStatement();
		       ResultSet rs = stmt.executeQuery( "SELECT * FROM PARTIDA;" );
		       while ( rs.next() ) {
		          noms.add(rs.getString("nom"));
		          
		       }
		       rs.close();
		       stmt.close();
		       c.close();
		     } catch ( Exception e ) {
		       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		       System.exit(0);
		     }
		     System.out.println("Operation done successfully");
		     return noms;
	   }
	   
	   public static Partida getPartida(String nom)
	   {
		   return null;
	   }
}
