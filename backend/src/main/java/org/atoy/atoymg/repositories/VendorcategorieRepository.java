package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Vendorcategorie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VendorcategorieRepository extends JpaRepository<Vendorcategorie, Long>, JpaSpecificationExecutor<Vendorcategorie> {
}
