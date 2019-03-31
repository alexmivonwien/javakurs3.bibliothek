package at.bibliothek.web.model;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import at.bibliothek.model.Kunde;
import at.bibliothek.services.database.KundeDBService;

/**
 * 
 * @author alex-mi
 *
 */
public class LazyKundeDataModel extends LazyDataModel<Kunde> {

	private static final long serialVersionUID = 1L;
	
	private KundeDBService kundeDBService;

	public LazyKundeDataModel(KundeDBService kundeDBService) {
		this.kundeDBService = kundeDBService;
	}

	@Override
	public Kunde getRowData(String rowKey) {
		return kundeDBService.getKundeById(Integer.valueOf(rowKey));
	}

	@Override
	public Object getRowKey(Kunde kunde) {
		return kunde.getId();
	}

	@Override
	public List<Kunde> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		
		List result = this.kundeDBService.load(first, pageSize, sortField, sortOrder, filters);

		// rowCount
		this.setRowCount(result.size());
		this.setPageSize(pageSize);

		return result;
	}

}
