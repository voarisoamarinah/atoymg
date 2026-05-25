package org.atoy.atoymg.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.atoy.atoymg.models.Vendorcategorie;



@Getter @Setter
public class VendorSearch {

	private Long id;

	private Vendorcategorie idvendorcategoryVendorcategories;
	
	private String businessname;
	private String contactemail;
	private String contactphone;
	private String website;
	private String city;
	private String countrycode;
	private Double baseprice;
	private Double basepriceMin;
	private Double basepriceMax;

	private Double rating;
	private Double ratingMin;
	private Double ratingMax;

	private Integer ratingcount;
	private Integer ratingcountMin;
	private Integer ratingcountMax;

	private String description;
	private Boolean isverified;
	private Boolean isactive;
	private java.time.OffsetDateTime createdat;
	private java.time.OffsetDateTime createdatMin;
	private java.time.OffsetDateTime createdatMax;

	private java.time.OffsetDateTime updatedat;
	private java.time.OffsetDateTime updatedatMin;
	private java.time.OffsetDateTime updatedatMax;

	
}
