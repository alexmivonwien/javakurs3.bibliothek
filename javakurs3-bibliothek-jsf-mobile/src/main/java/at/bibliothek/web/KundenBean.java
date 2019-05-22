package at.bibliothek.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import at.bibliothek.model.ApplicationException;
import at.bibliothek.model.Kunde;
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
	public static final String SELECTED_CUSTOMER = "selectedCustomer";

	@Inject
	private KundeDBService kundenDBService;

	private LazyKundeDataModel dataModel;

	private Kunde selektierterKunde;
	private int selectedCustId;
	


	public KundenBean() {
	}

	@PostConstruct
	private void init() {
		this.dataModel = new LazyKundeDataModel(kundenDBService);
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

	public int getSelectedCustId() {
		return selectedCustId;
	}

	public void setSelektierterKunde(Kunde selektierterKunde) {
		this.selektierterKunde = selektierterKunde;
	}

	public void onRowSelect(SelectEvent event) {
		selectedCustId = ((Kunde) event.getObject()).getId();
	}
	
	public void onRowDblClick(final SelectEvent event) {
		selectedCustId = ((Kunde) event.getObject()).getId();
	 
		try {
			ExternalContext extCtx = FacesContext.getCurrentInstance()
					.getExternalContext();

			extCtx.getFlash().put(SELECTED_CUSTOMER,((Kunde) event.getObject()));
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("rents.xhtml?custId=" +selectedCustId);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

	}
}
