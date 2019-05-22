package at.bibliothek.web.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import at.bibliothek.model.Rent;
import at.bibliothek.services.database.RentDBService;

/**
 * 
 * @author alex-mi
 *
 */
public class LazyRentsDataModel extends LazyDataModel<Rent> {

	private static final long serialVersionUID = 1L;
	
	private RentDBService rentDBService;
	
	private Long custId;

	public LazyRentsDataModel(RentDBService rentDBService, Long custId) {
		this.rentDBService = rentDBService;
		this.custId = custId;
	}

	@Override
	public Rent getRowData(String rowKey) {
		return rentDBService.getRentById(Long.valueOf(rowKey));
	}

	@Override
	public Object getRowKey(Rent rent) {
		return rent.getId();
	}

	@Override
	public List<Rent> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		List<Rent> result = this.rentDBService.lazyLoadRents(first, pageSize, sortField, sortOrder, filters, custId);
		int nUmberOfEntries = this.rentDBService.getRentsCount(filters, custId);

		// rowCount
		this.setRowCount(nUmberOfEntries);
		this.setPageSize(pageSize);

		return result;
	}

}
