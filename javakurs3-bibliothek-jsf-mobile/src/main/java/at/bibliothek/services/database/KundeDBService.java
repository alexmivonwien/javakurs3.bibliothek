package at.bibliothek.services.database;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;

import at.bibliothek.model.Constants;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.NatuerlichePerson;
import at.bibliothek.services.database.util.LoadKundeParametersHelper;

/**
 * @author Alex-Mi
 * 
 *         In Java EE 6 interfaces became absolutely optional. Neither in EJB
 *         3.1, nor CDI / JSR-330 you need interfaces. You can inject classes
 *         directly. They will be still proxied, so all aspects like
 *         persistence, transactions, interceptors, decorators are still
 *         available.
 * 
 *         http://www.adam-bien.com/roller/abien/entry/how_to_deal_with_interfaces
 * 
 */

@Stateless
public class KundeDBService {

	@PersistenceContext
	private EntityManager em;

	private static Logger logger = Logger.getLogger(KundeDBService.class.getName());

	/**
	 * creates a new customer (Kunde) in the database
	 * 
	 * @param kunde
	 * @throws ServiceException
	 */
	public long saveKunde(Kunde kunde) throws ServiceException {

		// https://stackoverflow.com/questions/4509086/what-is-the-difference-between-persist-and-merge-in-hibernate
		// Persist should be called only on new entities, while merge is meant
		// to reatach detached entities.
		em.persist(kunde);

		// The ID is only guaranteed to be generated at flush time.
		// Persisting an entity only makes it "attached" to the persistence context.
		// So, either flush the entity manager explicitely:
		em.flush(); // @see
					// https://stackoverflow.com/questions/9732453/jpa-returning-an-auto-generated-id-after-persist
		return kunde.getId();
	}

	/**
	 * selects the total number of customers corresponding to the filet criteria
	 * filters
	 * 
	 * @param filters
	 * @return
	 */
	public long getKundeCount(Map<String, Object> filters) {

		List<Long> result = new ArrayList<>();

		LoadKundeParametersHelper kundeParametersHelper = new LoadKundeParametersHelper();
		kundeParametersHelper.setFilters(filters);

		String QUERY_BEGIN = " select count (*) from Kunde k ";

		if (kundeParametersHelper.getFilters().size() > 0) {
			QUERY_BEGIN += kundeParametersHelper.addFiltersToQuery();
		}
		Query emQuery = em.createQuery(QUERY_BEGIN);
		result = (List<Long>) emQuery.getResultList();

		return result.size() > 0 ? result.get(0) : 0;

	}

	/**
	 * selects a "page" from the sorted list of all customers corresponding to the
	 * filters creiteria
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @return
	 */
	public List<? extends Kunde> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		List<? extends Kunde> result = new ArrayList<Kunde>();

		LoadKundeParametersHelper kundeParametersHelper = new LoadKundeParametersHelper();
		kundeParametersHelper.setFirst(first);
		kundeParametersHelper.setPageSize(pageSize);
		kundeParametersHelper.setSortField(sortField);
		kundeParametersHelper.setSortOrder(sortOrder);
		kundeParametersHelper.setFilters(filters);

		if (!kundeParametersHelper.checkValidParametersCombination()) {
			return result;
		}

		String QUERY_BEGIN = " select k from Kunde k ";

		if (kundeParametersHelper.getFilters().size() > 0) {
			QUERY_BEGIN += kundeParametersHelper.addFiltersToQuery();
		}

		if (kundeParametersHelper.getSortField() != null) {
			QUERY_BEGIN += " order by k." + kundeParametersHelper.getSortField();

			if (kundeParametersHelper.getSortOrder() != null) {
				QUERY_BEGIN += " "
						+ (kundeParametersHelper.getSortOrder().equals(Constants.SORT_ORDER_ASCENDING) ? " ASC"
								: " DESC");
			}
		} else {
			QUERY_BEGIN += " order by k.vorname asc";
		}

		Query emQuery = em.createQuery(QUERY_BEGIN);
		kundeParametersHelper.setQueryParameters(emQuery);

		emQuery.setFirstResult(first);
		emQuery.setMaxResults(pageSize);

		result = (List<Kunde>) emQuery.getResultList();
		return result;
	}

	/**
	 * loads a customer by id
	 * 
	 * @param id
	 * @return
	 */
	public Kunde getKundeById(int id) {
		return em.find(Kunde.class, Integer.valueOf(id));

	}
}
