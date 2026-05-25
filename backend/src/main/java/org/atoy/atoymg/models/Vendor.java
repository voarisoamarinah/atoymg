package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="vendors")
public class Vendor  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="idvendorcategory")

	private Vendorcategorie idvendorcategoryVendorcategories;

	@Column(name="businessname", unique = false, nullable = false)
	@NotNull(message = "Businessname cannot be null")
	@Size(max = 255)
	private String businessname;

	@Column(name="contactemail", unique = false, nullable = true)
	@Size(max = 255)
	private String contactemail;

	@Column(name="contactphone", unique = false, nullable = true)
	@Size(max = 30)
	private String contactphone;

	@Column(name="website", unique = false, nullable = true)
	@Size(max = 500)
	private String website;

	@Column(name="city", unique = false, nullable = true)
	@Size(max = 100)
	private String city;

	@Column(name="countrycode", unique = false, nullable = true)
	@Size(max = 2)
	private String countrycode;

	@Column(name="baseprice", unique = false, nullable = true)
	private Double baseprice;

	@Column(name="rating", unique = false, nullable = true)
	@DecimalMin(value = "1", inclusive = true)
	@DecimalMax(value = "5", inclusive = true)
	private Double rating;

	@Column(name="ratingcount", unique = false, nullable = false)
	@NotNull(message = "Ratingcount cannot be null")
	private Integer ratingcount;

	@Column(name="description", unique = false, nullable = true)
	@Size(max = 2147483647)
	private String description;

	@Column(name="isverified", unique = false, nullable = false)
	@NotNull(message = "Isverified cannot be null")
	private Boolean isverified;

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
