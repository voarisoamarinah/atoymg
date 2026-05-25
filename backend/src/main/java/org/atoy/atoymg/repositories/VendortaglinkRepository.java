package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Vendortaglink;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VendortaglinkRepository extends JpaRepository<Vendortaglink, Long>, JpaSpecificationExecutor<Vendortaglink> {
}
