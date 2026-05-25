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
import org.atoy.atoymg.models.Client;
import org.atoy.atoymg.models.dto.ClientSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.ClientService;
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
@RequestMapping("/clients")
@Tag(name = "Client", description = "Client Management APIs")
public class ClientController  {
	private final ClientService clientService;

	public ClientController(ClientService clientService) {
	   this.clientService = clientService;
	}

	@Operation(
	    summary = "Retrieve all client",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of client items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of client",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No client found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Client>>> getAllClients(
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
	    Page<Client> clients = clientService.getAllClient(pageable);
	
	    String message = "client retrieved successfully";
	
	    if(!clients.hasContent()) {
	            message = "No client found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Client>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            clients
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all client",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of client items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of client",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No client found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Client>>> getAllClients(
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
	    @RequestBody ClientSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Client> clients = clientService.getAllClient(pageable, object);
	
	    String message = "client retrieved successfully";
	
	    if(!clients.hasContent()) {
	            message = "No client found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Client>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            clients
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get client by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific client item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the client",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Client not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Client>> getClientById(
	    @Parameter(description = "ID of the client to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Client client = clientService.getClientById(id);
	    RestResponse<Client> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "client retrieved successfully", client);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new client",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new client item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Client created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid client supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createClient(
	    @Parameter(description = "Client object to be created", required = true)
	    @RequestBody @Valid Client client
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
	    Client newClient = clientService.createClient(client);
	    RestResponse<Client> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "client created successfully", newClient);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing client",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing client item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Client updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Client not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid client supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateClient(
	    @Parameter(description = "ID of the client to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated client object", required = true)
	    @RequestBody @Valid Client client
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
	    Client updateClient = clientService.updateClient(id, client);
	    RestResponse<Client> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "client updated successfully", updateClient);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete client",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a client item by its ID. Returns success even if client was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Client deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteClientById(
	    @Parameter(description = "ID of the client to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			clientService.getClientById(id);
			clientService.deleteClient(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"client deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"client already deleted or does not exist",
					null
				)
			);
		}
	}


}
