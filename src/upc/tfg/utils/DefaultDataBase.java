package upc.tfg.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import upc.tfg.logic.Carta;
import upc.tfg.logic.Districte;
import upc.tfg.logic.Jugador;
import upc.tfg.logic.Partida;
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
		       String sql = "";
		       sql = "DELETE from HIGHSCORES;";
		       stmt.executeUpdate(sql);
		       c.commit();
		       
		       for(String name:highscores.keySet()){
		    	   	int punts = highscores.get(name);
		    	   	String[] strNoms = name.split("_");
					String nomJugador = strNoms[0];
					String data = "";
					if(strNoms.length > 1)data = strNoms[1];
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
	                  " idJugadorInici INTEGER, " + 
	                  " passejantsAMoure INTEGER, " +
	                  " PRIMARY KEY (nom))"; 
			      stmt.executeUpdate(sql);
		      
		      //Fem la taula jugador
		      sql = "CREATE TABLE IF NOT EXISTS JUGADOR" +
	                  "(nomJugador VARCHAR(255), " +
	                  " id INTEGER, " + 
	                  " numPassejants INTEGER, " +
	                  " nomPartida VARCHAR(255), " +
	                  " color INTEGER, " +
	                  " tipus INTEGER, " +
	                  " PRIMARY KEY (nomJugador, nomPartida))"; 
			  stmt.executeUpdate(sql);
			  
			  //Fem la taula districte
			  sql = "CREATE TABLE IF NOT EXISTS DISTRICTE" +
	                  "(nomDistricte VARCHAR(255), " +
	                  " p_blaus INTEGER, " + 
	                  " p_vermells INTEGER, " + 
	                  " p_verds INTEGER, " + 
	                  " p_grocs INTEGER, " + 
	                  " p_blaus_bloq INTEGER, " + 
	                  " p_vermells_bloq INTEGER, " + 
	                  " p_verds_bloq INTEGER, " + 
	                  " p_grocs_bloq INTEGER, " + 
	                  " nomPartida VARCHAR(255), " + 
	                  " PRIMARY KEY (nomDistricte, nomPartida))"; 
			  stmt.executeUpdate(sql);
			  
			//Fem la taula carta
			  sql = "CREATE TABLE IF NOT EXISTS CARTA" +
	                  "(nomCarta VARCHAR(255), " +
	                  " nomPartida VARCHAR(255), " + 
	                  " idJugador INTEGER, " +
	                  " seleccionada INTEGER, " +
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
		       String sql = "INSERT INTO PARTIDA (nom,data,torn,pas,idJugadorActual,idJugadorInici,passejantsAMoure) " +
	                    "VALUES ('"+ partida.getNom() +"', '"+ partida.getData() +"', "+ partida.getTorn()+
	                    ", "+ partida.getPas() + ", " + partida.getIdJugadorActual() + ", " + 
	                    partida.getIdJugadorInici() + ", " + partida.getPassejantsAMoure() +");"; 
				stmt.executeUpdate(sql);
				
			//Guardem els jugadors	
		       for(int i = 0; i < 4; ++i){
		    	   Jugador j = partida.getJugador(i+1);
					sql = "INSERT INTO JUGADOR (nomJugador,id,numPassejants,nomPartida,color,tipus) " +
		                    "VALUES ('"+ j.getNom() +"', "+ j.getId() +", " + j.getTotalPassejants() + 
		                    ", '"+ partida.getNom()+"', "+ j.getColor() + ", "+ j.getTipusJugador() +");"; 
					stmt.executeUpdate(sql);
					//Guardem les cartes del jugador
					for(Carta carta:j.getCartes()){
						int seleccionada = 0;
						if(partida.getCartesAIntercanviar().containsValue(carta)) seleccionada = 1;
						sql = "INSERT INTO CARTA (nomCarta,nomPartida,idJugador,seleccionada) " +
			                    "VALUES ('"+ carta.getNom() +"', '"+ partida.getNom() +"', " + j.getId()+ ", "+
								seleccionada + ");"; 
						stmt.executeUpdate(sql);
					}
		       }
		       
		       //Guardem les cartes que estan a la baralla1
		       for(Carta carta:partida.getBaralla().getCartes()){
		    	   sql = "INSERT INTO CARTA (nomCarta,nomPartida,idJugador,seleccionada) " +
		                    "VALUES ('"+ carta.getNom() +"', '"+ partida.getNom() +"', " + -1 + ", "+ 0 +");"; 
					stmt.executeUpdate(sql);
		       }
		       
		       //Guardem les cartes que estan a la baralla2
		       for(Carta carta:partida.getBaralla2().getCartes()){
		    	   sql = "INSERT INTO CARTA (nomCarta,nomPartida,idJugador,seleccionada) " +
		                    "VALUES ('"+ carta.getNom() +"', '"+ partida.getNom() +"', " + -2 + ", "+ 0 + ");"; 
					stmt.executeUpdate(sql);
		       }
		       
		       for(Districte d:partida.getTauler().getDistrictes()){
		    	   sql = "INSERT INTO DISTRICTE (nomDistricte,p_blaus,p_vermells,p_verds," +
		    	   		"p_grocs,p_blaus_bloq,p_vermells_bloq,p_verds_bloq,p_grocs_bloq,nomPartida) " +
		                    "VALUES ('"+ d.getNom() +"', "+ d.getNumPassejantsBlaus() +", " + d.getNumPassejantsVermells() + 
		                    ", "+ d.getNumPassejantsVerds() +", " + d.getNumPassejantsGrocs()
		                     +", " + d.getNumPassejantsBloquejats(Constants.BLAU)  +", " + d.getNumPassejantsBloquejats(Constants.VERMELL)
		                      +", " + d.getNumPassejantsBloquejats(Constants.VERD)  +", " + d.getNumPassejantsBloquejats(Constants.GROC)+ ", '"+ partida.getNom() +"');"; 
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
	   
	   public static Map<String,String> getNomsPartides()
	   {
		   Map<String,String> noms = new HashMap<String,String>();
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
		          noms.put(rs.getString("nom"), rs.getString("data"));  
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
		       int[] arrayIA = new int[3];
		       int k = 0;
		       ResultSet rs = stmt.executeQuery( "SELECT * FROM JUGADOR "+
			       		"WHERE nomPartida='"+ nom + "';" );
			   while ( rs.next() ) {
				   Jugador j = new Jugador(rs.getString("nomJugador"), rs.getInt("id"), rs.getInt("color"), rs.getInt("tipus"));
				   j.setNumPassejants(rs.getInt("numPassejants"));
				   jugadors.add(j);
				   if(j.getTipusJugador() != Constants.HUMA){
					   arrayIA[k] = j.getTipusJugador();
					   ++k;
				   }
			   }
			   
			   //Aconseguim els districtes
			   rs = stmt.executeQuery( "SELECT * FROM DISTRICTE "+
			       		"WHERE nomPartida='"+ nom + "';" );
			   while ( rs.next() ) {
				   for(Districte d:districtes){
					   if(d.getNom().equals(rs.getString("nomDistricte"))){
						   d.inicialitzaIAfefeixPassejants(Constants.BLAU, rs.getInt("p_blaus"), rs.getInt("p_blaus_bloq"));
						   d.inicialitzaIAfefeixPassejants(Constants.VERMELL, rs.getInt("p_vermells"), rs.getInt("p_vermells_bloq"));
						   d.inicialitzaIAfefeixPassejants(Constants.VERD, rs.getInt("p_verds"), rs.getInt("p_verds_bloq"));
						   d.inicialitzaIAfefeixPassejants(Constants.GROC, rs.getInt("p_grocs"), rs.getInt("p_grocs_bloq"));
					   }
				   }
			   }
			   
			   Tauler.setDistrictes(districtes);
			   
			   //Aconseguim les cartes
			   Map<Integer,Carta> cartesAIntercanviar = new HashMap<Integer,Carta>();
			   for(Jugador j:jugadors){
				   rs = stmt.executeQuery( "SELECT * FROM CARTA "+
				       		"WHERE nomPartida='"+ nom + "' AND idJugador='"+ j.getId()+"';" );
				   int idJugadorAnterior = j.getId()+1;
				   if (idJugadorAnterior == 5)idJugadorAnterior = 1;
				   while ( rs.next() ) {
					   String nomCarta = rs.getString("nomCarta");
					   int seleccionada = rs.getInt("seleccionada");
					   for(int i = 0; i < CartesBD.nomsCartesComplets.length; ++i){
						   if(CartesBD.nomsCartesComplets[i].equalsIgnoreCase(nomCarta)){
							   Carta cAux = new Carta(CartesBD.nomsCartes[i], CartesBD.valorsCartes[i], CartesBD.nomsCartesComplets[i]);
							   j.afegirCarta(cAux);
							   if(seleccionada == 1)cartesAIntercanviar.put(idJugadorAnterior, cAux);
						   }
					   }
				   }
			   }
			   
			   //Aconseguim les cartes de la baralla1
			   ArrayList<Carta> cartesB1 = new ArrayList<Carta>();
			   rs = stmt.executeQuery( "SELECT * FROM CARTA "+
			       		"WHERE nomPartida='"+ nom + "' AND idJugador='"+ -1 +"';" );
			   while ( rs.next() ) {
				   String nomCarta = rs.getString("nomCarta");
				   for(int i = 0; i < CartesBD.nomsCartesComplets.length; ++i){
					   if(CartesBD.nomsCartesComplets[i].equalsIgnoreCase(nomCarta)){
						   cartesB1.add(new Carta(CartesBD.nomsCartes[i], CartesBD.valorsCartes[i], CartesBD.nomsCartesComplets[i]));
					   }
				   }
			   }
			   
			 //Aconseguim les cartes de la baralla2
			   ArrayList<Carta> cartesB2 = new ArrayList<Carta>();
			   rs = stmt.executeQuery( "SELECT * FROM CARTA "+
			       		"WHERE nomPartida='"+ nom + "' AND idJugador='"+ -2 +"';" );
			   while ( rs.next() ) {
				   String nomCarta = rs.getString("nomCarta");
				   for(int i = 0; i < CartesBD.nomsCartesComplets.length; ++i){
					   if(CartesBD.nomsCartesComplets[i].equalsIgnoreCase(nomCarta)){
						   cartesB2.add(new Carta(CartesBD.nomsCartes[i], CartesBD.valorsCartes[i], CartesBD.nomsCartesComplets[i]));
					   }
				   }
			   }
			   
			   //Creem la partida
			   rs = stmt.executeQuery( "SELECT * FROM PARTIDA "+
			       		"WHERE nom='"+ nom + "';" );
			   partida = new Partida(nom,rs.getString("data"),rs.getInt("torn"),rs.getInt("pas"),
					   jugadors,districtes, rs.getInt("idJugadorInici"), rs.getInt("passejantsAMoure"),
					   cartesB1, cartesB2, cartesAIntercanviar, arrayIA);
			       
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
	   
	   public static void deletePartida(String nom)
	   {
		   	 Connection c = null;
		     Statement stmt = null;
		     try {
		       Class.forName("org.sqlite.JDBC");
		       c = DriverManager.getConnection("jdbc:sqlite:test.db");
		       c.setAutoCommit(false);
		       System.out.println("Opened database successfully");
	
		       stmt = c.createStatement();
		       
		       //Borrem els jugadors
		       stmt.executeUpdate( "DELETE FROM JUGADOR "+
			       		"WHERE nomPartida='"+ nom + "';" );
			   
			   //Borrem els districtes
			   stmt.executeUpdate( "DELETE FROM DISTRICTE "+
			       		"WHERE nomPartida='"+ nom + "';" );
			   
			   //Borrem les cartes
			   stmt.executeUpdate( "DELETE FROM CARTA "+
				       		"WHERE nomPartida='"+ nom + "';" );
			   
			   //Borrem la partida
			   stmt.executeUpdate( "DELETE FROM PARTIDA "+
			       		"WHERE nom='"+ nom + "';" );
			   c.commit();
		       stmt.close();
		       c.close();
		     } catch ( Exception e ) {
		       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		       System.exit(0);
		     }
		     System.out.println("Operation done successfully");
	   }
}
