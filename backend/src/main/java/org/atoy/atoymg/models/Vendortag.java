package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="vendortags")
public class Vendortag  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="idvendorcategory")

	private Vendorcategorie idvendorcategoryVendorcategories;

	@Column(name="label", unique = true, nullable = false)
	@NotNull(message = "Label cannot be null")
	@Size(max = 100)
	private String label;
	

}
