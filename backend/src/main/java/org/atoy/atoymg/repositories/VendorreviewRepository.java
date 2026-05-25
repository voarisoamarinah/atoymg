package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Vendorreview;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VendorreviewRepository extends JpaRepository<Vendorreview, Long>, JpaSpecificationExecutor<Vendorreview> {
}
