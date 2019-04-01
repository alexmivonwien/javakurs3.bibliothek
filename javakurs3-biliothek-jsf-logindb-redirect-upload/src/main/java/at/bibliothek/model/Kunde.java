package at.bibliothek.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "kunde")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kunde_selector")

public abstract class Kunde {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column
	protected String vorname;
	
	@Column
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
