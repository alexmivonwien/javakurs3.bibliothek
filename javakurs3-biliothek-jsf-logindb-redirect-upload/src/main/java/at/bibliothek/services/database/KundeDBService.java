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

import at.bibliothek.model.Kunde;
import at.bibliothek.model.NatuerlichePerson;

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

	public List<NatuerlichePerson> findAllNatuerlichePersonen(String vorname, String nachname) throws ServiceException {

		List<NatuerlichePerson> result = new ArrayList<NatuerlichePerson>();

		Query query = em.createQuery(" from NatuerlichePerson where  vorname :v and nachname :n")
				.setParameter(1, vorname).setParameter(2, nachname);

		result = query.getResultList();

		return result;
	}

	/**
	 * creates a new customer (Kunde) in the database
	 * 
	 * @param kunde
	 * @throws ServiceException
	 */
	public long saveKunde(Kunde kunde) throws ServiceException {

		// https://stackoverflow.com/questions/4509086/what-is-the-difference-between-persist-and-merge-in-hibernate
		// Persist should be called only on new entities, while merge is meant
		// to reattach detached entities.
		em.persist(kunde);
		
		// The ID is only guaranteed to be generated at flush time. 
		// Persisting an entity only makes it "attached" to the persistence context. 
		// So, either flush the entity manager explicitely:
		em.flush();  // @see https://stackoverflow.com/questions/9732453/jpa-returning-an-auto-generated-id-after-persist
		return kunde.getId();
	}

	public List<? extends Kunde> findAllKunden(String name) throws ServiceException {

		List<? extends Kunde> result = new ArrayList<Kunde>();
		
		final String  QUERY_BEGIN = " from Kunde ";
		String queryComplete = name == null || name.trim().equals("") || name.equals("*") ? QUERY_BEGIN : QUERY_BEGIN + " where vorname :n";

		Query query = em.createQuery(queryComplete);
		
		if (queryComplete.length() > QUERY_BEGIN.length()) {
			query.setParameter(1, name);
		}
		
		result = query.getResultList();

		return result;
	}

	public List<Kunde> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		List<Kunde> result = new ArrayList<Kunde>();

		try {
			result = (List<Kunde>) this.findAllKunden("*");

		} catch (ServiceException se) {
			logger.error(se.getMessage(), se);
		}

		return result;
	}

	public Kunde getKundeById(long id) {

		return em.find(Kunde.class, Long.valueOf(id));

	}

}
