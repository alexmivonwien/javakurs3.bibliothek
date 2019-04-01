package at.bibliothek.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import at.bibliothek.model.ApplicationException;
import at.bibliothek.model.Firma;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.NatuerlichePerson;
import at.bibliothek.services.database.KundeDBService;
import at.bibliothek.services.database.ServiceException;
import at.bibliothek.web.model.LazyKundeDataModel;

/**
 * 
 * @author Alex-Mi
 *
 */
@Named
@RequestScoped
public class KundenBean {

	private Logger logger = Logger.getLogger(KundenBean.class.getName());

	@Inject
	private KundeDBService kundenDBService;

	private LazyKundeDataModel dataModel;

	private List<? extends Kunde> allKundenList;

	private Kunde selektierterKunde;

	public KundenBean() {
	}

	@PostConstruct
	private void init() {
		this.dataModel = new LazyKundeDataModel(kundenDBService);
	}

	public List<? extends Kunde> getAllKundenList() throws ApplicationException {
		if (allKundenList == null) {
			try {
				allKundenList = kundenDBService.findAllKunden("");
			} catch (ServiceException se) {
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
//
//		Kunde kunde = null;
//
//		NewCustomerBean.KundeTyp kundenTyp = Integer.MIN_VALUE;
//
//		try {
//			kundenTyp = Integer.valueOf(newCustomer.getKundeTyp());
//		} catch (NumberFormatException e) {
//			return;
//		}
//
//		if (kundenTyp == NewCustomerBean.KundeTyp.TYP_NATPERS) {
//			kunde = new NatuerlichePerson();
//			NatuerlichePerson natPers = (NatuerlichePerson) kunde;
//			natPers.setNachname(newCustomer.getNachname());
//			natPers.setGebDatum(newCustomer.getGebDatum());
//
//		} else if (kundenTyp == NewCustomerBean.KundeTyp.TYP_FIRMA) {
//			kunde = new Firma();
//			Firma fa = new Firma();
//			fa.setSteuerNummer(newCustomer.getSteuerNummer());
//
//		} else { // kundeTyp nicht bekannt, also weg von der Methode:
//
//			return;
//		}
//
//		kunde.setAdresse(newCustomer.getAddresse());
//		kunde.setVorname(newCustomer.getVorname());
//
//		try {
//			kundenDBService.saveKunde(kunde);
//
//		} catch (ServiceException e) {
//
//			logger.error("An exception occured while trying to save new customer", e);
//			throw new ApplicationException(
//					"An exception occured while trying to save new customer. See log for details");
//		}
	}

	public LazyKundeDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyKundeDataModel dataModel) {
		this.dataModel = dataModel;
	}

	public Kunde getSelektierterKunde() {
		return selektierterKunde;
	}

	public void setSelektierterKunde(Kunde selektierterKunde) {
		this.selektierterKunde = selektierterKunde;
	}

}
