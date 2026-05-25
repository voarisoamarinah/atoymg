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
import org.atoy.atoymg.models.Vendorcategorie;
import org.atoy.atoymg.models.dto.VendorcategorieSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.VendorcategorieService;
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
@RequestMapping("/vendorcategories")
@Tag(name = "Vendorcategorie", description = "Vendorcategorie Management APIs")
public class VendorcategorieController  {
	private final VendorcategorieService vendorcategorieService;

	public VendorcategorieController(VendorcategorieService vendorcategorieService) {
	   this.vendorcategorieService = vendorcategorieService;
	}

	@Operation(
	    summary = "Retrieve all vendorcategorie",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendorcategorie items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendorcategorie",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendorcategorie found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Vendorcategorie>>> getAllVendorcategories(
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
	    Page<Vendorcategorie> vendorcategories = vendorcategorieService.getAllVendorcategorie(pageable);
	
	    String message = "vendorcategorie retrieved successfully";
	
	    if(!vendorcategories.hasContent()) {
	            message = "No vendorcategorie found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendorcategorie>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendorcategories
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all vendorcategorie",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of vendorcategorie items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of vendorcategorie",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No vendorcategorie found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Vendorcategorie>>> getAllVendorcategories(
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
	    @RequestBody VendorcategorieSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Vendorcategorie> vendorcategories = vendorcategorieService.getAllVendorcategorie(pageable, object);
	
	    String message = "vendorcategorie retrieved successfully";
	
	    if(!vendorcategories.hasContent()) {
	            message = "No vendorcategorie found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Vendorcategorie>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vendorcategories
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get vendorcategorie by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific vendorcategorie item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the vendorcategorie",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendorcategorie not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Vendorcategorie>> getVendorcategorieById(
	    @Parameter(description = "ID of the vendorcategorie to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Vendorcategorie vendorcategorie = vendorcategorieService.getVendorcategorieById(id);
	    RestResponse<Vendorcategorie> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendorcategorie retrieved successfully", vendorcategorie);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new vendorcategorie",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new vendorcategorie item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Vendorcategorie created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendorcategorie supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createVendorcategorie(
	    @Parameter(description = "Vendorcategorie object to be created", required = true)
	    @RequestBody @Valid Vendorcategorie vendorcategorie
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
	    Vendorcategorie newVendorcategorie = vendorcategorieService.createVendorcategorie(vendorcategorie);
	    RestResponse<Vendorcategorie> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "vendorcategorie created successfully", newVendorcategorie);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing vendorcategorie",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing vendorcategorie item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendorcategorie updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Vendorcategorie not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid vendorcategorie supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVendorcategorie(
	    @Parameter(description = "ID of the vendorcategorie to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated vendorcategorie object", required = true)
	    @RequestBody @Valid Vendorcategorie vendorcategorie
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
	    Vendorcategorie updateVendorcategorie = vendorcategorieService.updateVendorcategorie(id, vendorcategorie);
	    RestResponse<Vendorcategorie> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "vendorcategorie updated successfully", updateVendorcategorie);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete vendorcategorie",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a vendorcategorie item by its ID. Returns success even if vendorcategorie was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Vendorcategorie deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteVendorcategorieById(
	    @Parameter(description = "ID of the vendorcategorie to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			vendorcategorieService.getVendorcategorieById(id);
			vendorcategorieService.deleteVendorcategorie(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendorcategorie deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"vendorcategorie already deleted or does not exist",
					null
				)
			);
		}
	}


}
