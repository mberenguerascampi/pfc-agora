package upc.tfg.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import upc.tfg.logic.Carta;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;
import upc.tfg.logic.Passejant;
import upc.tfg.logic.Tauler;

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
//		      String sqlDrop = "DROP TABLE PARTIDA"; 
//			  stmt.executeUpdate(sqlDrop);
//			  sqlDrop = "DROP TABLE JUGADOR"; 
//			  stmt.executeUpdate(sqlDrop);
//			  sqlDrop = "DROP TABLE DISTRICTE"; 
//			  stmt.executeUpdate(sqlDrop);
//			  sqlDrop = "DROP TABLE CARTA"; 
//			  stmt.executeUpdate(sqlDrop);
			  
		      String sql = "CREATE TABLE IF NOT EXISTS PARTIDA" +
	                  "(nom VARCHAR(255), " +
	                  " data VARCHAR(255), " + 
	                  " torn INTEGER, " + 
	                  " pas INTEGER, " + 
	                  " idJugadorActual INTEGER, " + 
	                  " PRIMARY KEY (nom))"; 
			      stmt.executeUpdate(sql);
		      
		      //Fem la taula jugador
		      sql = "CREATE TABLE IF NOT EXISTS JUGADOR" +
	                  "(nomJugador VARCHAR(255), " +
	                  " id INTEGER, " + 
	                  " numPassejants INTEGER, " +
	                  " nomPartida VARCHAR(255), " +
	                  " PRIMARY KEY (nomJugador, nomPartida))"; 
			  stmt.executeUpdate(sql);
			  
			  //Fem la taula districte
			  sql = "CREATE TABLE IF NOT EXISTS DISTRICTE" +
	                  "(nomDistricte VARCHAR(255), " +
	                  " p_blaus INTEGER, " + 
	                  " p_vermells INTEGER, " + 
	                  " p_verds INTEGER, " + 
	                  " p_grocs INTEGER, " + 
	                  " nomPartida VARCHAR(255), " + 
	                  " PRIMARY KEY (nomDistricte, nomPartida))"; 
			  stmt.executeUpdate(sql);
			  
			//Fem la taula carta
			  sql = "CREATE TABLE IF NOT EXISTS CARTA" +
	                  "(nomCarta VARCHAR(255), " +
	                  " nomPartida VARCHAR(255), " + 
	                  " idJugador INTEGER, " + 
	                  " PRIMARY KEY (nomCarta, nomPartida, idJugador))"; 
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
		       String sql = "INSERT INTO PARTIDA (nom,data,torn,pas,idJugadorActual) " +
	                    "VALUES ('"+ partida.getNom() +"', '"+ partida.getData() +"', "+ partida.getTorn()+
	                    ", "+ partida.getPas() + ", " + partida.getIdJugadorActual() +");"; 
				stmt.executeUpdate(sql);
				
			//Guardem els jugadors	
		       for(int i = 0; i < 4; ++i){
		    	   Jugador j = partida.getJugador(i+1);
					sql = "INSERT INTO JUGADOR (nomJugador,id,numPassejants,nomPartida) " +
		                    "VALUES ('"+ j.getNom() +"', "+ j.getId() +", " + j.getTotalPassejants() + ", '"+ partida.getNom() +"');"; 
					stmt.executeUpdate(sql);
					//Guardem les cartes del jugador
					for(Carta carta:j.getCartes()){
						sql = "INSERT INTO CARTA (nomCarta,nomPartida,idJugador) " +
			                    "VALUES ('"+ carta.getNom() +"', '"+ partida.getNom() +"', " + j.getId() + ");"; 
						stmt.executeUpdate(sql);
					}
		       }
		       
		       for(Districte d:partida.getTauler().getDistrictes()){
		    	   sql = "INSERT INTO DISTRICTE (nomDistricte,p_blaus,p_vermells,p_verds,p_grocs,nomPartida) " +
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
		   	 Connection c = null;
		     Statement stmt = null;
		     ArrayList<Jugador> jugadors = new ArrayList<Jugador>();
		     Districte[] districtes = Tauler.creaDistrictes();
		     Partida partida = null;
		     try {
		       Class.forName("org.sqlite.JDBC");
		       c = DriverManager.getConnection("jdbc:sqlite:test.db");
		       c.setAutoCommit(false);
		       System.out.println("Opened database successfully");
	
		       stmt = c.createStatement();
		       
		       //Aconseguim els jugadors
		       ResultSet rs = stmt.executeQuery( "SELECT * FROM JUGADOR "+
			       		"WHERE nomPartida='"+ nom + "';" );
			   while ( rs.next() ) {
				   Jugador j = new Jugador(rs.getString("nomJugador"), rs.getInt("id"), 1);
				   j.setNumPassejants(rs.getInt("numPassejants"));
				   jugadors.add(j);
			   }
			   
			   //Aconseguim els districtes
			   rs = stmt.executeQuery( "SELECT * FROM DISTRICTE "+
			       		"WHERE nomPartida='"+ nom + "';" );
			   while ( rs.next() ) {
				   for(Districte d:districtes){
					   if(d.getNom().equals(rs.getString("nomDistricte"))){
						   d.inicialitzaIAfefeixPassejants(Constants.BLAU, rs.getInt("p_blaus"));
						   d.inicialitzaIAfefeixPassejants(Constants.VERMELL, rs.getInt("p_vermells"));
						   d.inicialitzaIAfefeixPassejants(Constants.VERD, rs.getInt("p_verds"));
						   d.inicialitzaIAfefeixPassejants(Constants.GROC, rs.getInt("p_grocs"));
					   }
				   }
			   }
			   
			   //Aconseguim les cartes
			   for(Jugador j:jugadors){
				   rs = stmt.executeQuery( "SELECT * FROM CARTA "+
				       		"WHERE nomPartida='"+ nom + "' AND idJugador='"+ j.getId()+"';" );
				   while ( rs.next() ) {
					   String nomCarta = rs.getString("nomCarta");
					   for(int i = 0; i < CartesBD.nomsCartesComplets.length; ++i){
						   if(CartesBD.nomsCartesComplets[i].equalsIgnoreCase(nomCarta)){
							   j.afegirCarta(new Carta(CartesBD.nomsCartes[i], CartesBD.valorsCartes[i], CartesBD.nomsCartesComplets[i]));
							   System.out.println("AFEGINT CARTA A JUGADOR");
						   }
					   }
				   }
			   }
			   
			   //Creem la partida
			   rs = stmt.executeQuery( "SELECT * FROM PARTIDA "+
			       		"WHERE nom='"+ nom + "';" );
			   partida = new Partida(nom,rs.getString("data"),rs.getInt("torn"),rs.getInt("pas"),jugadors,districtes);
			       
		       rs.close();
		       stmt.close();
		       c.close();
		     } catch ( Exception e ) {
		       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		       System.exit(0);
		     }
		     System.out.println("Operation done successfully");
		   return partida;
	   }
}
