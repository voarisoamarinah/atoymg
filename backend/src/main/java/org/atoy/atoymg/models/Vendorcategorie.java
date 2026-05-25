package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="vendorcategories")
public class Vendorcategorie  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@Column(name="code", unique = true, nullable = false)
	@NotNull(message = "Code cannot be null")
	@Size(max = 50)
	private String code;

	@Column(name="label", unique = false, nullable = false)
	@NotNull(message = "Label cannot be null")
	@Size(max = 100)
	private String label;
	

}
