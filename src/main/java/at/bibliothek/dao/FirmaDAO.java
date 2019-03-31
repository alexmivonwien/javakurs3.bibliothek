package at.bibliothek.dao;

import java.sql.SQLException;
import java.util.List;

import at.bibliothek.model.Firma;

/**
 * 
 * @author User
 *
 */
public interface FirmaDAO extends KundeDAO {

	public static final String DESCRIMINATOR_VALUE_FIRMA = "F";
	public static final String COLUMN_TAX_NUMBER = "steuernummer";

	
	public List <Firma> findBySteuernummer(String steuernummer) throws SQLException;
	
	public void saveOrUpdate (Firma firma ) throws SQLException;
}
