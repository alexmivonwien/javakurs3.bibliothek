package at.bibliothek.services.database;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import at.bibliothek.dao.DAOFactory;
import at.bibliothek.dao.FirmaDAO;
import at.bibliothek.dao.KundeDAO;
import at.bibliothek.dao.NatPersonDAO;
import at.bibliothek.model.Firma;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.NatuerlichePerson;

/**
 * @author User
 * 
 * @see http://stackoverflow.com/questions/15761791/transaction-rollback-on-sqlexception-using-new-try-with-resources-block
 *
 */
public class KundeDBService {
	
	private static Logger logger = Logger.getLogger(KundeDBService.class.getName());



	public List<NatuerlichePerson> findAllNatuerlichePersonen(String vorname, String nachname) throws ServiceException {

		List<NatuerlichePerson> result = new ArrayList <NatuerlichePerson>();
		
		NatPersonDAO natPersDAO = null;
		Connection connection = null;
		Boolean autoCommitMode =null; 
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		
		try {
			connection = daoFactory.createConnection();
			autoCommitMode = connection.getAutoCommit();
			connection.setAutoCommit(false);
		
			natPersDAO = daoFactory.getNatPersDAO(connection);
			result = natPersDAO.findByName(vorname, nachname);
			
			connection.commit(); // in diesem Fall würde connection.rollback() auch passen, da wir nichts in der Datenbank geschrieben haben
			
		} catch (SQLException e) {
			
			logger.error("An SQLException occured while accessing the Database, trying to rollback the transaction:", e);
			
			if (connection!=null){
				try{
					connection.rollback();
				}catch (SQLException sqle){
					logger.error("Unable to rollback the transaction:", sqle);
				}
			}
			e.printStackTrace();
			throw new ServiceException("An exception occured when trying to find all Naturliche Personen: ", e);
		}
		
		finally{ // unabhängig davon, ob es eine Exception ausgelöst wurde, oder nicht:
			
			if (autoCommitMode!=null && connection!=null){
				try{
					connection.setAutoCommit(autoCommitMode);
				}catch (SQLException sqle){
					logger.error("Unable to set the previous AutoCommoit mode:", sqle);
				}
			}
		}
		
		return result;
	}
	
	public void saveKunde(Kunde kunde) throws ServiceException {

		Connection connection = null;
		Boolean autoCommitMode = null;

		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

		connection = daoFactory.createConnection();

		try {
			autoCommitMode = connection.getAutoCommit();
			connection.setAutoCommit(false);

			if (kunde instanceof NatuerlichePerson) {
				NatPersonDAO natPersDAO = daoFactory.getNatPersDAO(connection);
				natPersDAO.save((NatuerlichePerson) kunde);
			} else if (kunde instanceof Firma) {

				FirmaDAO firmaDAO = daoFactory.getFirmaDAO(connection);
				firmaDAO.saveOrUpdate((Firma) kunde);
			}
			
			connection.commit();
			
		} catch (Exception e) {
			
			logger.error("An SQLException occured while accessing the Database, trying to rollback the transaction:", e);
			
			if (connection!=null){
				try{
					connection.rollback();
				}catch (SQLException sqle){
					logger.error("Unable to rollback the transaction:", sqle);
				}
			}
			throw new ServiceException("An exception occured when trying to find save kunde: ", e);
		}
		
		finally{ // unabhängig davon, ob es eine Exception ausgelöst wurde, oder nicht:
			
			if (autoCommitMode!=null && connection!=null){
				try{
					connection.setAutoCommit(autoCommitMode);
				}catch (SQLException sqle){
					logger.error("Unable to set the previous AutoCommoit mode:", sqle);
				}
			}
		}

	} // saveKunde()
	
	public List<? extends Kunde> findAllKunden(String name) throws ServiceException {

		List<? extends Kunde> result = new ArrayList <Kunde>();
		
		KundeDAO kudneDAO = null;
		Connection connection = null;
		Boolean autoCommitMode =null; 
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		
		try {
			connection = daoFactory.createConnection();
			autoCommitMode = connection.getAutoCommit();
			connection.setAutoCommit(false);
		
			kudneDAO = daoFactory.getKundeDAO(connection);
			result = kudneDAO.findByName(name);
			
			connection.commit(); // in diesem Fall würde connection.rollback() auch passen, da wir nichts in der Datenbank geschrieben haben
			
		} catch (SQLException e) {
			
			logger.error("An SQLException occured while accessing the Database, trying to rollback the transaction:", e);
			
			if (connection!=null){
				try{
					connection.rollback();
				}catch (SQLException sqle){
					logger.error("Unable to rollback the transaction:", sqle);
				}
			}
			e.printStackTrace();
			throw new ServiceException("An exception occured when trying to find all Naturliche Personen: ", e);
		}
		
		finally{ // unabhängig davon, ob es eine Exception ausgelöst wurde, oder nicht:
			
			if (autoCommitMode!=null && connection!=null){
				try{
					connection.setAutoCommit(autoCommitMode);
				}catch (SQLException sqle){
					logger.error("Unable to set the previous AutoCommoit mode:", sqle);
				}
			}
		}
		
		return result;
	}
	
}
