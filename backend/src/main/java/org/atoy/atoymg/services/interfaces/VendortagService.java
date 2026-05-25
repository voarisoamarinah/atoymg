package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Vendortag;
import org.atoy.atoymg.models.dto.VendortagSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface VendortagService {
    Page<Vendortag> getAllVendortag(Pageable pageable);

    Page<Vendortag> getAllVendortag(Pageable pageable, VendortagSearch object);

    Vendortag getVendortagById(Long id);

    

    Vendortag createVendortag(Vendortag vendortag);

    Vendortag updateVendortag(Long id, Vendortag vendortag);

    void deleteVendortag(Long id);
    
}
