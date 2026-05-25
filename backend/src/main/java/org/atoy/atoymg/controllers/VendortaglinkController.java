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
import org.atoy.atoymg.models.Vendortaglink;
import org.atoy.atoymg.models.dto.VendortaglinkSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.VendortaglinkService;
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
@RequestMapping("/vendortaglinks")
@Tag(name = "Vendortaglink", description = "Vendortaglink Management APIs")
public class VendortaglinkController  {
	private final VendortaglinkService vendortaglinkService;

	public VendortaglinkController(VendortaglinkService vendortaglinkService) {
	   this.vendortaglinkService = vendortaglinkService;
	}

	@Operation(
	    summary = "Retrieve all vendortaglink",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendortaglink items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendortaglink",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendortaglink found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Vendortaglink>>> getAllVendortaglinks(
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
	    Page<Vendortaglink> vendortaglinks = vendortaglinkService.getAllVendortaglink(pageable);
	
	    String message = "vendortaglink retrieved successfully";
	
	    if(!vendortaglinks.hasContent()) {
	            message = "No vendortaglink found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendortaglink>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendortaglinks
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all vendortaglink",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendortaglink items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendortaglink",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendortaglink found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Vendortaglink>>> getAllVendortaglinks(
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
	    @RequestBody VendortaglinkSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Vendortaglink> vendortaglinks = vendortaglinkService.getAllVendortaglink(pageable, object);
	
	    String message = "vendortaglink retrieved successfully";
	
	    if(!vendortaglinks.hasContent()) {
	            message = "No vendortaglink found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendortaglink>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendortaglinks
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get vendortaglink by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific vendortaglink item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the vendortaglink",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendortaglink not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Vendortaglink>> getVendortaglinkById(
	    @Parameter(description = "ID of the vendortaglink to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Vendortaglink vendortaglink = vendortaglinkService.getVendortaglinkById(id);
	    RestResponse<Vendortaglink> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendortaglink retrieved successfully", vendortaglink);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new vendortaglink",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new vendortaglink item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Vendortaglink created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendortaglink supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createVendortaglink(
	    @Parameter(description = "Vendortaglink object to be created", required = true)
	    @RequestBody @Valid Vendortaglink vendortaglink
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
	    Vendortaglink newVendortaglink = vendortaglinkService.createVendortaglink(vendortaglink);
	    RestResponse<Vendortaglink> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "vendortaglink created successfully", newVendortaglink);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing vendortaglink",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing vendortaglink item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendortaglink updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendortaglink not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendortaglink supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVendortaglink(
	    @Parameter(description = "ID of the vendortaglink to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated vendortaglink object", required = true)
	    @RequestBody @Valid Vendortaglink vendortaglink
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
	    Vendortaglink updateVendortaglink = vendortaglinkService.updateVendortaglink(id, vendortaglink);
	    RestResponse<Vendortaglink> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendortaglink updated successfully", updateVendortaglink);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete vendortaglink",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a vendortaglink item by its ID. Returns success even if vendortaglink was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendortaglink deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteVendortaglinkById(
	    @Parameter(description = "ID of the vendortaglink to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			vendortaglinkService.getVendortaglinkById(id);
			vendortaglinkService.deleteVendortaglink(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendortaglink deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendortaglink already deleted or does not exist",
					null
				)
			);
		}
	}


}
