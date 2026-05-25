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
import org.atoy.atoymg.models.Vendortag;
import org.atoy.atoymg.models.dto.VendortagSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.VendortagService;
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
@RequestMapping("/vendortags")
@Tag(name = "Vendortag", description = "Vendortag Management APIs")
public class VendortagController  {
	private final VendortagService vendortagService;

	public VendortagController(VendortagService vendortagService) {
	   this.vendortagService = vendortagService;
	}

	@Operation(
	    summary = "Retrieve all vendortag",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendortag items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendortag",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendortag found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Vendortag>>> getAllVendortags(
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
	    Page<Vendortag> vendortags = vendortagService.getAllVendortag(pageable);
	
	    String message = "vendortag retrieved successfully";
	
	    if(!vendortags.hasContent()) {
	            message = "No vendortag found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendortag>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendortags
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all vendortag",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendortag items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendortag",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendortag found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Vendortag>>> getAllVendortags(
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
	    @RequestBody VendortagSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Vendortag> vendortags = vendortagService.getAllVendortag(pageable, object);
	
	    String message = "vendortag retrieved successfully";
	
	    if(!vendortags.hasContent()) {
	            message = "No vendortag found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendortag>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendortags
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get vendortag by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific vendortag item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the vendortag",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendortag not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Vendortag>> getVendortagById(
	    @Parameter(description = "ID of the vendortag to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Vendortag vendortag = vendortagService.getVendortagById(id);
	    RestResponse<Vendortag> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendortag retrieved successfully", vendortag);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new vendortag",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new vendortag item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Vendortag created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendortag supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createVendortag(
	    @Parameter(description = "Vendortag object to be created", required = true)
	    @RequestBody @Valid Vendortag vendortag
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
	    Vendortag newVendortag = vendortagService.createVendortag(vendortag);
	    RestResponse<Vendortag> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "vendortag created successfully", newVendortag);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing vendortag",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing vendortag item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendortag updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendortag not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendortag supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVendortag(
	    @Parameter(description = "ID of the vendortag to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated vendortag object", required = true)
	    @RequestBody @Valid Vendortag vendortag
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
	    Vendortag updateVendortag = vendortagService.updateVendortag(id, vendortag);
	    RestResponse<Vendortag> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendortag updated successfully", updateVendortag);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete vendortag",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a vendortag item by its ID. Returns success even if vendortag was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendortag deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteVendortagById(
	    @Parameter(description = "ID of the vendortag to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			vendortagService.getVendortagById(id);
			vendortagService.deleteVendortag(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendortag deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendortag already deleted or does not exist",
					null
				)
			);
		}
	}


}
