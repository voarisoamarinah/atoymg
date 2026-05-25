package org.atoy.atoymg.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.atoy.atoymg.models.Vendorcategorie;



@Getter @Setter
public class VendortagSearch {

	private Long id;

	private Vendorcategorie idvendorcategoryVendorcategories;
	
	private String label;
	
}
