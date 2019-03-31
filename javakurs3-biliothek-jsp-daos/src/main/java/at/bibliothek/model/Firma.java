package at.bibliothek.model;

public class Firma extends Kunde {

	private String steuerNummer;
	
	public Firma(){
		super();
	}
	
	public Firma(String name, String adres) {
		super(name, adres);
	}

	public String getSteuerNummer() {
		return steuerNummer;
	}

	public void setSteuerNummer(String steuerNummer) {
		this.steuerNummer = steuerNummer;
	}

	public boolean isRechtsFaehig() {
		return true;
	}

	public String getName() {
		return vorname;
	}

	public void setVorname(String vorname) {
		super.setVorname(vorname);
	}


	public void setName(String name) {
		super.setVorname(name);
	}

	public String toString() {
		return "Firma: " + this.getName();
	}

}
