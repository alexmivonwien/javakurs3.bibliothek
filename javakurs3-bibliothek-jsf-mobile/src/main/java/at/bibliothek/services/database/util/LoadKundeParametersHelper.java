package at.bibliothek.services.database.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

public class LoadKundeParametersHelper {
	
	public static final String JOINING_OR = " OR ";
	public static final String JOINING_AND = " AND ";
	public static final String CONDITION_WHERE = " WHERE ";


	private int first;
	private int pageSize;
	private String sortField;
	private SortOrder sortOrder;
	private Map<String, Object> filters;

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Map<String, Object> getFilters() {
		return filters == null ? new HashMap<>() : filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	/**
	 * utility method to check the consistency of the arguments
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @return
	 */
	public boolean checkValidParametersCombination() {
		
		if (filters.size() == 0)
			return true;

		if ((first > 0) && sortOrder == null) {
			return false;
		}

		if (sortField == null && sortOrder != null) {
			
			if (filters.size() >= 1) {
				sortField = filters.entrySet().iterator().next().getKey();
				
			} else{
				
				return false;
			}
		}

		if (sortField == null && (first > 0 || pageSize > 0)) {
			return false;
		}

		return true;
	}


	public String addFiltersToQuery() {
		
		if (filters.size() == 0)
			return StringUtils.EMPTY;

		StringBuilder whereCondition = new StringBuilder(CONDITION_WHERE);

		int pseudoCounter = 0;
		for (Entry<String, Object> entry : filters.entrySet()) {
			whereCondition.append("k." + entry.getKey() + " LIKE :" + "x" + (++pseudoCounter) );
			whereCondition.append(JOINING_AND);
		}
		
		if (whereCondition.length() > CONDITION_WHERE.length()) {
			whereCondition.delete(whereCondition.length() - JOINING_AND.length() , whereCondition.length());
		}
		
		return whereCondition.toString();
	}
	

	public void setQueryParameters(Query emQuery) {
		
		int pseudoCounter = 0;
		for (Entry<String, Object> entry : filters.entrySet()) {
			emQuery.setParameter("x" + (++pseudoCounter), entry.getValue()  + "%");
		}
		
		if (first >= 0 ) {
			emQuery.setFirstResult(first);
			}
		
		if (pageSize > 0){
			emQuery.setMaxResults(pageSize);
		}
	}
		
}
