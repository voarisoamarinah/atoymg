package org.atoy.atoymg.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.atoy.atoymg.models.Event;

import org.atoy.atoymg.models.Vendor;

import org.atoy.atoymg.models.Eventvendorstatuse;



@Getter @Setter
public class EventvendorSearch {

	private Long id;

	private Event ideventEvents;
	
	private Vendor idvendorVendors;
	
	private Eventvendorstatuse ideventvendorstatusEventvendorstatuses;
	
	private Double agreedprice;
	private Double agreedpriceMin;
	private Double agreedpriceMax;

	private String notes;
	private java.time.OffsetDateTime createdat;
	private java.time.OffsetDateTime createdatMin;
	private java.time.OffsetDateTime createdatMax;

	private java.time.OffsetDateTime updatedat;
	private java.time.OffsetDateTime updatedatMin;
	private java.time.OffsetDateTime updatedatMax;

	
}
