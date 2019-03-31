package at.bibliothek.web;

import java.io.IOException;
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
import at.bibliothek.services.database.KundeDBService;
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
public class RentsBean {

	private Logger logger = Logger.getLogger(RentsBean.class.getName());
	
	public static final String SELECTED_RENT = "selectedRent";

	@Inject
	private RentDBService rentsDBService;
	
	@Inject
	private KundeDBService kundeDBService;

	private List<Rent> rentsList;
	
	private Rent rentForKassaZettelNummer;

	private String filterByCustomerId;
	
	private String rentsOfCustomer;

	private String filterByDate;

	private String filterByKassaZettelNummer;
	
	private LazyRentsDataModel dataModel;
	
	private Rent selectedRent;
	
	private Long selectedRentId;

	public RentsBean() {
	}
	
	@PostConstruct
	public void init() {
		ExternalContext extCtx = FacesContext.getCurrentInstance().getExternalContext();
		String custId = ((HttpServletRequest)extCtx.getRequest()).getParameter("custId");
		
		
		if (StringUtils.isNotEmpty(custId)){
			this.dataModel = new LazyRentsDataModel(rentsDBService, Long.valueOf(custId));
			
			filterByCustomerId = custId;
			if ( this.rentsOfCustomer==null){
				Kunde selectedCust = (Kunde)extCtx.getFlash().get(KundenBean.SELECTED_CUSTOMER);
				
				if (selectedCust!=null) {//in case user came with double click from the "customers.jsf" view
					this.rentsOfCustomer = selectedCust.getVorname();
				} else { //in case the user hits directly the rents.jsf page by providing custId as a request-parameter
					this.rentsOfCustomer =  kundeDBService.getKundeById(Integer.valueOf(custId)).getVorname();
				}
			}
		} else {
			
			this.dataModel = new LazyRentsDataModel(rentsDBService, null);
			
		}
	}
	
	public String getRentsOfCustomer() {
		return rentsOfCustomer;
	}
	
	public LazyRentsDataModel getDataModel() {
		return dataModel;
	}
	
	public Rent getSelectedRent() {
		return selectedRent;
	}

	public void setSelectedRent(Rent selectedRent) {
		this.selectedRent = selectedRent;
	}
	
	public void onRowDblClick(final SelectEvent event) {
		this.selectedRentId = ((Rent) event.getObject()).getId();
	 
		try {
			ExternalContext extCtx = FacesContext.getCurrentInstance()
					.getExternalContext();

			extCtx.getFlash().put(SELECTED_RENT,((Rent) event.getObject()));
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("rent_details.xhtml?rentId=" +this.selectedRentId);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

	}


	public void setDataModel(LazyRentsDataModel dataModel) {
		this.dataModel = dataModel;
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
}
