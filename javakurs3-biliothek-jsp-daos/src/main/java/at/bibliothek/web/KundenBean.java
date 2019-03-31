package at.bibliothek.web;

import java.util.List;

import org.apache.log4j.Logger;

import at.bibliothek.model.ApplicationException;
import at.bibliothek.model.Firma;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.NatuerlichePerson;
import at.bibliothek.services.database.KundeDBService;
import at.bibliothek.services.database.ServiceException;

/**
 * 
 * @author User
 *
 */
public class KundenBean {

	private Logger logger = Logger.getLogger(KundenBean.class.getName());

	private KundeDBService kundenDBService = new KundeDBService();
	
	private List<? extends Kunde> allKundenList;

	public KundenBean() {
	}

	public List<? extends Kunde> getAllKundenList() throws ApplicationException {
		if (allKundenList==null){
			try{
				allKundenList = kundenDBService.findAllKunden("");
			}catch (ServiceException se){
				logger.error(se.getMessage(), se);
				throw new ApplicationException(se.getMessage(), se);
			}
		}
		
		return allKundenList;
	}

	public void setAllKundenList(List<Kunde> kundenList) {
		this.allKundenList = kundenList;
	}

	/**
	 * diese Methode speichert den neuen Kunde durch KustomerDBService
	 * 
	 * @param newCustomer
	 * @throws ApplicationException 
	 */
	public void setNewCustomer(NewCustomerBean newCustomer) throws ApplicationException {
		
		Kunde kunde = null;
		
		int kundenTyp = Integer.MIN_VALUE;
		
		try{
			kundenTyp = Integer.valueOf(newCustomer.getKundeTyp());
		}catch (NumberFormatException e){
			return;
		}
		
		if ( kundenTyp == NewCustomerBean.TYP_NATPERS){
			kunde = new NatuerlichePerson();
			NatuerlichePerson natPers = (NatuerlichePerson)kunde;
			natPers.setNachname(newCustomer.getNachname());
			natPers.setGebDatum(newCustomer.getGebDatum());
			
		} else if (kundenTyp == NewCustomerBean.TYP_FIRMA){
			kunde = new Firma();
			Firma fa = new Firma();
			fa.setSteuerNummer(newCustomer.getSteuerNummer());
			
		}else { // kundeTyp nicht bekannt, also weg von der Methode:
			
			return;
		}
		
		kunde.setAdresse(newCustomer.getAddresse());
		kunde.setVorname(newCustomer.getVorname());
		
		try {
			kundenDBService.saveKunde(kunde);
			
		} catch (ServiceException e) {
			
			logger.error("An exception occured while trying to save new customer", e);
			throw new ApplicationException("An exception occured while trying to save new customer. See log for details");
		}
	}

}
