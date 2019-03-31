package at.bibliothek.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 
 */

@Entity
@DiscriminatorValue("C")

public class CD extends Rentable {

	@Column
	private String author;
	
	@Column
	private String titel;
	
	public CD (){}
	
	public CD (String titel){
		this.titel = titel;
	}
	
	@Override
	public  String getAuthor(){
		return author;
	};
	
	@Override
	public boolean isRented() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTitle() {
		return titel;
	}

	@Override
	public String toString(){
		return "CD-> Titel: " + titel ;
	}
}
