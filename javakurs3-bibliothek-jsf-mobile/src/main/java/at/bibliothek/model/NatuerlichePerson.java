package at.bibliothek.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

@Entity
@DiscriminatorValue("N")
public class NatuerlichePerson extends Kunde {
	
	@Transient
	private Logger loger = Logger.getLogger(this.getClass().getName());

	@Column
	private String nachname;
	
	@Column (name= "birthDate")
	private Date gebDatum;
	
	public NatuerlichePerson(){
		super();
	}

	public NatuerlichePerson(String name, String nachname, String adres, Date gebDatum) {
		super(name, adres);
		this.nachname = nachname;
		this.gebDatum = gebDatum;
	}

	public boolean isRechtsFaehig() {
		if ( getAlter() > 18){ 
			return true;
		}
		return false;
	}
	
	public int getAlter(){
		
		// wir komvertiren das Date-Objekt zu einem Instant Objekt (gibt es erst seit Java 1.8:)
		
		LocalDate toLocalDate = Instant.ofEpochMilli(gebDatum.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		//Instant inst = Instant.ofEpochMilli(gebDatum.getTime());
		
		// nun legen wir eine Periode beginnend mit dem vom gebDatum-erzeugten LocalDate und
		//endend jetzt:
		Period period = Period.between(toLocalDate, LocalDate.now());
		
		// hier wird ermittelt wieviel Jahren das Period beinhaltet:
		int anzahlAnJahrenImPeriod = period.getYears();
		
		return anzahlAnJahrenImPeriod;

	}
	
	public void setGebDatum(String gebDatum) throws ApplicationException {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FOMRAT_PATTERN_1);

		try {
			this.gebDatum = sdf.parse(gebDatum);

		} catch (ParseException pe) {
			loger.info("Unsuccessfull attempt to parse " + gebDatum);
			
			sdf = new SimpleDateFormat(Constants.DATE_FOMRAT_PATTERN_2);

			try {
				this.gebDatum = sdf.parse(gebDatum);

			} catch (ParseException pe2) {
				throw new ApplicationException("Unable to parse geburtsDatum: " + gebDatum, pe2);
			}
		}

	}
	
	public void setGebDatum(Date date)  {
		this.gebDatum = date;

	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		super.setVorname(vorname);
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public Date getGebDatum() {
		return gebDatum;
	}

	public String toString() {
		return "Nat.Pers.: " + this.getVorname() + " " + this.getNachname() + "; alter = " + this.getAlter() + " Adresse: " + this.getAdresse();
	}

}
