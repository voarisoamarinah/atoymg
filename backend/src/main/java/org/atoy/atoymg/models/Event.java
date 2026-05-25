package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="events")
public class Event  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="idorganizer")

	private Organizer idorganizerOrganizers;

	@ManyToOne
	@JoinColumn(name="idclient")

	private Client idclientClients;

	@ManyToOne
	@JoinColumn(name="ideventtype")

	private Eventtype ideventtypeEventtypes;

	@ManyToOne
	@JoinColumn(name="ideventstatus")

	private Eventstatuse ideventstatusEventstatuses;

	@Column(name="title", unique = false, nullable = false)
	@NotNull(message = "Title cannot be null")
	@Size(max = 255)
	private String title;

	@Column(name="eventdate", unique = false, nullable = false)
	@NotNull(message = "Eventdate cannot be null")
	private java.time.LocalDate eventdate;

	@Column(name="enddate", unique = false, nullable = true)
	private java.time.LocalDate enddate;

	@Column(name="venuecity", unique = false, nullable = true)
	@Size(max = 100)
	private String venuecity;

	@Column(name="guestcount", unique = false, nullable = true)
	@DecimalMin(value = "0")
	private Integer guestcount;

	@Column(name="totalbudget", unique = false, nullable = true)
	@DecimalMin(value = "0", inclusive = true)
	private Double totalbudget;

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
