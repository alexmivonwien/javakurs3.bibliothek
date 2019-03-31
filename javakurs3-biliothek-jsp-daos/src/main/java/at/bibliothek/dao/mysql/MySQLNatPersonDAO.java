package at.bibliothek.dao.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import at.bibliothek.dao.KundeDAO;
import at.bibliothek.dao.NatPersonDAO;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.NatuerlichePerson;

public class MySQLNatPersonDAO extends MySQLKundeDAO implements NatPersonDAO {
	
	@SuppressWarnings("unchecked")
	public List <NatuerlichePerson> findByName(String name) throws SQLException{
		return  (List<NatuerlichePerson>) super.findByName(name);
	}

	@Override
	public List<NatuerlichePerson> findByName(String vorname, String nachname) throws SQLException {
		String SELECTOR_VALUE = MySQLNatPersonDAO.DESCRIMINATOR_VALUE_NATPERS;

		
		PreparedStatement preparedStatement = this.getConnection().prepareStatement(" select * from " + KundeDAO.TABLE_KUNDE + " where " + COLUMN_NAME + " = ? and " + COLUMN_FAMILY_NAME + " = ? and " + COLUMN_DESCRIMINATOR + " = ? ");
		preparedStatement.setString(1, vorname);
		preparedStatement.setString(2, nachname);
		preparedStatement.setString(3, SELECTOR_VALUE);
		
		ResultSet result = preparedStatement.executeQuery();
		List<Kunde> kundenList = convertToKundenList(result);
		
		return conertKundenListToNatPersList(kundenList);
	}
	
	private List<NatuerlichePerson> conertKundenListToNatPersList (List<Kunde> kundenList){
		List<NatuerlichePerson> natPersList = new ArrayList <NatuerlichePerson>();
		for (Kunde kunde : kundenList){
			if (kunde instanceof NatuerlichePerson){
				natPersList.add((NatuerlichePerson)kunde);
			}
			
		}
		
		return natPersList;
	}
	

	@Override
	public void save(NatuerlichePerson natpers) throws SQLException {

		String SELECTOR_VALUE = MySQLNatPersonDAO.DESCRIMINATOR_VALUE_NATPERS;
		
		PreparedStatement preparedStatement = this.getConnection().prepareStatement(" insert into " + NatPersonDAO.TABLE_KUNDE + " values (null, ?, ?, ?, ?, null, ?) ");
		preparedStatement.setString(1, natpers.getNachname());
		preparedStatement.setString(2, natpers.getNachname());
		preparedStatement.setString(3, natpers.getAdresse());
		preparedStatement.setDate(4, new Date(natpers.getGebDatum().getTime()));
		preparedStatement.setString(5, SELECTOR_VALUE);
		
		int affectedRows = preparedStatement.executeUpdate();
		
		if (affectedRows!=1){
			// what happened here?....
		}
		
	}
	

	@Override
	public void update(NatuerlichePerson natpers) {
		// TODO Auto-generated method stub
		
	}
	
}
