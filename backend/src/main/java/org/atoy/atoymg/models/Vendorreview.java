package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="vendorreviews")
public class Vendorreview  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="idvendor")

	private Vendor idvendorVendors;

	@ManyToOne
	@JoinColumn(name="idevent")

	private Event ideventEvents;

	@ManyToOne
	@JoinColumn(name="idorganizer")

	private Organizer idorganizerOrganizers;

	@ManyToOne
	@JoinColumn(name="idclient")

	private Client idclientClients;

	@Column(name="score", unique = false, nullable = false)
	@NotNull(message = "Score cannot be null")
	@DecimalMin(value = "1", inclusive = true)
	@DecimalMax(value = "5", inclusive = true)
	private Integer score;

	@Column(name="comment", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String comment;

	@Column(name="createdat", unique = false, nullable = false)
	@NotNull(message = "Createdat cannot be null")
	private java.time.OffsetDateTime createdat;
	

}
