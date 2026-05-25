package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Vendor;
import org.atoy.atoymg.models.dto.VendorSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface VendorService {
    Page<Vendor> getAllVendor(Pageable pageable);

    Page<Vendor> getAllVendor(Pageable pageable, VendorSearch object);

    Vendor getVendorById(Long id);

    

    Vendor createVendor(Vendor vendor);

    Vendor updateVendor(Long id, Vendor vendor);

    void deleteVendor(Long id);
    
}
