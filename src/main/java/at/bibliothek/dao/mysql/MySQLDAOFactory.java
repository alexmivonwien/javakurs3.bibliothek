package at.bibliothek.dao.mysql;

import java.sql.Connection;

import at.bibliothek.dao.DAOFactory;
import at.bibliothek.dao.FirmaDAO;
import at.bibliothek.dao.KundeDAO;
import at.bibliothek.dao.NatPersonDAO;
import at.bibliothek.dao.RentDAO;
import at.bibliothek.dao.RentableDAO;
import at.bibliothek.dao.mysql.MySQLKundeDAO;
import at.bibliothek.dao.mysql.MySQLNatPersonDAO;
import at.bibliothek.dao.mysql.MySQLRentDAO;
import at.bibliothek.dao.mysql.MySQLRentableDAO;

/**
 * 
 * @author User
 * 
 * @see http://tutorials.jenkov.com/java-persistence/dao-manager.html
 *
 */
public class MySQLDAOFactory extends DAOFactory {

	
	public KundeDAO getKundeDAO(Connection connection)  {
		MySQLKundeDAO kundeImpl = new MySQLKundeDAO();
		kundeImpl.setConnection(connection);
		return kundeImpl;
	}
	
	public NatPersonDAO getNatPersDAO (Connection connection){
		MySQLNatPersonDAO natPersImpl = new MySQLNatPersonDAO();
		natPersImpl.setConnection(connection);
		return natPersImpl;
		
	}
	
	public  RentableDAO getRentableDAO(Connection connection)  {
		MySQLRentableDAO rentableImpl = new MySQLRentableDAO();
		rentableImpl.setConnection(connection);
		return rentableImpl;
	}
	
	public RentDAO getRentDAO(Connection connection)  {
		MySQLRentDAO rentImpl = new MySQLRentDAO();
		rentImpl.setConnection(connection);
		return rentImpl;
	}
	
	// method to create MySQL connection
	public Connection createConnection() {
		return ConnectionManager.getConnection();
	}

	@Override
	public FirmaDAO getFirmaDAO(Connection connection) {
		MySQLFirmaDAO firmaImpl = new MySQLFirmaDAO();
		firmaImpl.setConnection(connection);
		return firmaImpl;
	}
}
