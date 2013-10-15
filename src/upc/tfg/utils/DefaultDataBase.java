package upc.tfg.utils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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
}
