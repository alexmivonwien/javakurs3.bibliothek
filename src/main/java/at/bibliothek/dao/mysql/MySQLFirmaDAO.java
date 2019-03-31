package at.bibliothek.dao.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import at.bibliothek.dao.FirmaDAO;
import at.bibliothek.dao.NatPersonDAO;
import at.bibliothek.model.Firma;
import at.bibliothek.model.Kunde;

public class MySQLFirmaDAO extends MySQLKundeDAO implements FirmaDAO{ 

	public List <Firma> findByName(String name) throws SQLException{
		List <? extends Kunde> result = super.findByName(name);
		return convertToFirmenList(result);
	}
	
	
	private List <Firma> convertToFirmenList(List <? extends Kunde> inputList){
		List <Firma> firmenListResult = new ArrayList<Firma> ();
		
		for (Kunde kunde : inputList){
			firmenListResult.add((Firma)kunde);
		}
		
		return firmenListResult;
	}


	@Override
	public List<Firma> findBySteuernummer(String steuernummer) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void saveOrUpdate(Firma firma) throws SQLException {
		String SELECTOR_VALUE = MySQLFirmaDAO.DESCRIMINATOR_FIRMA;
		
		PreparedStatement preparedStatement = this.getConnection().prepareStatement(" insert into " + FirmaDAO.TABLE_KUNDE + " values (null, ?, null, ?,null,  ?, ? )");
		preparedStatement.setString(1, firma.getName());
		preparedStatement.setString(2, firma.getAdresse());
		preparedStatement.setString(3, firma.getSteuerNummer());
		preparedStatement.setString(4, SELECTOR_VALUE);
		
		int affectedRows = preparedStatement.executeUpdate();
		
		if (affectedRows!=1){
			// what happened here?....
		}
		
	}
}
