package at.bibliothek.services.database;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import at.bibliothek.dao.DAOFactory;
import at.bibliothek.dao.RentDAO;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.Rent;
import at.bibliothek.model.Rentable;

public class RentDBService {

	private Logger logger = Logger.getLogger(RentDBService.class.getName());

	public Rent[] transferRentablesFromOneCustomerToOther(List<Rentable> rentable1, Kunde kunde1, Kunde kunde2, Date date) throws ServiceException {
		Rent[] affectedRents = new Rent[2];

		return affectedRents;
	}
	
	/**
	 * @author User
	 * 
	 *  Diese Methode lädt das Rent-Objekt, das die Kassenzettellnummer entspricht.
	 *  Dabei geht's um "eager loading" - das Objekt wird samt alle Rentables und
	 *  Kundendetails geladen.
	 * 
	 * @param kassaZettelId
	 * @return
	 * @throws ServiceException
	 */
	public Rent getRent(String kassaZettelNummer) throws ServiceException {
		Rent rentResult = null;
		Connection connection = null;
		if (kassaZettelNummer == null){
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

		try {

			connection = daoFactory.createConnection();
			RentDAO rentDAO = daoFactory.getRentDAO(connection);
			rentResult  = rentDAO.getRent(kassaZettelNummer);

		} catch (SQLException e) {
			logger.error("An exception occured when trying to get the Rent for " + kassaZettelNummer, e);
			throw new ServiceException("An exception occured when trying to get the Rent for " + kassaZettelNummer, e);
		}

		return rentResult;
	}

	public List<Rent> findBy(Kunde kunde, java.util.Date date) throws ServiceException {
		List<Rent> rentResult = new ArrayList<Rent>();

		Connection connection = null;

		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

		try {

			connection = daoFactory.createConnection();
			RentDAO rentDAO = daoFactory.getRentDAO(connection);
			rentResult  = rentDAO.findBy(kunde, date);

		} catch (SQLException e) {
			logger.error("An exception occured when trying to find all Rents", e);
			throw new ServiceException("An exception occured when trying to find all Rents ", e);
		}

		return rentResult;
	}
	
	public List<Rent> findByCustomerId(String customerId) throws ServiceException {
		List<Rent> rentResult = new ArrayList<Rent>();

		Connection connection = null;

		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

		try {

			connection = daoFactory.createConnection();
			RentDAO rentDAO = daoFactory.getRentDAO(connection);
			rentResult  = rentDAO.findBy(customerId);

		} catch (SQLException e) {
			logger.error("An exception occured when trying to find all Rents", e);
			throw new ServiceException("An exception occured when trying to find all Rents ", e);
		}

		return rentResult;
	}
}
