package at.bibliothek.model;

public class Buch implements Rentable{

	String titel;
	String author;
	String isbn;
	
	public Buch (String titel, String author, String isbn){
		this.titel = titel;
		this.author = author;
		this.isbn = isbn;
	}

	//@Override
	public boolean isRented() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString(){
		return "BUCH-> Titel: " + this.titel + "; isbn: " + this.isbn;
	}

	//@Override
	public String getTitle() {
		return titel;
	}
}
