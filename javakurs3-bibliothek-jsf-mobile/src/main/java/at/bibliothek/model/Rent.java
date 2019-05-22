package at.bibliothek.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table (name = "rent")
public class Rent implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@ManyToOne
	private Kunde kunde;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "rent_rentable")
	private List <Rentable> rentables = new ArrayList <Rentable> ();
	
	@Column
	private Date rentedOn;
	
	@Column
	private Date rentedTill;
	
	@Column
	private Double billAmount;
	
	@Column
	private String kassaZettelNummer;
	
	@Column
	private Boolean abgeschlossen;
	
	public long getId() {
		return id;
	}
	
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
	
	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public String getKassaZettelNummer() {
		return kassaZettelNummer;
	}
	public void setKassaZettelNummer(String kassaZettelNummer) {
		this.kassaZettelNummer = kassaZettelNummer;
	}
	
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
