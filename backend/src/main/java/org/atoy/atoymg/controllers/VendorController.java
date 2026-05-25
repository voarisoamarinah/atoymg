package org.atoy.atoymg.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.atoy.atoymg.models.Vendor;
import org.atoy.atoymg.models.dto.VendorSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.VendorService;
import org.atoy.atoymg.utils.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;

import org.atoy.atoymg.dto.RestResponse;
import org.atoy.atoymg.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/vendors")
@Tag(name = "Vendor", description = "Vendor Management APIs")
public class VendorController  {
	private final VendorService vendorService;

	public VendorController(VendorService vendorService) {
	   this.vendorService = vendorService;
	}

	@Operation(
	    summary = "Retrieve all vendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendor items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendor",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendor found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Vendor>>> getAllVendors(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: id,asc;name,desc",
	        example = "id,asc"
	    )
	    @RequestParam(defaultValue = "id,asc") String sortParam) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Vendor> vendors = vendorService.getAllVendor(pageable);
	
	    String message = "vendor retrieved successfully";
	
	    if(!vendors.hasContent()) {
	            message = "No vendor found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendor>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendors
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all vendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendor items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendor",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendor found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Vendor>>> getAllVendors(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: id,asc;name,desc",
	        example = "id,asc"
	    )
	    @RequestParam(defaultValue = "id,asc") String sortParam,
	    @RequestBody VendorSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Vendor> vendors = vendorService.getAllVendor(pageable, object);
	
	    String message = "vendor retrieved successfully";
	
	    if(!vendors.hasContent()) {
	            message = "No vendor found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendor>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendors
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get vendor by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific vendor item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the vendor",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendor not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Vendor>> getVendorById(
	    @Parameter(description = "ID of the vendor to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Vendor vendor = vendorService.getVendorById(id);
	    RestResponse<Vendor> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendor retrieved successfully", vendor);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new vendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new vendor item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Vendor created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendor supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createVendor(
	    @Parameter(description = "Vendor object to be created", required = true)
	    @RequestBody @Valid Vendor vendor
	    ,BindingResult bindingResult
	) {
	    if (bindingResult.hasErrors()) {
	       HashMap<String, String> errors = new HashMap<>();
	       bindingResult.getFieldErrors().forEach(error -> {
	           errors.put(error.getField(), error.getDefaultMessage());
	       });
	       RestResponse<HashMap<String, String>> errorResponse =
	       RestResponse.buildErrorResponse(HttpStatus.BAD_REQUEST,
	       "Validation failed", errors);
	
	       return ResponseEntity.badRequest().body(errorResponse);
	    }
	    Vendor newVendor = vendorService.createVendor(vendor);
	    RestResponse<Vendor> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "vendor created successfully", newVendor);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing vendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing vendor item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendor updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendor not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendor supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVendor(
	    @Parameter(description = "ID of the vendor to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated vendor object", required = true)
	    @RequestBody @Valid Vendor vendor
	    ,BindingResult bindingResult
	) {
	    if (bindingResult.hasErrors()) {
	       HashMap<String, String> errors = new HashMap<>();
	       bindingResult.getFieldErrors().forEach(error -> {
	           errors.put(error.getField(), error.getDefaultMessage());
	       });
	       RestResponse<HashMap<String, String>> errorResponse =
	       RestResponse.buildErrorResponse(HttpStatus.BAD_REQUEST,
	       "Validation failed", errors);
	
	       return ResponseEntity.badRequest().body(errorResponse);
	    }
	    Vendor updateVendor = vendorService.updateVendor(id, vendor);
	    RestResponse<Vendor> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendor updated successfully", updateVendor);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete vendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a vendor item by its ID. Returns success even if vendor was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendor deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteVendorById(
	    @Parameter(description = "ID of the vendor to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			vendorService.getVendorById(id);
			vendorService.deleteVendor(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendor deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendor already deleted or does not exist",
					null
				)
			);
		}
	}


}
