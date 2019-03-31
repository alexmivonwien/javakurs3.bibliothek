package at.bibliothek.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author User
 *
 */

@Entity
@DiscriminatorValue("D")
public class DVD extends Rentable {
	
	@Column
	private String author;
	
	@Column
	private String titel;
	
	@Column
	private String betriebssystem;
	
	public DVD(){}
	
	public DVD(String titel, String betriebssystem){
		this.titel = titel;
		this.betriebssystem = betriebssystem;
	}
	
	@Override
	public boolean isRented() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTitle() {
		return titel;
	}
	
	public String getBetriebssystem() {
		return betriebssystem;
	}

	public void setBetriebssystem(String betriebssystem) {
		this.betriebssystem = betriebssystem;
	}
	
	
	@Override
	public  String getAuthor(){
		return author;
	};
	
	@Override
	public String toString(){
		return "DVD-> Titel: " + titel + ", betriebssystem = " + betriebssystem;
	}

}
