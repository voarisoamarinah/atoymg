package org.atoy.atoymg.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.atoy.atoymg.models.Vendor;

import org.atoy.atoymg.models.Vendortag;



@Getter @Setter
public class VendortaglinkSearch {

	private Long id;

	private Vendor idvendorVendors;
	
	private Vendortag idtagVendortags;
	
	
}
