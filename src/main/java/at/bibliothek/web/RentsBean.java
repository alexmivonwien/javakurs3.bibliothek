package at.bibliothek.web;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import at.bibliothek.model.ApplicationException;
import at.bibliothek.model.Rent;
import at.bibliothek.services.database.RentDBService;
import at.bibliothek.services.database.ServiceException;

/**
 * 
 * @author User
 *
 */
public class RentsBean {

	private Logger logger = Logger.getLogger(RentsBean.class.getName());

	private RentDBService rentsDBService = new RentDBService();

	private List<Rent> rentsList;
	
	private Rent rentForKassaZettelNummer;

	private String filterByCustomerId;

	private String filterByDate;

	private String filterByKassaZettelNummer;

	public RentsBean() {
	}

	public String getFilterByKassaZettelNummer() {
		return filterByKassaZettelNummer;
	}

	public void setFilterByKassaZettelNummer(String filterByKassaZettelNummer) {
		this.filterByCustomerId = null;
		this.filterByDate = null;
		this.filterByKassaZettelNummer = filterByKassaZettelNummer;
	}

	public String getFilterByCustomerId() {
		return filterByCustomerId;
	}

	public void setFilterByCustomerId(String filterByCustomerId) {
		this.filterByKassaZettelNummer = null;
		this.filterByDate = null;
		this.filterByCustomerId = filterByCustomerId;
	}

	public String getFilterByDate() {
		return filterByDate;
	}

	public void setFilterByDate(String filterByDate) {
		this.filterByDate = filterByDate;
	}

	public Rent getRentForKassaZettelNummer() throws ApplicationException {
		Rent result = null;

		if (rentForKassaZettelNummer == null && filterByKassaZettelNummer != null) {
			try {
				result = rentsDBService.getRent(filterByKassaZettelNummer);
			} catch (ServiceException e) {
				logger.error(
						"A service exception occured when trying to obtain the rent for " + filterByKassaZettelNummer,
						e);
				// hier kann ich entscheiden, ob ich die Exception weiter
				// auslöse, wie:
				// throw new ApplicationException(e)
			}
			
			rentForKassaZettelNummer = result;
			
		} else if (rentForKassaZettelNummer!=null){
			
			return rentForKassaZettelNummer;
			
		}else {
			
			throw new ApplicationException(
					"You tried to get the rent for a filterByKassaZettelNummer that was null !!!");
		}
		return result;

	}

	public List<Rent> getRentsList() throws ApplicationException {
		if (filterByCustomerId != null && filterByDate == null && filterByKassaZettelNummer == null) {
			try {
				rentsList = rentsDBService.findByCustomerId(filterByCustomerId);
			} catch (ServiceException e) {
				logger.error("A service exception occured when trying to obtain all rents:", e);

				// hier kann ich entscheiden, ob ich die Exception weiter
				// auslöse, wie:
				// throw new ApplicationException(e)
				//
				// oder es verschweigen und einfach eine leere Liste
				// zurückgeben, wie:
				//
				// rentsList = new ArrayList();

			}
		} else { // no filters, so give all rents completed today:
			
			try {
				rentsList = rentsDBService.findBy(null, new Date());
			} catch (ServiceException e) {
				logger.error("A service exception occured when trying to obtain all rents:", e);
			}
			
		}
		return rentsList;
	}

	public void setRentsList(List<Rent> rentsList) {
		this.rentsList = rentsList;
	}
}
