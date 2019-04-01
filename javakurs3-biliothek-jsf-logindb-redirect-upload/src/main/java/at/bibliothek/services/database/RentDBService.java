package at.bibliothek.services.database;

import java.sql.Connection;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.bibliothek.model.Kunde;
import at.bibliothek.model.Rent;
import at.bibliothek.model.Rentable;


/**
 * @author Alex-Mi
 * 
 * In Java EE 6 interfaces became absolutely optional. Neither in EJB 3.1, nor CDI / JSR-330 you need interfaces. 
 * You can inject classes directly. They will be still proxied, so all aspects like persistence, transactions, 
 * interceptors, decorators are still available.
 *
 * http://www.adam-bien.com/roller/abien/entry/how_to_deal_with_interfaces
 *
 */

@Stateless
public class RentDBService {
	
	@PersistenceContext
	private EntityManager em;

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
		
		if (kassaZettelNummer == null){
			return null;
		}
		Rent rentResult = null;
		
		Query getRentQuery = em.createQuery(" from Rent r where r.kassaZettelNummer :kz ").setParameter(1, kassaZettelNummer);
		
		List <Rent> result = getRentQuery.getResultList();
		
		rentResult = result.size() > 0 ? result.get(0) : null;
		
		return rentResult;
	}

	public List<Rent> findBy(Kunde kunde, java.util.Date ofDate) throws ServiceException {

		Query findRantByKundeAndDate = em.createQuery(" from Rent r where kunde : k and rentedOn : ro ").setParameter(1, kunde).setParameter(2, ofDate);
		
		return findRantByKundeAndDate.getResultList();
	}
	
	public List<Rent> findByCustomerId(String customerId) throws ServiceException {

		Query findRantByKundeAndDate = em.createQuery(" from Rent r where kunde.id : id ").setParameter(1, Integer.valueOf(customerId));
		
		return findRantByKundeAndDate.getResultList();
	}
}
