package at.bibliothek.model;

import javax.persistence.Column;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * um JPA richitg zu benutzen, müssen wir leider das Interface Rentable in einer
 * Abstrakter Klasse umwandeln:
 * 
 * @see 
 * https://www.thoughts-on-java.org/complete-guide-inheritance-strategies-jpa-hibernate/
 * 
 */

@Entity
@Table(name = "rentable")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rentable_selector")

public abstract class Rentable { // Ausleihbar

	
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rentable_seq")
	// @SequenceGenerator(name = "rentable_seq", sequenceName = "rentable_seq")
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	public abstract boolean isRented();

	public abstract String getTitle();

}
