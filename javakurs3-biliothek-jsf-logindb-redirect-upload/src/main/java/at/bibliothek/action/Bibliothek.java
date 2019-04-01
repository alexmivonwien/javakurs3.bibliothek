package at.bibliothek.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import at.bibliothek.model.NatuerlichePerson;
import at.bibliothek.model.Rent;
import at.bibliothek.services.database.KundeDBService;
import at.bibliothek.services.database.RentDBService;

/**
 * Das ist das "Hauptprogramm", oder der "Einstiegspunkt" unserer Anwendung.
 * In der Methode main() speilt sich alles.
 */
public class Bibliothek {
	
	
	public Bibliothek(int sizeOfArray) throws Exception {
	}
	
	public static String readline() throws Exception {
		
		BufferedReader buf = new BufferedReader (new InputStreamReader (System.in));
		String inputFromUser = buf.readLine();
		
		return inputFromUser;
	
	}
	
	public static void main(String[] args) throws Exception{
		
		KundeDBService kundeService = new KundeDBService();
		List kundenSumbenge = kundeService.findAllKunden("Pet");
		
		List<NatuerlichePerson> kunden = kundeService.findAllNatuerlichePersonen("Stefan", "Schmidt");
		System.out.println(kunden);
		
		RentDBService rentDBService = new RentDBService();
		
		List<Rent> rents = rentDBService.findBy(kunden.get(0), null);
		
		System.out.println(rents);
		

	}
}
