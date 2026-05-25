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
import org.atoy.atoymg.models.Eventtype;
import org.atoy.atoymg.models.dto.EventtypeSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.EventtypeService;
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
@RequestMapping("/eventtypes")
@Tag(name = "Eventtype", description = "Eventtype Management APIs")
public class EventtypeController  {
	private final EventtypeService eventtypeService;

	public EventtypeController(EventtypeService eventtypeService) {
	   this.eventtypeService = eventtypeService;
	}

	@Operation(
	    summary = "Retrieve all eventtype",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of eventtype items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of eventtype",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No eventtype found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Eventtype>>> getAllEventtypes(
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
	    Page<Eventtype> eventtypes = eventtypeService.getAllEventtype(pageable);
	
	    String message = "eventtype retrieved successfully";
	
	    if(!eventtypes.hasContent()) {
	            message = "No eventtype found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Eventtype>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            eventtypes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all eventtype",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of eventtype items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of eventtype",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No eventtype found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Eventtype>>> getAllEventtypes(
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
	    @RequestBody EventtypeSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Eventtype> eventtypes = eventtypeService.getAllEventtype(pageable, object);
	
	    String message = "eventtype retrieved successfully";
	
	    if(!eventtypes.hasContent()) {
	            message = "No eventtype found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Eventtype>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            eventtypes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get eventtype by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific eventtype item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the eventtype",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Eventtype not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Eventtype>> getEventtypeById(
	    @Parameter(description = "ID of the eventtype to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Eventtype eventtype = eventtypeService.getEventtypeById(id);
	    RestResponse<Eventtype> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "eventtype retrieved successfully", eventtype);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new eventtype",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new eventtype item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Eventtype created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid eventtype supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createEventtype(
	    @Parameter(description = "Eventtype object to be created", required = true)
	    @RequestBody @Valid Eventtype eventtype
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
	    Eventtype newEventtype = eventtypeService.createEventtype(eventtype);
	    RestResponse<Eventtype> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "eventtype created successfully", newEventtype);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing eventtype",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing eventtype item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Eventtype updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Eventtype not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid eventtype supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEventtype(
	    @Parameter(description = "ID of the eventtype to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated eventtype object", required = true)
	    @RequestBody @Valid Eventtype eventtype
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
	    Eventtype updateEventtype = eventtypeService.updateEventtype(id, eventtype);
	    RestResponse<Eventtype> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "eventtype updated successfully", updateEventtype);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete eventtype",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a eventtype item by its ID. Returns success even if eventtype was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Eventtype deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteEventtypeById(
	    @Parameter(description = "ID of the eventtype to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			eventtypeService.getEventtypeById(id);
			eventtypeService.deleteEventtype(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"eventtype deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"eventtype already deleted or does not exist",
					null
				)
			);
		}
	}


}
