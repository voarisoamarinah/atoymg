package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Vendortaglink;
import org.atoy.atoymg.models.dto.VendortaglinkSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface VendortaglinkService {
    Page<Vendortaglink> getAllVendortaglink(Pageable pageable);

    Page<Vendortaglink> getAllVendortaglink(Pageable pageable, VendortaglinkSearch object);

    Vendortaglink getVendortaglinkById(Long id);

    

    Vendortaglink createVendortaglink(Vendortaglink vendortaglink);

    Vendortaglink updateVendortaglink(Long id, Vendortaglink vendortaglink);

    void deleteVendortaglink(Long id);
    
}
