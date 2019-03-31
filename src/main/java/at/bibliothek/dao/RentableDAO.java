package at.bibliothek.dao;

import java.util.List;

import at.bibliothek.model.Rentable;

public interface RentableDAO {
	
	public static final String COLUMN_TITLE = "titel";

	public static final String COLUMN_DESCRIMINATOR = "rentable_selector";
	public static final String DESCRIMINATOR_CD = "C";
	public static final String DESCRIMINATOR_DVD = "D";
	public static final String DESCRIMINATOR_BOOK = "B";

	
	List <Rentable> findByTitle(String title);
	
	void save (Rentable rentable);

}
