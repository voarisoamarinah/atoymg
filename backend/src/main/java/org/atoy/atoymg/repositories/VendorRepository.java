package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Vendor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VendorRepository extends JpaRepository<Vendor, Long>, JpaSpecificationExecutor<Vendor> {
}
