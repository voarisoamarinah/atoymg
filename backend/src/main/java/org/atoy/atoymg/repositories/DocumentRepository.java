package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Document;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
}
