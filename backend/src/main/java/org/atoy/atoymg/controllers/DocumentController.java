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
import org.atoy.atoymg.models.Document;
import org.atoy.atoymg.models.dto.DocumentSearch;
import org.springframework.web.bind.annotation.*;
import org.atoy.atoymg.services.interfaces.DocumentService;
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
@RequestMapping("/documents")
@Tag(name = "Document", description = "Document Management APIs")
public class DocumentController  {
	private final DocumentService documentService;

	public DocumentController(DocumentService documentService) {
	   this.documentService = documentService;
	}

	@Operation(
	    summary = "Retrieve all document",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of document items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of document",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No document found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Document>>> getAllDocuments(
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
	    Page<Document> documents = documentService.getAllDocument(pageable);
	
	    String message = "document retrieved successfully";
	
	    if(!documents.hasContent()) {
	            message = "No document found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Document>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            documents
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all document",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of document items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of document",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No document found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Document>>> getAllDocuments(
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
	    @RequestBody DocumentSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Document> documents = documentService.getAllDocument(pageable, object);
	
	    String message = "document retrieved successfully";
	
	    if(!documents.hasContent()) {
	            message = "No document found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Document>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            documents
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get document by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific document item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the document",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Document not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Document>> getDocumentById(
	    @Parameter(description = "ID of the document to retrieve", required = true)
	    @PathVariable Long id
	) {
	    Document document = documentService.getDocumentById(id);
	    RestResponse<Document> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "document retrieved successfully", document);
	    return ResponseEntity.ok(response);
	}

 

	@Operation(
	    summary = "Create new document",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Create a new document item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Document created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid document supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createDocument(
	    @Parameter(description = "Document object to be created", required = true)
	    @RequestBody @Valid Document document
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
	    Document newDocument = documentService.createDocument(document);
	    RestResponse<Document> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "document created successfully", newDocument);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing document",
	    security = { @SecurityRequirement(name = "bearerAuth") },


	    description = "Update an existing document item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Document updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Document not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid document supplied"
	    )
	})

 
	@PutMapping("/{id}")
	public ResponseEntity<?> updateDocument(
	    @Parameter(description = "ID of the document to update", required = true)
	    @PathVariable Long id,
	    @Parameter(description = "Updated document object", required = true)
	    @RequestBody @Valid Document document
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
	    Document updateDocument = documentService.updateDocument(id, document);
	    RestResponse<Document> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "document updated successfully", updateDocument);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete document",
	    security = { @SecurityRequirement(name = "bearerAuth") },    


	    description = "Delete a document item by its ID. Returns success even if document was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Document deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteDocumentById(
	    @Parameter(description = "ID of the document to delete", required = true)
	    @PathVariable Long id
	) {
		try {
			documentService.getDocumentById(id);
			documentService.deleteDocument(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"document deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"document already deleted or does not exist",
					null
				)
			);
		}
	}


}
