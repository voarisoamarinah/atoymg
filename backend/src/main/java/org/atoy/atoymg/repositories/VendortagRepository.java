package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Vendortag;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VendortagRepository extends JpaRepository<Vendortag, Long>, JpaSpecificationExecutor<Vendortag> {
}
