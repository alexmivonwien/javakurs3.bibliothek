package at.bibliothek.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Rent implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public List <Rentable> getRentables() {
		return rentables;
	}
	
	public int getRentablesSize() {
		return rentables.size();
	}

	public Kunde getKunde() {
		return kunde;
	}
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
	public Date getRentedOn() {
		return rentedOn;
	}
	public void setRentedOn(Date rentedOn) {
		this.rentedOn = rentedOn;
	}
	public Date getRentedTill() {
		return rentedTill;
	}
	public void setRentedTill(Date rentedTill) {
		this.rentedTill = rentedTill;
	}
	public double getBetragzuZahlen() {
		return betragzuZahlen;
	}
	public void setBetragzuZahlen(double betragzuZahlen) {
		this.betragzuZahlen = betragzuZahlen;
	}
	
	public String getKassaZettelNummer() {
		return kassaZettelNummer;
	}
	public void setKassaZettelNummer(String kassaZettelNummer) {
		this.kassaZettelNummer = kassaZettelNummer;
	}
	
	private Kunde kunde;
	private Date rentedOn;
	private Date rentedTill;
	private double betragzuZahlen;
	private String kassaZettelNummer;
	private Boolean abgeschlossen;
	
	private List <Rentable> rentables = new ArrayList <Rentable> ();
	
	@Override
	public String toString(){
		return "Rent: kassaZettelNummer = " + kassaZettelNummer + "; kunde = " + kunde.getVorname() + "; rentedOn: " + rentedOn + "; rentedTill: " + rentedTill + "rentables: " + rentables;
	}
	
	public Boolean getAbgeschlossen() {
		return abgeschlossen;
	}

	public void setAbgeschlossen(Boolean abgeschlossen) {
		this.abgeschlossen = abgeschlossen;
	}

}
