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
import org.atoy.atoymg.models.Eventvendorstatuse;
import org.atoy.atoymg.models.dto.EventvendorstatuseSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.EventvendorstatuseService;
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
@RequestMapping("/eventvendorstatuses")
@Tag(name = "Eventvendorstatuse", description = "Eventvendorstatuse Management APIs")
public class EventvendorstatuseController  {
	private final EventvendorstatuseService eventvendorstatuseService;

	public EventvendorstatuseController(EventvendorstatuseService eventvendorstatuseService) {
	   this.eventvendorstatuseService = eventvendorstatuseService;
	}

	@Operation(
	    summary = "Retrieve all eventvendorstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of eventvendorstatuse items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of eventvendorstatuse",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No eventvendorstatuse found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Eventvendorstatuse>>> getAllEventvendorstatuses(
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
	    Page<Eventvendorstatuse> eventvendorstatuses = eventvendorstatuseService.getAllEventvendorstatuse(pageable);
	
	    String message = "eventvendorstatuse retrieved successfully";
	
	    if(!eventvendorstatuses.hasContent()) {
	            message = "No eventvendorstatuse found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Eventvendorstatuse>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            eventvendorstatuses
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all eventvendorstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of eventvendorstatuse items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of eventvendorstatuse",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No eventvendorstatuse found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Eventvendorstatuse>>> getAllEventvendorstatuses(
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
	    @RequestBody EventvendorstatuseSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Eventvendorstatuse> eventvendorstatuses = eventvendorstatuseService.getAllEventvendorstatuse(pageable, object);
	
	    String message = "eventvendorstatuse retrieved successfully";
	
	    if(!eventvendorstatuses.hasContent()) {
	            message = "No eventvendorstatuse found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Eventvendorstatuse>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            eventvendorstatuses
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get eventvendorstatuse by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific eventvendorstatuse item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the eventvendorstatuse",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Eventvendorstatuse not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Eventvendorstatuse>> getEventvendorstatuseById(
	    @Parameter(description = "ID of the eventvendorstatuse to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Eventvendorstatuse eventvendorstatuse = eventvendorstatuseService.getEventvendorstatuseById(id);
	    RestResponse<Eventvendorstatuse> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "eventvendorstatuse retrieved successfully", eventvendorstatuse);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new eventvendorstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new eventvendorstatuse item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Eventvendorstatuse created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid eventvendorstatuse supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createEventvendorstatuse(
	    @Parameter(description = "Eventvendorstatuse object to be created", required = true)
	    @RequestBody @Valid Eventvendorstatuse eventvendorstatuse
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
	    Eventvendorstatuse newEventvendorstatuse = eventvendorstatuseService.createEventvendorstatuse(eventvendorstatuse);
	    RestResponse<Eventvendorstatuse> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "eventvendorstatuse created successfully", newEventvendorstatuse);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing eventvendorstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing eventvendorstatuse item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Eventvendorstatuse updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Eventvendorstatuse not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid eventvendorstatuse supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEventvendorstatuse(
	    @Parameter(description = "ID of the eventvendorstatuse to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated eventvendorstatuse object", required = true)
	    @RequestBody @Valid Eventvendorstatuse eventvendorstatuse
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
	    Eventvendorstatuse updateEventvendorstatuse = eventvendorstatuseService.updateEventvendorstatuse(id, eventvendorstatuse);
	    RestResponse<Eventvendorstatuse> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "eventvendorstatuse updated successfully", updateEventvendorstatuse);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete eventvendorstatuse",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a eventvendorstatuse item by its ID. Returns success even if eventvendorstatuse was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Eventvendorstatuse deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteEventvendorstatuseById(
	    @Parameter(description = "ID of the eventvendorstatuse to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			eventvendorstatuseService.getEventvendorstatuseById(id);
			eventvendorstatuseService.deleteEventvendorstatuse(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"eventvendorstatuse deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"eventvendorstatuse already deleted or does not exist",
					null
				)
			);
		}
	}


}
