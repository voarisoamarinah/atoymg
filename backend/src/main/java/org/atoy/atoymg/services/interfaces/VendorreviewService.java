package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Vendorreview;
import org.atoy.atoymg.models.dto.VendorreviewSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface VendorreviewService {
    Page<Vendorreview> getAllVendorreview(Pageable pageable);

    Page<Vendorreview> getAllVendorreview(Pageable pageable, VendorreviewSearch object);

    Vendorreview getVendorreviewById(Long id);

    

    Vendorreview createVendorreview(Vendorreview vendorreview);

    Vendorreview updateVendorreview(Long id, Vendorreview vendorreview);

    void deleteVendorreview(Long id);
    
}
