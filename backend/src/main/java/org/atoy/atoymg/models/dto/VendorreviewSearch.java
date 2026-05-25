package org.atoy.atoymg.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.atoy.atoymg.models.Vendor;

import org.atoy.atoymg.models.Event;

import org.atoy.atoymg.models.Organizer;

import org.atoy.atoymg.models.Client;



@Getter @Setter
public class VendorreviewSearch {

	private Long id;

	private Vendor idvendorVendors;
	
	private Event ideventEvents;
	
	private Organizer idorganizerOrganizers;
	
	private Client idclientClients;
	
	private Integer score;
	private Integer scoreMin;
	private Integer scoreMax;

	private String comment;
	private java.time.OffsetDateTime createdat;
	private java.time.OffsetDateTime createdatMin;
	private java.time.OffsetDateTime createdatMax;

	
}
