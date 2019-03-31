package at.bibliothek.dao;

import java.sql.Connection;

/**
 * 
 * @author User
 *
 * @see  http://tutorials.jenkov.com/java-persistence/dao-design-problems.html
 * 
 * @see http://stackoverflow.com/questions/19154202/data-access-object-dao-in-java
 * 
 * @see http://www.oracle.com/technetwork/java/dataaccessobject-138824.html
 * 
 * @see http://stackoverflow.com/questions/13785634/responsibilities-and-use-of-service-and-dao-layers
 * 
 * @see https://en.wikipedia.org/wiki/Service_layers_pattern
 * 
 * 
 */
public abstract class AbstractDatabaseDAO {
	
	protected Connection connection;

	public Connection getConnection(){
		return connection;
	};

	public void setConnection(Connection connection){
		this.connection = connection;
	};	
	
}
