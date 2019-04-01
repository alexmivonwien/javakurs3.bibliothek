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
@DiscriminatorValue("B")
public class Buch extends Rentable {

	@Column
	private String titel;
	
	@Column
	private String author;
	
	@Column
	private String isbn;
	
	public Buch(){}
	
	public Buch (String titel, String author, String isbn){
		this.titel = titel;
		this.author = author;
		this.isbn = isbn;
	}

	@Override
	public boolean isRented() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString(){
		return "BUCH-> Titel: " + this.titel + "; isbn: " + this.isbn;
	}

	@Override
	public String getTitle() {
		return titel;
	}
}
