/**
 * 
 * 1.) Heute entwickeln wir ein kleines
 * Bestandhaltungssystem f�r eine Universit�ts-
 * bibliothek. Die Bibliothek leiht aus:
 * 
 *  - B�cher (Papier)
 *  - DVDs 
 *  - Blue-Rays
 *  - CDs
 * 
 * Das System besteht aus den folgenden Klassen:
 * 
 * - Bibliothek;
 * - Kunde (Name, Nachname, Adresse);
 * - Buch (Autor, Titel, ISBN);
 * - DVD (Titel, Autor, Jahr, Betriebssystem, L�nge (Min)) 
 * - Blue-Ray (Titel, Autor, Jahr, Betriebssystem, L�nge (Min)) 
 * - CD (Titel, Autor, Jahr, L�nge (Min)) 
 * - Weitere Klassen (Beim Bedarf);
 * 
 * 
 * Das Sytsem gibt die folgende Information an:
 * 
 * - F�r alle Artikel: Suche nach Titel - Ist der Artikel vorhanden oder ist bereits ausgeliehen?
 * - Welcher Kunde hat am meisten Verzug bei der R�ckgabe seines Artikels?
 *
 * u.s.w;
 * 
 * 
 * 2.) Dar�ber hinaus haben wir die beiden Formen der for- Schleife erkl�rt:
 * 
 * 
 *  2.a) Die komplette (die m�chtigere) Form der Schleife,
 *  die aus drei Teilen besteht:
 * 
 * 		for (...Teil.I... ; ..Teil..II.... ; .Teil .III...){
 * 
 * 		}
 * 
 * Im Teil.I werden die Variablen initialisiert, die in der
 * Schleife verwendet werden, wie int i =0. Man kann hier 
 * MEHRERE Variablen vom gleichen Typ initialisieren, wie:
 *   
 *   for (int i = 0, j = 5;........
 *   
 * Teil I wird nur einmal am Anfang des Schleife ausgef�hrt;
 * 
 * Der Teil. II dient zur Pr�fung (boolischer Ausdruck),
 * ob die Schleife noch weiter geht oder nicht.
 * 
 * for ( .....;  i < array.lengt;......)
 * 
 * Teil II wird VOR jeder Iteration ausgef�hrt und bestimmt,
 * ob die Schleife �berhapt weiter l�uft oder nicht
 *
 * Der Teil. III dient zur �nderung der im Teil I bereits definierten
 * Variablen.
 * 
 *  for ( .....; ......; i = i + 1)
 * 
 * Teil III wird am Ende jeder Iteration ausgef�hrt
 * 
 * Alle III Teile sind optional.
 * 
 * 2.b) Die vereinfachte Form der Schleife (benannt enhanced for - loop)
 * 
 * 
 * for ( Zimmer z : zimmerArray)
 * 
 * - diese Form der Schleife verwendet kein Index, sondern eine Referenz-Variable
 * vom GLEICHEN TYP wie der Typ von dem zimmerArray.
 * Die Java Run Time Environment l�uft f�r uns alle Elemente des Arrays durch
 * (In jeder Schritt der Iteration)
 * 
 * Sommit referiert die Variable z bei jeder Iteration das laufende Element vom Array.
 * 
 * Diese Form ist einfacher, verwendet keine Indexes und wird nur mit Arrays und mit �hnlichen
 * Sammlungen verwendet.
 * 
 */
package at.bibliothek.model;