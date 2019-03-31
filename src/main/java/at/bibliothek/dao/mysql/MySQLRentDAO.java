package at.bibliothek.dao.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import at.bibliothek.dao.AbstractDatabaseDAO;
import at.bibliothek.dao.RentDAO;
import at.bibliothek.model.Buch;
import at.bibliothek.model.CD;
import at.bibliothek.model.DVD;
import at.bibliothek.model.Firma;
import at.bibliothek.model.Kunde;
import at.bibliothek.model.NatuerlichePerson;
import at.bibliothek.model.Rent;
import at.bibliothek.model.Rentable;

public class MySQLRentDAO extends AbstractDatabaseDAO implements RentDAO {

	Logger logger = Logger.getLogger(MySQLRentDAO.class.getName());

	public static final String TABLE_RENT = "rent";

	public static final String COLUMN_CASH_RECIEPT_ID = "kassaZettelNummer";
	public static final String COLUMN_RENTED_ON = "rentedOn";
	public static final String COLUMN_RENTED_TILL = "rentedTill";
	public static final String COLUMN_BETRAG = "billAmount";
	public static final String COLUMN_COMPLETED = "completed";
	public static final String COLUMN_CUSTOMER_ID = "kunde_id";

	public final static String RENT_EAGER_SELECT = " select r." + COLUMN_CASH_RECIEPT_ID + ", r." + COLUMN_RENTED_ON + ", r."
			+ COLUMN_RENTED_TILL + ", r." + COLUMN_BETRAG + ", k." + MySQLKundeDAO.COLUMN_NAME + ", k."
			+ MySQLNatPersonDAO.COLUMN_FAMILY_NAME + ", k." + MySQLNatPersonDAO.COLUMN_BIRTH_DATE + ", k."
			+ MySQLKundeDAO.COLUMN_DESCRIMINATOR + ", rebl." + MySQLRentableDAO.COLUMN_TITLE + ", rebl."
			+ MySQLRentableDAO.COLUMN_DESCRIMINATOR + " from " + TABLE_RENT
			+ " r, kunde k, rentable rebl, rent_rentable rr where r." + COLUMN_COMPLETED + "= 0 and r."
			+ COLUMN_CUSTOMER_ID + " = k.id and rr.rent_id = r.id and rr.rentable_id = rebl.id ";

	@Override
	public List<Rent> findBy(String kundeId) throws SQLException {

		StringBuilder sqlStatement = new StringBuilder(
				"select * from " + TABLE_RENT + " r, " + MySQLKundeDAO.TABLE_KUNDE + " k  where  r." + COLUMN_CUSTOMER_ID + " = k." +MySQLKundeDAO.COLUMN_ID +  " and r." + COLUMN_CUSTOMER_ID + " = ? ");

		PreparedStatement preparedStatement = this.getConnection().prepareStatement(sqlStatement.toString());

		preparedStatement.setInt(1, Integer.valueOf(kundeId));

		ResultSet result = preparedStatement.executeQuery();
		return convertToRentList(result, true, false, true);
	}

	@Override
	public List<Rent> findBy(Kunde kunde, java.util.Date date) throws SQLException {

		StringBuilder sqlStatement = new StringBuilder(RENT_EAGER_SELECT);

		boolean vornameVorhanden = false;
		if (kunde!=null && kunde.getVorname() != null) {
			vornameVorhanden = true;
			sqlStatement.append(" and k." + MySQLKundeDAO.COLUMN_NAME + " = ? ");
		}

		boolean nachnameVorhanden = false;
		if (kunde != null && kunde instanceof NatuerlichePerson && ((NatuerlichePerson) kunde).getNachname() != null) {
			nachnameVorhanden = true;
			sqlStatement.append(" and k." + MySQLNatPersonDAO.COLUMN_FAMILY_NAME + " = ? ");
		}

		boolean idVorhanden = false;
		if (kunde != null &&  kunde.getId() > 0) {
			idVorhanden = true;
			sqlStatement.append("and k." + MySQLKundeDAO.COLUMN_ID + " = ? ");
		}

		boolean adresseVorhanden = false;
		if (kunde != null && kunde.getAdresse() != null) {
			adresseVorhanden = true;
			sqlStatement.append(" and k." + MySQLKundeDAO.COLUMN_ADDRESS + " = ? ");
		}

		boolean datumVorhanden = false;
		if (date != null) {
			datumVorhanden = true;
			sqlStatement.append(" and r." + COLUMN_RENTED_ON + " = ? ");
		}

		PreparedStatement preparedStatement = this.getConnection().prepareStatement(sqlStatement.toString());

		int pos = 0;

		if (vornameVorhanden) {
			preparedStatement.setString(++pos, kunde.getVorname());
		}

		if (nachnameVorhanden) {
			preparedStatement.setString(++pos, ((NatuerlichePerson) kunde).getNachname());
		}

		if (idVorhanden) {
			preparedStatement.setInt(++pos, kunde.getId());
		}

		if (adresseVorhanden) {
			preparedStatement.setString(++pos, kunde.getAdresse());

		}
		if (datumVorhanden) {
			preparedStatement.setDate(++pos, new Date(date.getTime()));

		}

		ResultSet result = preparedStatement.executeQuery();

		return convertToRentList(result, true, true, false);
	}

	@Override
	public Rent getRent(String kassaZettelNummer) throws SQLException {

		StringBuilder sqlStatement = new StringBuilder(RENT_EAGER_SELECT);
		sqlStatement.append(" and r." + MySQLRentDAO.COLUMN_CASH_RECIEPT_ID + " = ? ");
		
		PreparedStatement preparedStatement = this.getConnection().prepareStatement(sqlStatement.toString());

		preparedStatement.setString(1, kassaZettelNummer);
		
		ResultSet result = preparedStatement.executeQuery();

		List <Rent> resultRent = convertToRentList(result, true, true, false);
				
		return resultRent.size() > 0 ? resultRent.get(0) : null;
	}

	@Override
	public void saveOrUpdate(Rent rent) {
		// TODO Auto-generated method stub

	}

	private List<Rent> convertToRentList(ResultSet resultSet, boolean includeCustomerDetails, boolean includeRents, boolean includeStatus) throws SQLException {

		Map<String, Rent> rentMap = new HashMap<String, Rent>();

		while (resultSet.next()) {

			String kassaZettelNummer = resultSet.getString(COLUMN_CASH_RECIEPT_ID);
			Date rentedOn = resultSet.getDate(COLUMN_RENTED_ON);
			Date rentedTill = resultSet.getDate(COLUMN_RENTED_TILL);
			double betrag = resultSet.getDouble(COLUMN_BETRAG);
			Boolean completed = null;
			if (includeStatus){
				completed = resultSet.getBoolean(COLUMN_COMPLETED);
			}
			String name = null;
			String descriminatorKunde = null;

			if (includeCustomerDetails) { // include Kundedaten
				name = resultSet.getString(MySQLKundeDAO.COLUMN_NAME);
				descriminatorKunde = resultSet.getString(MySQLKundeDAO.COLUMN_DESCRIMINATOR);
			}

			Rent rent = rentMap.get(kassaZettelNummer);

			if (rent == null) {
				rent = new Rent();
				rent.setBetragzuZahlen(betrag);
				rent.setKassaZettelNummer(kassaZettelNummer);
				rent.setRentedOn(rentedOn);
				rent.setRentedTill(rentedTill);
				
				if (completed!=null){
					rent.setAbgeschlossen(completed);
				}

				Kunde kunde = null;

				if (includeCustomerDetails) { // include Kundedaten
					
					if (descriminatorKunde.equalsIgnoreCase(MySQLKundeDAO.DESCRIMINATOR_NAT_PERS)) {
						String nachname = resultSet.getString(MySQLNatPersonDAO.COLUMN_FAMILY_NAME);
						Date gebDate = resultSet.getDate(MySQLNatPersonDAO.COLUMN_BIRTH_DATE);
						kunde = new NatuerlichePerson(name, nachname, null, gebDate);

					} else if (descriminatorKunde.equalsIgnoreCase(MySQLKundeDAO.DESCRIMINATOR_FIRMA)) {
						kunde = new Firma(name, null);
					}
					
					rent.setKunde(kunde);
				}

			
				rentMap.put(kassaZettelNummer, rent);
			}

			if (includeRents) { // include Renatables-Daten

				String titel = resultSet.getString(MySQLRentableDAO.COLUMN_TITLE);
				String descriminatorRentable = resultSet.getString(MySQLRentableDAO.COLUMN_DESCRIMINATOR);

				Rentable rentable = null;
				if (descriminatorRentable.equalsIgnoreCase(MySQLRentableDAO.DESCRIMINATOR_CD)) {
					rentable = new CD(titel);
				} else if (descriminatorRentable.equalsIgnoreCase(MySQLRentableDAO.DESCRIMINATOR_BOOK)) {
					rentable = new Buch(titel, null, null);
				} else if (descriminatorRentable.equalsIgnoreCase(MySQLRentableDAO.DESCRIMINATOR_DVD)) {
					rentable = new DVD(titel, null);
				}
				rent.getRentables().add(rentable);
			}

		}

		return new ArrayList<Rent>(rentMap.values());
	}
}
