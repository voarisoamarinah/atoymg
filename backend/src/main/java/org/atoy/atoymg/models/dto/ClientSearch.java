package org.atoy.atoymg.models.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ClientSearch {

	private Long id;

	private String fullname;
	private String email;
	private String phone;
	private String notes;
	private java.time.OffsetDateTime createdat;
	private java.time.OffsetDateTime createdatMin;
	private java.time.OffsetDateTime createdatMax;

	
}
