package at.bibliothek.model;

public abstract class Kunde {

	private int id;

	protected String vorname;
	protected String adresse;
	
	
	public abstract boolean isRechtsFaehig();
	
	public Kunde (){}
	
	public Kunde (String vorname){
		this.vorname = vorname;
	}

	public Kunde (String vorname, String adres){
		this.vorname = vorname;
		this.adresse = adres;
	}

	/** 
	 * Das Feld ID is generiert in der Datenbank.
	 * Deswegen, wenn ID > 0, dann ist dasKunde-Objekt 
	 * (als eine Zeile in der Tabelle kunde)
	 * in der Datenbank bereits vorhanden.
	 * 
	 * @return ID vom Kunde
	 */
	public int getId() {
		return id;
	}

	/** 
	 * Das Feld ID is generiert in der Datenbank.
	 * Deswegen, wenn ID > 0, dann ist dasKunde-Objekt 
	 * (als eine Zeile in der Tabelle kunde)
	 * in der Datenbank bereits vorhanden.
	 * 
	 * @return ID vom Kunde
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	public String getVorname() {
		
//		if ( Context.getUser().isAdministrator())
//			return vorname;
//		else
//				vorname.equals("Donald Trump")){
//			return "Modald Wund";
//		}
		return vorname;
	}
	
	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	
}
