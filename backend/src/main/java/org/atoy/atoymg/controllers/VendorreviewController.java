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
import org.atoy.atoymg.models.Vendorreview;
import org.atoy.atoymg.models.dto.VendorreviewSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.VendorreviewService;
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
@RequestMapping("/vendorreviews")
@Tag(name = "Vendorreview", description = "Vendorreview Management APIs")
public class VendorreviewController  {
	private final VendorreviewService vendorreviewService;

	public VendorreviewController(VendorreviewService vendorreviewService) {
	   this.vendorreviewService = vendorreviewService;
	}

	@Operation(
	    summary = "Retrieve all vendorreview",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendorreview items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendorreview",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendorreview found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Vendorreview>>> getAllVendorreviews(
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
	    Page<Vendorreview> vendorreviews = vendorreviewService.getAllVendorreview(pageable);
	
	    String message = "vendorreview retrieved successfully";
	
	    if(!vendorreviews.hasContent()) {
	            message = "No vendorreview found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendorreview>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendorreviews
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all vendorreview",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendorreview items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendorreview",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendorreview found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Vendorreview>>> getAllVendorreviews(
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
	    @RequestBody VendorreviewSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Vendorreview> vendorreviews = vendorreviewService.getAllVendorreview(pageable, object);
	
	    String message = "vendorreview retrieved successfully";
	
	    if(!vendorreviews.hasContent()) {
	            message = "No vendorreview found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendorreview>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendorreviews
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get vendorreview by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific vendorreview item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the vendorreview",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendorreview not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Vendorreview>> getVendorreviewById(
	    @Parameter(description = "ID of the vendorreview to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Vendorreview vendorreview = vendorreviewService.getVendorreviewById(id);
	    RestResponse<Vendorreview> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendorreview retrieved successfully", vendorreview);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new vendorreview",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new vendorreview item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Vendorreview created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendorreview supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createVendorreview(
	    @Parameter(description = "Vendorreview object to be created", required = true)
	    @RequestBody @Valid Vendorreview vendorreview
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
	    Vendorreview newVendorreview = vendorreviewService.createVendorreview(vendorreview);
	    RestResponse<Vendorreview> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "vendorreview created successfully", newVendorreview);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing vendorreview",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing vendorreview item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendorreview updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendorreview not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendorreview supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVendorreview(
	    @Parameter(description = "ID of the vendorreview to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated vendorreview object", required = true)
	    @RequestBody @Valid Vendorreview vendorreview
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
	    Vendorreview updateVendorreview = vendorreviewService.updateVendorreview(id, vendorreview);
	    RestResponse<Vendorreview> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendorreview updated successfully", updateVendorreview);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete vendorreview",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a vendorreview item by its ID. Returns success even if vendorreview was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendorreview deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteVendorreviewById(
	    @Parameter(description = "ID of the vendorreview to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			vendorreviewService.getVendorreviewById(id);
			vendorreviewService.deleteVendorreview(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendorreview deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendorreview already deleted or does not exist",
					null
				)
			);
		}
	}


}
