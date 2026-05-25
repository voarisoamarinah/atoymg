package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Document;
import org.atoy.atoymg.models.dto.DocumentSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.DocumentRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.DocumentService;
import org.atoy.atoymg.specification.DocumentSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultDocumentService implements DocumentService {
	private final DocumentRepository documentRepository;

	public DefaultDocumentService(DocumentRepository documentRepository) {
	   this.documentRepository = documentRepository;
	}

	@Override
	public Page<Document> getAllDocument(Pageable pageable) {
	    try {
	        return documentRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving document", ex);
	    }
	}

	@Override
	public Page<Document> getAllDocument(Pageable pageable, DocumentSearch object) {
	    try {
	        Specification<Document> spec=DocumentSpecification.filter(object);
	        return documentRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving document", ex);
	    }
	}

	@Override
	public Document getDocumentById(Long id) {
	    Optional<Document> document = documentRepository.findById(id);
	    if (document.isPresent()) {
	        return document.get();
	    } else {
	        throw new ResourceNotFoundException("Document not found with id : " + id);
	    }
	}

 

	@Override
	public Document createDocument(Document document) {
	    try {
	        return documentRepository.save(document);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating document", ex);
	    }
	}

	@Override
	public Document updateDocument(Long id, Document document) {
	    Optional<Document> existingDocument = documentRepository.findById(id);
	    if (existingDocument.isPresent()) {
	        document.setId(id);
	        try {
	            return documentRepository.save(document);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating document", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Document not found with id : " + id);
	    }
	}

	@Override
	public void deleteDocument(Long id) {
	    try {
	        documentRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting document", ex);
	    }
	}


}
