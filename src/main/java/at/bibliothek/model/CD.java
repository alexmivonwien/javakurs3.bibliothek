package at.bibliothek.model;

public class CD implements Rentable {

	private String titel;
	
	public CD (String titel){
		this.titel = titel;
	}
	
	//@Override
	public boolean isRented() {
		// TODO Auto-generated method stub
		return false;
	}

	//@Override
	public String getTitle() {
		return titel;
	}

	@Override
	public String toString(){
		return "CD-> Titel: " + titel ;
	}
}
