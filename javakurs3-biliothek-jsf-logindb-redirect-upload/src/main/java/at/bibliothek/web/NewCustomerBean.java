package at.bibliothek.web;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import at.bibliothek.model.Firma;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.NatuerlichePerson;
import at.bibliothek.services.database.KundeDBService;
import at.bibliothek.services.database.ServiceException;

/**
 * 
 * @author Alex-Mi
 *
 */
@ManagedBean
@ViewScoped
public class NewCustomerBean implements Serializable {

	public static final String SESSION_ATTRIBUTE_CUST_ID = "session.customer.id";
	
	private static final long serialVersionUID = 1L;

	public enum KundeTyp {

		TYP_NATPERS("Nat. Person"), TYP_FIRMA("Firma");

		private String value;

		private KundeTyp(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}

	}

	private KundeTyp custmerType;
	private Map<String, KundeTyp> custmerTypes;

	private long savedKundeId;

	@Inject
	private KundeDBService kundeService;

	private Logger logger = Logger.getLogger(NewCustomerBean.class.getName());

	private String vorname;

	private String nachname;

	private String addresse;

	private Date gebDatum;

	private String steuerNummer;

	private String kundeTyp = Integer.MIN_VALUE + "";

	@PostConstruct
	public void init() {
		custmerTypes = new HashMap<String, KundeTyp>();
		custmerTypes.put(KundeTyp.TYP_NATPERS.value, KundeTyp.TYP_NATPERS);
		custmerTypes.put(KundeTyp.TYP_FIRMA.value, KundeTyp.TYP_FIRMA);
	}

	public KundeTyp getCustmerType() {
		return custmerType;
	}

	public void setCustmerType(KundeTyp custmerType) {
		this.custmerType = custmerType;
	}

	public Map<String, KundeTyp> getCustmerTypes() {
		return custmerTypes;
	}

	public void setCustmerTypes(Map<String, KundeTyp> custmerTypes) {
		this.custmerTypes = custmerTypes;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getAddresse() {
		return addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}

	public Date getGebDatum() {
		return gebDatum;
	}

	public void setGebDatum(Date date) {
		this.gebDatum = date;
	}

	public String getSteuerNummer() {
		return steuerNummer;
	}

	public void setSteuerNummer(String steuerNummer) {
		this.steuerNummer = steuerNummer;
	}

	public String getKundeTyp() {
		return kundeTyp;
	}

	public void setKundeTyp(String kundenTyp) {
		this.kundeTyp = kundenTyp;
	}

	public String saveNewCustomer(ActionEvent e) {

		if (!validateFields()) { // remain on the same page
			return null;
		}

		Kunde neuerKunde = null;

		switch (this.custmerType) {
		case TYP_NATPERS: {
			neuerKunde = new NatuerlichePerson();
			((NatuerlichePerson) neuerKunde).setGebDatum(this.gebDatum);
			((NatuerlichePerson) neuerKunde).setNachname(nachname);
			break;
		}

		case TYP_FIRMA: {
			neuerKunde = new Firma();
			((Firma) neuerKunde).setSteuerNummer("123456");
			break;
		}
		}

		neuerKunde.setVorname(vorname);
		neuerKunde.setAdresse(this.addresse);

		try {
			savedKundeId = kundeService.saveKunde(neuerKunde);
			
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute(SESSION_ATTRIBUTE_CUST_ID, savedKundeId);

		} catch (ServiceException se) {
			this.logger.error(se.getMessage(), se);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Unable to save the new customer: " + se.getMessage()));
		}

		return null;
	}

	public long getSavedKundeId() {
		return savedKundeId;
	}

	public void setSavedKundeId(long savedKundeId) {
		this.savedKundeId = savedKundeId;
	}

	private boolean validateFields() {

		if (this.custmerType == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please enter a customer type"));

			return false;
		}

		if (this.vorname == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Please enter a customer name / first name"));

			return false;
		}

		if (this.addresse == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please enter a customer adress"));

			return false;
		}

		return true;

	}
}
