package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="clients")
public class Client  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@Column(name="fullname", unique = false, nullable = false)
	@NotNull(message = "Fullname cannot be null")
	@Size(max = 255)
	private String fullname;

	@Column(name="email", unique = false, nullable = true)
	@Size(max = 255)
	private String email;

	@Column(name="phone", unique = false, nullable = true)
	@Size(max = 30)
	private String phone;

	@Column(name="notes", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String notes;

	@Column(name="createdat", unique = false, nullable = false)
	@NotNull(message = "Createdat cannot be null")
	private java.time.OffsetDateTime createdat;
	

}
