package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Document;
import org.atoy.atoymg.models.dto.DocumentSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface DocumentService {
    Page<Document> getAllDocument(Pageable pageable);

    Page<Document> getAllDocument(Pageable pageable, DocumentSearch object);

    Document getDocumentById(Long id);

    

    Document createDocument(Document document);

    Document updateDocument(Long id, Document document);

    void deleteDocument(Long id);
    
}
