package at.bibliothek.web;

public enum KundeTyp {

	TYP_NATPERS("Nat. Person"), TYP_FIRMA("Firma");

	private String value;

	private KundeTyp(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}