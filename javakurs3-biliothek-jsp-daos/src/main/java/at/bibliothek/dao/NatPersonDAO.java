package at.bibliothek.dao;

import java.sql.SQLException;
import java.util.List;

import at.bibliothek.model.NatuerlichePerson;

/**
 * 
 * @author User
 *
 */
public interface NatPersonDAO extends KundeDAO {
	

	public static final String DESCRIMINATOR_VALUE_NATPERS = "N";
	public static final String COLUMN_BIRTH_DATE = "birthDate";
	public static final String COLUMN_FAMILY_NAME = "nachname";


	public List <NatuerlichePerson> findByName(String vorname, String nachname) throws SQLException;
	
	public void save(NatuerlichePerson natpers) throws SQLException;
	
	public void update(NatuerlichePerson natpers) throws SQLException;

}
