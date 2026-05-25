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
import org.atoy.atoymg.models.Organizer;
import org.atoy.atoymg.models.dto.OrganizerSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.OrganizerService;
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
@RequestMapping("/organizers")
@Tag(name = "Organizer", description = "Organizer Management APIs")
public class OrganizerController  {
	private final OrganizerService organizerService;

	public OrganizerController(OrganizerService organizerService) {
	   this.organizerService = organizerService;
	}

	@Operation(
	    summary = "Retrieve all organizer",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of organizer items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of organizer",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No organizer found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Organizer>>> getAllOrganizers(
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
	    Page<Organizer> organizers = organizerService.getAllOrganizer(pageable);
	
	    String message = "organizer retrieved successfully";
	
	    if(!organizers.hasContent()) {
	            message = "No organizer found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Organizer>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            organizers
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all organizer",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of organizer items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of organizer",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No organizer found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Organizer>>> getAllOrganizers(
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
	    @RequestBody OrganizerSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Organizer> organizers = organizerService.getAllOrganizer(pageable, object);
	
	    String message = "organizer retrieved successfully";
	
	    if(!organizers.hasContent()) {
	            message = "No organizer found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Organizer>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            organizers
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get organizer by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific organizer item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the organizer",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Organizer not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Organizer>> getOrganizerById(
	    @Parameter(description = "ID of the organizer to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Organizer organizer = organizerService.getOrganizerById(id);
	    RestResponse<Organizer> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "organizer retrieved successfully", organizer);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new organizer",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new organizer item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Organizer created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid organizer supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createOrganizer(
	    @Parameter(description = "Organizer object to be created", required = true)
	    @RequestBody @Valid Organizer organizer
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
	    Organizer newOrganizer = organizerService.createOrganizer(organizer);
	    RestResponse<Organizer> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "organizer created successfully", newOrganizer);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing organizer",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing organizer item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Organizer updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Organizer not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid organizer supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateOrganizer(
	    @Parameter(description = "ID of the organizer to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated organizer object", required = true)
	    @RequestBody @Valid Organizer organizer
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
	    Organizer updateOrganizer = organizerService.updateOrganizer(id, organizer);
	    RestResponse<Organizer> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "organizer updated successfully", updateOrganizer);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete organizer",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a organizer item by its ID. Returns success even if organizer was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Organizer deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteOrganizerById(
	    @Parameter(description = "ID of the organizer to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			organizerService.getOrganizerById(id);
			organizerService.deleteOrganizer(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"organizer deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"organizer already deleted or does not exist",
					null
				)
			);
		}
	}


}
