package at.bibliothek.services.database;

/**
 * Warum ist eine Service-Schicht notwendig, wenn man bereits die DAO-Schicht hat?
 * 
 * Während DAO-Klassen vor allem "elementaren" Datenbank-Operationen  ( CRUD ) abbilden, dient die Service-Schicht 
 * dazu, komplexere, zusammengesetzte Operationen (Geschäftsprozesse) darzustellen.
 * D.h.: Sie Service-Schicht benutzt DAO Schicht, um Geschäftsprozesse abzubilden, während die DAO-Schicht
 * keine Ahnung von der Service-Schicht hat.
 * 
 * SQL-Exceptions, die in einer DAO-Klasse auftretten, werden oft in der Service-Schicht behandelt.
 * Das ist so, weil nur die Service-Schicht weißt, ob diese Exception für die konrkrete Geschäftsprozess von
 * Bedeutung ist oder nicht, und die Transaktion (sieh unten) abbrechen oder nicht.
 * 
 * Eine Methode in einer Service-Klasse besteht aus "elementaren" Datenbank-Operationen (CRUD), 
 * Prüfungen und eventuell Umwandlungen. Oft müssen alle diese "elementaren" Datenbank-Operationen (CRUD) 
 * entweder alle erfolgreich, oder keine davon ausgeführt werden. In diesem Fall spricht man von Transaktionen.
 * D.h: In einer Datenbank-Transaktion kann es nicht sein, dass nur die Hälfte der Operationen erfolgreich sind.
 * Entweder ALLE oder gar KEINE.
 * 
 * Zum Beispiel die Methode 
 * 
 * RentDBService.transferRentablesFromOneCustomerToOther() 
 * 
 * kann als eine Transaktion betrachtet werden. 
 * 
 */


