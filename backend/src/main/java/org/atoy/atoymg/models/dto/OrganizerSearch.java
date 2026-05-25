package org.atoy.atoymg.models.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class OrganizerSearch {

	private Long id;

	private String agencyname;
	private String slug;
	private String email;
	private String phone;
	private String city;
	private Boolean isactive;
	private java.time.OffsetDateTime createdat;
	private java.time.OffsetDateTime createdatMin;
	private java.time.OffsetDateTime createdatMax;

	private java.time.OffsetDateTime updatedat;
	private java.time.OffsetDateTime updatedatMin;
	private java.time.OffsetDateTime updatedatMax;

	
}
