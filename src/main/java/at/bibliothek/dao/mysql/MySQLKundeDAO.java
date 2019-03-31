package at.bibliothek.dao.mysql;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import at.bibliothek.dao.AbstractDatabaseDAO;
import at.bibliothek.dao.KundeDAO;
import at.bibliothek.model.Firma;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.NatuerlichePerson;

public class MySQLKundeDAO extends AbstractDatabaseDAO implements KundeDAO {

	//@Override
	public List <? extends Kunde> findByName(String name) throws SQLException{
		
		String SELECTOR_VALUE = null;
		
		if (this instanceof MySQLFirmaDAO) {
			SELECTOR_VALUE = MySQLFirmaDAO.DESCRIMINATOR_VALUE_FIRMA;
		}else if (this instanceof MySQLNatPersonDAO) {
			SELECTOR_VALUE = MySQLNatPersonDAO.DESCRIMINATOR_VALUE_NATPERS;
		}
		
		StringBuilder sqlStatement = new StringBuilder(" select * from " + KundeDAO.TABLE_KUNDE + " where " + COLUMN_NAME);
		
		if (name == null || name.trim().equals("")){
			sqlStatement.append(" LIKE '%' ");
		}else{
			sqlStatement.append(" LIKE ? ");
		}
		
		if (SELECTOR_VALUE!=null){
			sqlStatement.append(" and " + COLUMN_DESCRIMINATOR + " = ? ");
		}
		PreparedStatement preparedStatement = this.getConnection().prepareStatement(sqlStatement.toString() );
		
		if (name != null && !name.trim().equals("")){
			preparedStatement.setString(1, name + "%");
		}
		
		if (SELECTOR_VALUE!=null){
			preparedStatement.setString(2, SELECTOR_VALUE);
		}
		
		ResultSet result = preparedStatement.executeQuery();
		return convertToKundenList(result);
	}

	
	protected List<Kunde> convertToKundenList(ResultSet resultSet) throws SQLException {

		List<Kunde> kundeListResult = new ArrayList<Kunde>();

		while (resultSet.next()) {
			Kunde kunde = null;
		
			int id = resultSet.getInt(COLUMN_ID);
			String name = resultSet.getString(COLUMN_NAME);
			String adress = resultSet.getString(COLUMN_ADDRESS);

			if (this instanceof MySQLFirmaDAO) {
				String steuerNummer = resultSet.getString(MySQLFirmaDAO.COLUMN_TAX_NUMBER);
				Firma firma = new Firma(name, adress);
				firma.setSteuerNummer(steuerNummer);
				kunde = firma;
			} else if (this instanceof MySQLKundeDAO) {
				String nachname = resultSet.getString(MySQLNatPersonDAO.COLUMN_FAMILY_NAME);
				Date gebDatum = resultSet.getDate(MySQLNatPersonDAO.COLUMN_BIRTH_DATE);

				NatuerlichePerson natPers = new NatuerlichePerson(name, nachname, adress, gebDatum);
				kunde = natPers;
			}
			
			kunde.setId(id);
			kundeListResult.add(kunde); 
		}

		return kundeListResult;
	}

}
