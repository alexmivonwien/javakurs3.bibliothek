package at.bibliothek.services.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;

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

	
	/**
	 * This method is to be implemented: it tranfsers all renatblables from one customer to another one and is left 
	 * here to demonstrate the meaning of a transaction only.
	 * 
	 * @param rentable1
	 * @param kunde1
	 * @param kunde2
	 * @param date
	 * @return
	 * @throws ServiceException
	 */
	public Rent[] transferRentablesFromOneCustomerToOther(List<Rentable> rentable1, Kunde kunde1, Kunde kunde2, Date date) throws ServiceException {
		Rent[] affectedRents = new Rent[2];

		return affectedRents;
	}
	
	
	public Rent getRentById(long id) {
		return em.find(Rent.class, id);
	}
		
	/**
	 * loads all rentables that belong to a particular rent
	 * 
	 * @param rent
	 * @return
	 */
	public List <Rentable> getRentables(Rent rent) {
		
		List <Rentable> rentablesResult = new ArrayList <Rentable> ();
		
		 Query queryString =  em.createQuery(" select r from Rent r JOIN FETCH r.rentables where r.id = :rid").setParameter("rid", rent.getId());
		 List <Rent> resultList = queryString.getResultList();
		 
		 if (resultList.size() > 0){
			 Rent completeRent = resultList.get(0);
			 rentablesResult = completeRent.getRentables();
		 } 
		 
		 return rentablesResult;
	}
	
	
	/**
	 * gets the total number of rents corresponding to the filetrs crietaria.
	 * If custId is present, filters additionally by custId
	 * 
	 * @param filters
	 * @param custId
	 * @return
	 */
	public long getRentsCount( Map<String, Object> filters, Long custId) {

		if (custId !=null){
			filters.put("kunde.id", custId);
		}
		
		String queryString =  " select count (*) from Rent r " + composeWhereClause(filters);

		Query loadQuery = em.createQuery( queryString );
		List<Long> result  = loadQuery.getResultList();
		
		return result.size() > 0 ? result.get(0) : 0;
	}

	
	
	/**
	 * loads a page of sorted rents according to the sortField / sortOrder that match the filters and custId, if present
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param custId
	 * @return
	 */
	public List<Rent> lazyLoadRents(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters, Long custId) {
		
		List<Rent> result = new ArrayList<Rent> ();
		
		if (custId !=null){
			filters.put("kunde.id", custId);
		}
		
		String queryString =  " from Rent r " + composeWhereClause(filters) + composeOrderClause(sortField, sortOrder);
		
		Query loadQuery = em.createQuery( queryString );
		loadQuery.setFirstResult(first);
		loadQuery.setMaxResults(pageSize);
		
		result = loadQuery.getResultList();
		
		return result;
	}
	
	
	private String composeWhereClause (Map<String,Object> filters){
		
		if (filters == null || filters.size() == 0){
			return "";
		}

		StringBuilder whereClause = new StringBuilder(" where ");
		final String AND = " and ";
		
		for (String key : filters.keySet()){
			Object value = filters.get(key);

			if (key.endsWith("kassaZettelNummer")){
				//  like for a string type needed:
				whereClause.append("r." + key + " like '" + value + "%'" + AND);
			} else if ( key.endsWith("billAmount")){
				// like for a number type  type needed:
				whereClause.append("r." +key + " like " + value + "%" + AND);
			} else if ( key.endsWith("id")){
				whereClause.append("r." + key + " = " + value + AND);
			} else {
				// something else, like Id, so equals needed:
				whereClause.append(key + " = " + value + AND);
			}
		};
		
		int startIndex = whereClause.lastIndexOf(AND);
		whereClause.delete(startIndex, startIndex+AND.length());

		return whereClause.toString();
	}
	
	private String composeOrderClause (String sortField, SortOrder sortOrder){
		
		if (sortField!=null && sortField.trim().length() > 0){
			return " order by r." + sortField + " " + (sortOrder.equals(SortOrder.ASCENDING)? "ASC" : "DESC");
		}
		return "";
	}
}
