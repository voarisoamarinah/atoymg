package org.atoy.atoymg.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.atoy.atoymg.models.Eventvendor;



@Getter @Setter
public class DocumentSearch {

	private Long id;

	private Eventvendor ideventvendorEventvendors;
	
	private String documenttype;
	private String fileurl;
	private java.time.OffsetDateTime createdat;
	private java.time.OffsetDateTime createdatMin;
	private java.time.OffsetDateTime createdatMax;

	
}
