package at.bibliothek.dao;

import java.sql.Connection;

import at.bibliothek.dao.mysql.MySQLDAOFactory;

/**
 * 
 * @author User
 * 
 * @see http://www.oracle.com/technetwork/java/dataaccessobject-138824.html
 * @see http://tutorials.jenkov.com/java-persistence/dao-manager.html
 *
 */
public abstract class DAOFactory {

	// List of DAO types supported by the factory
	public static final int MYSQL = 1;
	public static final int ORACLE = 2;
	public static final int MSSQL = 3;

	public abstract KundeDAO getKundeDAO(Connection connection);

	public abstract NatPersonDAO getNatPersDAO(Connection connection);

	public abstract RentableDAO getRentableDAO(Connection connection);

	public abstract RentDAO getRentDAO(Connection connection);
	
	public abstract FirmaDAO getFirmaDAO(Connection connection);

	
	public static DAOFactory getDAOFactory(int whichFactory) {

		switch (whichFactory) {
			case MYSQL:
				return new MySQLDAOFactory();
			default:
				return null;
			}
	} // getDAOFactory()
	
	public  abstract Connection createConnection();
}
