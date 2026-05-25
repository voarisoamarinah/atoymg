package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="eventvendors")
public class Eventvendor  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="idevent")

	private Event ideventEvents;

	@ManyToOne
	@JoinColumn(name="idvendor")

	private Vendor idvendorVendors;

	@ManyToOne
	@JoinColumn(name="ideventvendorstatus")

	private Eventvendorstatuse ideventvendorstatusEventvendorstatuses;

	@Column(name="agreedprice", unique = false, nullable = true)
	@DecimalMin(value = "0", inclusive = true)
	private Double agreedprice;

	@Column(name="notes", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String notes;

	@Column(name="createdat", unique = false, nullable = false)
	@NotNull(message = "Createdat cannot be null")
	private java.time.OffsetDateTime createdat;

	@Column(name="updatedat", unique = false, nullable = false)
	@NotNull(message = "Updatedat cannot be null")
	private java.time.OffsetDateTime updatedat;
	

}
