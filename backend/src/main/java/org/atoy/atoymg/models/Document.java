package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="documents")
public class Document  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="ideventvendor")

	private Eventvendor ideventvendorEventvendors;

	@Column(name="documenttype", unique = false, nullable = false)
	@NotNull(message = "Documenttype cannot be null")
	@Size(max = 50)
	private String documenttype;

	@Column(name="fileurl", unique = false, nullable = false)
	@NotNull(message = "Fileurl cannot be null")
	@Size(max = 1000)
	private String fileurl;

	@Column(name="createdat", unique = false, nullable = false)
	@NotNull(message = "Createdat cannot be null")
	private java.time.OffsetDateTime createdat;
	

}
