package at.bibliothek.dao;

/**
 * Die DAO-Schicht dient dazu, Modell-Objekte  (Buch, CD, Rent, u s.w.) von unserer Anwendung 
 * in einem externen System (Datenbank) anzulegen, sie von dort auszulesen, sie dort zu aktualisieren, 
 * sowie sie von dort zu löschen. Das sind die sogennanten CRUD elementaren Operationen 
 * (Create, Read, Update, Delete).
 * Somit besitzt die DAO-Schicht das Wissen, wie man Obejkte der eigenen Anwendung in Daten eines 
 * externen Systems und umgekehrt "übersetzt". Das externe System muss NICHT eine Datenbank sein,
 * es kein auch Dateisystem auf einen File-Share Server, oder ein Web-Service sein, u.s.w.
 * 
 * Bei der Plannung der DAO-Schicht ist zu berücksichitgen:
 * 
 * 1.) Wie werden die Objekte der Anwendung vom externen System geholt? Hierbei ist "lazy loading"
 * vs. "eager loading" zu überlegen. 
 * 
 * Beim "lazy loading" werden die Attribute eines Objektes, die an sich Sammlungen von Objekten sind,
 * nicht gleich beim Auslesen des Objektes vom externen System (der Datenbank), sondern eventuell zu 
 * einem späterem Zeitpunkt, und nur auf expliziter Anfrage, geholt ( mitgelesen).
 * Beim "eager loading" werden insbesondere die Attribute eines Objektes, die Sammlungen von Objekten
 * sind, gleich mit dem Auslesen des "Hauptobjektes" auch geholt ( mitgelesen).
 *
 * Als Beispiel in unserer Anwendung können die Rent-Objekte dienen. Jedes Rent-Objekt besitzt die 
 * Eigenschaft Rentables, die eine Sammlung von Rentables ist. Beim "lazy loading" werden diese 
 * Rentables zuerst gar nicht mitgelesen - sieh die Methode RentDAO.findBy(String kundeId).
 * 
 * Beim "eager loading" werden auch alle Rentables, die zu einem Rent gehören, gleich beim Auslesen
 * des Rents mitgelesen (geholt) - sieh die Methode RentDAO.findBy(Kunde kunde, Date date).
 * 
 * Mehr dazu:

 * https://de.wikipedia.org/wiki/Lazy_Loading
 * https://www.it-visions.de/glossar/alle/6217/Eager_Loading.aspx
 * 
 * 
 * 2.) Was für externes System benutzt wird - Datenbank, Host, Web-Service? Hier gibt es ein Paar
 * Möglichkeiten:
 * 
 *  2.a.) Es ist im voraus festgelegt, dass die Anwendung mit einem konkreten, vordefinierten 
 *  externen System für CRUD-Operationen arbeiten wird - zum Beispiel mit MySQL Server, Version 
 *  5.6 oder höher. 
 *  In diesem Fall kann man direkt mit der Modellierung konkreter DAO-Klassen anfangen. Trotzdem 
 *  empfiehlt es sich, die wichtigsten DAO-Einheiten zuerst als DAO-Schnittstellen zu entwerfen. 
 *  Dabei ist es erlaubt, dass die Signatur der Interface-Methoden das JDBC-Interface Connection 
 *  verwenden, weil es vom Anfang an klar ist, dass das externe System eine (konkrete) 
 *  Datenbank sein wird.
 *  
 *  2.b) Es ist im voraus festgelegt, dass die Anwendung mit einer Datenbank als externes System 
 *  für CRUD-Operationen arbeiten wird, aber wir wollen das konkrete Datenbanksystem zu einem späteren
 *  Zeitpunkt auswhählen, oder überhaput erlauben, dass die Anwendung mit mehreren Datenbanken arbeitet.
 *  
 *  In diesem Fall besteht die DAO-Schicht am Anfang zuerst nur aus Interfaces. Die Interface-Namen
 *  können schondarauf hinweisen, dass die implementierenden klassen mit einer Datenbank arbeiten werden. 
 *  Die Signatur der Interface-Methoden kann das JDBC-Interface Connection verwenden und SQLException -s
 *  auswerfen.
 *  
 *  Laut Oracle ist es auch auf Interface-Ebene erlaubt, dass manche Java-Methoden das JDBC RowSet als return
 *  Type haben, wie zum Beispiel:
 *  
 *		public interface CustomerDAO {
 *		  public int insertCustomer(...);
 *		  public boolean deleteCustomer(...);
 *		  public Customer findCustomer(...);
 *		  public boolean updateCustomer(...);
 *		  public RowSet selectCustomersRS(...); // JDBC RowSet als Rückgabetyp
 *		  public Collection selectCustomersTO(...);
 *		}
 *  
 *   Quelle: "Core J2EE Patterns - Data Access Object":
 *   http://www.oracle.com/technetwork/java/dataaccessobject-138824.html
 *  
 *  2.c.) Es ist im voraus nicht klar, was für externes System für CRUD-Operationen benutzt wird, wir wollen 
 *  auf jeden Fall eine Datenbank und eventuell auch ein Web-Service haben.
 *  
 *  In diesem Fall besteht die DAO-Schicht zuerst nur aus Interfaces. Eventuell kommt sogar eine Hierachie
 *  von Java-Interfaces in Frage: Auf die obersten Ebene sind die CRUD Operationen so abstrakt wie möglich
 *  abgebildet, ohne dass Interface-Methoden das JDBC-Interface Connection verwenden. Erst auf die unteren 
 *  Schichten können die Interface-Methoden das JDBC-Interface Connection in ihrer Signatur verwenden.
 *  Die konkreten DAO-Implementierungsklassen können sehr wohl in der Methoden-Signatur das JDBC-Interface
 *  Connection verwenden.
 *   
 */
