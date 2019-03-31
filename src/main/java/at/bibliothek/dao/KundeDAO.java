package at.bibliothek.dao;

import java.sql.SQLException;
import java.util.List;

import at.bibliothek.model.Kunde;

/**
 * 
 * @author User
 * 
 * Da at.bibliothek.model.Kunde eine abstrakte Klasse ist (und es keine Kunde objekte Instanziert werden können)
 * beinhaltet KundeDAO nur Methoden zum auslesen, aber nicht zum Speichern eines Kunden. 
 * 
 * Methoden zum Speichern eines Kunden sind in den DAO Klassen FirmaDAO und NatPersDAO.
 *
 */
public interface KundeDAO {

	public static final String TABLE_KUNDE = "kunde";

	public static final String DESCRIMINATOR_NAT_PERS = "N";
	public static final String DESCRIMINATOR_FIRMA = "F";

	public static final String COLUMN_ID= "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_ADDRESS = "adress";
	public static final String COLUMN_DESCRIMINATOR = "kunde_selector";
	
	
	
	
	List <? extends Kunde> findByName(String name) throws SQLException;

}
