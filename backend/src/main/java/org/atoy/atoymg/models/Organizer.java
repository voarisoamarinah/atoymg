package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="organizers")
public class Organizer  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@Column(name="agencyname", unique = false, nullable = false)
	@NotNull(message = "Agencyname cannot be null")
	@Size(max = 255)
	private String agencyname;

	@Column(name="slug", unique = true, nullable = false)
	@NotNull(message = "Slug cannot be null")
	@Size(max = 255)
	private String slug;

	@Column(name="email", unique = true, nullable = false)
	@NotNull(message = "Email cannot be null")
	@Size(max = 255)
	private String email;

	@Column(name="phone", unique = false, nullable = true)
	@Size(max = 30)
	private String phone;

	@Column(name="city", unique = false, nullable = true)
	@Size(max = 2)
	private String city;

	@Column(name="isactive", unique = false, nullable = false)
	@NotNull(message = "Isactive cannot be null")
	private Boolean isactive;

	@Column(name="createdat", unique = false, nullable = false)
	@NotNull(message = "Createdat cannot be null")
	private java.time.OffsetDateTime createdat;

	@Column(name="updatedat", unique = false, nullable = false)
	@NotNull(message = "Updatedat cannot be null")
	private java.time.OffsetDateTime updatedat;
	

}
