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
import org.atoy.atoymg.models.Eventstatuse;
import org.atoy.atoymg.models.dto.EventstatuseSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.EventstatuseService;
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
@RequestMapping("/eventstatuses")
@Tag(name = "Eventstatuse", description = "Eventstatuse Management APIs")
public class EventstatuseController  {
	private final EventstatuseService eventstatuseService;

	public EventstatuseController(EventstatuseService eventstatuseService) {
	   this.eventstatuseService = eventstatuseService;
	}

	@Operation(
	    summary = "Retrieve all eventstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of eventstatuse items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of eventstatuse",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No eventstatuse found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Eventstatuse>>> getAllEventstatuses(
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
	    Page<Eventstatuse> eventstatuses = eventstatuseService.getAllEventstatuse(pageable);
	
	    String message = "eventstatuse retrieved successfully";
	
	    if(!eventstatuses.hasContent()) {
	            message = "No eventstatuse found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Eventstatuse>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            eventstatuses
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all eventstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of eventstatuse items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of eventstatuse",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No eventstatuse found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Eventstatuse>>> getAllEventstatuses(
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
	    @RequestBody EventstatuseSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Eventstatuse> eventstatuses = eventstatuseService.getAllEventstatuse(pageable, object);
	
	    String message = "eventstatuse retrieved successfully";
	
	    if(!eventstatuses.hasContent()) {
	            message = "No eventstatuse found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Eventstatuse>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            eventstatuses
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get eventstatuse by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific eventstatuse item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the eventstatuse",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Eventstatuse not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Eventstatuse>> getEventstatuseById(
	    @Parameter(description = "ID of the eventstatuse to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Eventstatuse eventstatuse = eventstatuseService.getEventstatuseById(id);
	    RestResponse<Eventstatuse> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "eventstatuse retrieved successfully", eventstatuse);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new eventstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new eventstatuse item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Eventstatuse created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid eventstatuse supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createEventstatuse(
	    @Parameter(description = "Eventstatuse object to be created", required = true)
	    @RequestBody @Valid Eventstatuse eventstatuse
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
	    Eventstatuse newEventstatuse = eventstatuseService.createEventstatuse(eventstatuse);
	    RestResponse<Eventstatuse> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "eventstatuse created successfully", newEventstatuse);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing eventstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing eventstatuse item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Eventstatuse updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Eventstatuse not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid eventstatuse supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEventstatuse(
	    @Parameter(description = "ID of the eventstatuse to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated eventstatuse object", required = true)
	    @RequestBody @Valid Eventstatuse eventstatuse
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
	    Eventstatuse updateEventstatuse = eventstatuseService.updateEventstatuse(id, eventstatuse);
	    RestResponse<Eventstatuse> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "eventstatuse updated successfully", updateEventstatuse);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete eventstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a eventstatuse item by its ID. Returns success even if eventstatuse was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Eventstatuse deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteEventstatuseById(
	    @Parameter(description = "ID of the eventstatuse to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			eventstatuseService.getEventstatuseById(id);
			eventstatuseService.deleteEventstatuse(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"eventstatuse deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"eventstatuse already deleted or does not exist",
					null
				)
			);
		}
	}


}
