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
import org.atoy.atoymg.models.Event;
import org.atoy.atoymg.models.dto.EventSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.EventService;
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
@RequestMapping("/events")
@Tag(name = "Event", description = "Event Management APIs")
public class EventController  {
	private final EventService eventService;

	public EventController(EventService eventService) {
	   this.eventService = eventService;
	}

	@Operation(
	    summary = "Retrieve all event",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of event items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of event",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No event found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Event>>> getAllEvents(
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
	    Page<Event> events = eventService.getAllEvent(pageable);
	
	    String message = "event retrieved successfully";
	
	    if(!events.hasContent()) {
	            message = "No event found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Event>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            events
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all event",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of event items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of event",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No event found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Event>>> getAllEvents(
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
	    @RequestBody EventSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Event> events = eventService.getAllEvent(pageable, object);
	
	    String message = "event retrieved successfully";
	
	    if(!events.hasContent()) {
	            message = "No event found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Event>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            events
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get event by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific event item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the event",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Event not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Event>> getEventById(
	    @Parameter(description = "ID of the event to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Event event = eventService.getEventById(id);
	    RestResponse<Event> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "event retrieved successfully", event);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new event",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new event item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Event created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid event supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createEvent(
	    @Parameter(description = "Event object to be created", required = true)
	    @RequestBody @Valid Event event
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
	    Event newEvent = eventService.createEvent(event);
	    RestResponse<Event> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "event created successfully", newEvent);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing event",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing event item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Event updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Event not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid event supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEvent(
	    @Parameter(description = "ID of the event to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated event object", required = true)
	    @RequestBody @Valid Event event
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
	    Event updateEvent = eventService.updateEvent(id, event);
	    RestResponse<Event> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "event updated successfully", updateEvent);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete event",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a event item by its ID. Returns success even if event was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Event deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteEventById(
	    @Parameter(description = "ID of the event to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			eventService.getEventById(id);
			eventService.deleteEvent(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"event deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"event already deleted or does not exist",
					null
				)
			);
		}
	}


}
