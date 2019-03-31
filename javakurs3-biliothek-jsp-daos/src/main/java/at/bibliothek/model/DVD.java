package at.bibliothek.model;

public class DVD implements Rentable {
	
	private String titel;
	private String betriebssystem;
	
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
	public String toString(){
		return "DVD-> Titel: " + titel + ", betriebssystem = " + betriebssystem;
	}

}
