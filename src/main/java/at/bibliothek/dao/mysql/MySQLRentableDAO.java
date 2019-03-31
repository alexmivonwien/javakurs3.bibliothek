package at.bibliothek.dao.mysql;

import java.util.List;

import at.bibliothek.dao.AbstractDatabaseDAO;
import at.bibliothek.dao.RentableDAO;
import at.bibliothek.model.Rentable;

public class MySQLRentableDAO extends AbstractDatabaseDAO  implements RentableDAO {

	@Override
	public List <Rentable> findByTitle(String title){
		return null;
	};
	
	@Override
	public void save (Rentable rentable) {
		
	}
	
}
