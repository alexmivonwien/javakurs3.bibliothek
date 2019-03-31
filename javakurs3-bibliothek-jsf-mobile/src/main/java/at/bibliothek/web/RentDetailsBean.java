package at.bibliothek.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import at.bibliothek.model.ApplicationException;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.Rent;
import at.bibliothek.model.Rentable;
import at.bibliothek.services.database.RentDBService;
import at.bibliothek.services.database.ServiceException;
import at.bibliothek.web.model.LazyRentsDataModel;

/**
 * 
 * @author Alex-Mi
 *
 */

@Named
@ViewScoped
public class RentDetailsBean {

	private Logger logger = Logger.getLogger(RentDetailsBean.class.getName());
	
	@Inject
	private RentDBService rentsDBService;
	
	private Rent selectedRent;
	
	private String kundeName;
	
	private String kasssaZettelNo;

	
	public RentDetailsBean() {
	}
	
	@PostConstruct
	public void init() {
		ExternalContext extCtx = FacesContext.getCurrentInstance().getExternalContext();
		Rent selRent = (Rent)extCtx.getFlash().get(RentsBean.SELECTED_RENT);
		this.kundeName = selRent.getKunde().getVorname();
		this.kasssaZettelNo = selRent.getKassaZettelNummer();
		this.selectedRent = selRent;
	}
	
	
	public String getKundeName() {
		return kundeName;
	}

	public String getKasssaZettelNo() {
		return kasssaZettelNo;
	}

	public List<Rentable> getRentables() {
		return this.rentsDBService.getRentables(this.selectedRent);
	}
	
	public Rent getSelectedRent() {
		return selectedRent;
	}

}
