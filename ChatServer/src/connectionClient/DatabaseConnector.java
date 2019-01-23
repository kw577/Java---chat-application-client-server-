package connectionClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

	private String adress;
    private String userName; 
    private String password;



	private Connection session;
    
	
	
	public DatabaseConnector(String adress, String userName, String password) {
		this.adress = adress;
		this.userName = userName;
		this.password = password;
		
		
		if(this.loadDriver()) {
			System.out.println("Driver loaded succesfully");
			
			this.session = this.connectToDatabase(adress, userName, password);
			
			
		} else {
			System.err.println("Error while loading driver");
		}
		
		
		
	}

	private boolean loadDriver() {
	    // LADOWANIE STEROWNIKA
	    //System.out.print("Sprawdzanie sterownika:");
	    try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	        return true;
	    } catch (Exception e) {
	        //System.out.println("Blad przy ladowaniu sterownika bazy!");
	        return false;
	    }
	}
	
	
	 
	
	
	private Connection connectToDatabase(String adress,
	        String userName, String password) {
	    System.out.print("\nTrying to connect with database:\n");
	    String baza = adress;

	    java.sql.Connection connection = null;
	    try {
	        connection = DriverManager.getConnection(baza, userName, password);
	    } catch (SQLException e) {
	        System.out.println("\nError while connecting database!");
	        System.exit(1);
	    }
	    return connection;
	}
	
	
	
	private static void closeConnection(Connection connection, Statement s) {
	    System.out.print("\nZamykanie polaczenia z baza:");
	    try {
	        s.close();
	        connection.close();
	    } catch (SQLException e) {
	        System.out
	                .println("Blad przy zamykaniu polaczenia " + e.toString());
	        System.exit(4);
	    }
	    System.out.print(" zamkniecie OK");
	}
	
	
	private static ResultSet executeQuery(Statement s, String sql) {
	    try {
	        return s.executeQuery(sql);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	private static void printDataFromQuery(ResultSet r) {
	    ResultSetMetaData rsmd;
	    try {
	        rsmd = r.getMetaData();
	        int numcols = rsmd.getColumnCount(); // pobieranie liczby column

	        // wyswietlanie kolejnych rekordow:
	        while (r.next()) {
	            for (int i = 1; i <= numcols; i++) {
	                Object obj = r.getObject(i);
	                if (obj != null)
	                    System.out.print(" " + obj.toString() + " ");
	                else
	                    System.out.print(" ");
	            }
	            // System.out.println();
	        }
	    } catch (SQLException e) {
	        System.out.println("Blad odczytu z bazy! " + e.toString());
	        System.exit(3);
	    }
	}
	
	
	
    private static Statement createStatement(Connection connection) {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ;
        return null;
    }
	
	
	public Connection getSession() {
		return session;
	}

	public void setSession(Connection session) {
		this.session = session;
	}
	
	
	
	// Test
	public static void main(String[] args) {
		DatabaseConnector dbConnection = new DatabaseConnector("jdbc:mysql://localhost:3306/chat_database", "root", "");
        String sql = "Select * from user";
        Statement s = createStatement(dbConnection.getSession());
        ResultSet r = executeQuery(s, sql);
        printDataFromQuery(r);
        
        System.out.println("\n\n\n");
        sql = "Select password from user where nick='Kuba'";
        s = createStatement(dbConnection.getSession());
        r = executeQuery(s, sql);
        printDataFromQuery(r);
        
        
        
        closeConnection(dbConnection.getSession(), s);
	
	
	}
	
	
}
