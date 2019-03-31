package at.bibliothek.web;

import org.apache.log4j.Logger;

public class NewCustomerBean {
	
	public static final int TYP_NATPERS = 1;
	public static final int TYP_FIRMA = 2;

	private Logger logger = Logger.getLogger(NewCustomerBean.class.getName());

	private String vorname;

	private String nachname;

	private String addresse;
	
	private String gebDatum;

	private String steuerNummer;
	
	private String kundeTyp = Integer.MIN_VALUE + "";
	
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getAddresse() {
		return addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}

	public String getGebDatum() {
		return gebDatum;
	}

	public void setGebDatum(String date) {
		this.gebDatum = date;
	}

	public String getSteuerNummer() {
		return steuerNummer;
	}

	public void setSteuerNummer(String steuerNummer) {
		this.steuerNummer = steuerNummer;
	}

	public String getKundeTyp() {
		return kundeTyp;
	}

	public void setKundeTyp(String kundenTyp) {
		this.kundeTyp = kundenTyp;
	}

}
