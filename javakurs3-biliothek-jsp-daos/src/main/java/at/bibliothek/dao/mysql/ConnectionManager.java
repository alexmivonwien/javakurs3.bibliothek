package at.bibliothek.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Diese Klasse ist zuständig für die Erstellung neuer DatenbankVerbindung(en) und deren Wiederverwendung in der Anwendung.
 * 
 * 
 * This class is responsible for obtaining a connection to the database and returning it to the calling code.
 * This very simple example creates 
 * 
 * @author User
 * 
 * @see http://stackoverflow.com/questions/3083333/jdbc-connection-pooling-connection-reuse
 * 
 *
 */
class ConnectionManager {

	private static Connection connection;

	static {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");  
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bibliothek", "bibl", "bibl");
		} catch (Exception e) {
			System.out
					.println(" An exception occured while trying to get connection from ");
			e.printStackTrace();
		}
	}

	private ConnectionManager() {
	}
	
	public static Connection getConnection(){
		return connection;
	}
	
}
