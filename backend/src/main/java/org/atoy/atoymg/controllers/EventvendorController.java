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
import org.atoy.atoymg.models.Eventvendor;
import org.atoy.atoymg.models.dto.EventvendorSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.EventvendorService;
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
@RequestMapping("/eventvendors")
@Tag(name = "Eventvendor", description = "Eventvendor Management APIs")
public class EventvendorController  {
	private final EventvendorService eventvendorService;

	public EventvendorController(EventvendorService eventvendorService) {
	   this.eventvendorService = eventvendorService;
	}

	@Operation(
	    summary = "Retrieve all eventvendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of eventvendor items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of eventvendor",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No eventvendor found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Eventvendor>>> getAllEventvendors(
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
	    Page<Eventvendor> eventvendors = eventvendorService.getAllEventvendor(pageable);
	
	    String message = "eventvendor retrieved successfully";
	
	    if(!eventvendors.hasContent()) {
	            message = "No eventvendor found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Eventvendor>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            eventvendors
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all eventvendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of eventvendor items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of eventvendor",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No eventvendor found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Eventvendor>>> getAllEventvendors(
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
	    @RequestBody EventvendorSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Eventvendor> eventvendors = eventvendorService.getAllEventvendor(pageable, object);
	
	    String message = "eventvendor retrieved successfully";
	
	    if(!eventvendors.hasContent()) {
	            message = "No eventvendor found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Eventvendor>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            eventvendors
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get eventvendor by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific eventvendor item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the eventvendor",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Eventvendor not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Eventvendor>> getEventvendorById(
	    @Parameter(description = "ID of the eventvendor to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Eventvendor eventvendor = eventvendorService.getEventvendorById(id);
	    RestResponse<Eventvendor> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "eventvendor retrieved successfully", eventvendor);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new eventvendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new eventvendor item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Eventvendor created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid eventvendor supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createEventvendor(
	    @Parameter(description = "Eventvendor object to be created", required = true)
	    @RequestBody @Valid Eventvendor eventvendor
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
	    Eventvendor newEventvendor = eventvendorService.createEventvendor(eventvendor);
	    RestResponse<Eventvendor> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "eventvendor created successfully", newEventvendor);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing eventvendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing eventvendor item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Eventvendor updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Eventvendor not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid eventvendor supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEventvendor(
	    @Parameter(description = "ID of the eventvendor to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated eventvendor object", required = true)
	    @RequestBody @Valid Eventvendor eventvendor
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
	    Eventvendor updateEventvendor = eventvendorService.updateEventvendor(id, eventvendor);
	    RestResponse<Eventvendor> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "eventvendor updated successfully", updateEventvendor);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete eventvendor",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a eventvendor item by its ID. Returns success even if eventvendor was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Eventvendor deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteEventvendorById(
	    @Parameter(description = "ID of the eventvendor to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			eventvendorService.getEventvendorById(id);
			eventvendorService.deleteEventvendor(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"eventvendor deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"eventvendor already deleted or does not exist",
					null
				)
			);
		}
	}


}
