package org.atoy.atoymg.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.atoy.atoymg.models.Organizer;

import org.atoy.atoymg.models.Client;

import org.atoy.atoymg.models.Eventtype;

import org.atoy.atoymg.models.Eventstatuse;



@Getter @Setter
public class EventSearch {

	private Long id;

	private Organizer idorganizerOrganizers;
	
	private Client idclientClients;
	
	private Eventtype ideventtypeEventtypes;
	
	private Eventstatuse ideventstatusEventstatuses;
	
	private String title;
	private java.time.LocalDate eventdate;
	private java.time.LocalDate eventdateMin;
	private java.time.LocalDate eventdateMax;

	private java.time.LocalDate enddate;
	private java.time.LocalDate enddateMin;
	private java.time.LocalDate enddateMax;

	private String venuecity;
	private Integer guestcount;
	private Integer guestcountMin;
	private Integer guestcountMax;

	private Double totalbudget;
	private Double totalbudgetMin;
	private Double totalbudgetMax;

	private String notes;
	private java.time.OffsetDateTime createdat;
	private java.time.OffsetDateTime createdatMin;
	private java.time.OffsetDateTime createdatMax;

	private java.time.OffsetDateTime updatedat;
	private java.time.OffsetDateTime updatedatMin;
	private java.time.OffsetDateTime updatedatMax;

	
}
