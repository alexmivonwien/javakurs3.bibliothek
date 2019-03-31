package at.bibliothek.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import at.bibliothek.model.Kunde;
import at.bibliothek.model.Rent;

public interface RentDAO {

	//<T> List <Rent> findBy(T template );
	
	/**
	 * diese Methode l�dt alle Rents zu einem Kunden.
	 * Dabei werden die Rentables "lazy loaded", w�hrend die Kundendetails - "eagerly loaded"
	 */
	List<Rent> findBy(String kundeId) throws SQLException;
	
	/**
	 * diese Methode l�dt alle Rent, die das Kunden zum angegebenen Datum 
	 * ausgeliehen hat. Wenn das Datum null ist, werden einfach alle Rents
	 * zum Kunde zur�ckgegeben.
	 * Dabei werden  sowohl die Rentables, als auch die Kundendetails - "eagerly loaded".
	 */
	
	List <Rent> findBy(Kunde kunde, Date date ) throws SQLException;
	
	/**
	 * diese Methode l�dt das Rent, das einer Kassazettel entspricht.
	 * Dabei werden  sowohl die Rentables, als auch die Kundendetails - "eagerly loaded".
	 */
	Rent getRent(String kassaZettelNummer) throws SQLException;
	
	
	
	void saveOrUpdate (Rent rent);
}
