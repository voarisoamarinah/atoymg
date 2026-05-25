package org.atoy.atoymg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="vendortaglinks")
public class Vendortaglink  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="idvendor")

	private Vendor idvendorVendors;

	@ManyToOne
	@JoinColumn(name="idtag")

	private Vendortag idtagVendortags;
	

}
