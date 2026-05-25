package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Vendorcategorie;
import org.atoy.atoymg.models.dto.VendorcategorieSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface VendorcategorieService {
    Page<Vendorcategorie> getAllVendorcategorie(Pageable pageable);

    Page<Vendorcategorie> getAllVendorcategorie(Pageable pageable, VendorcategorieSearch object);

    Vendorcategorie getVendorcategorieById(Long id);

    

    Vendorcategorie createVendorcategorie(Vendorcategorie vendorcategorie);

    Vendorcategorie updateVendorcategorie(Long id, Vendorcategorie vendorcategorie);

    void deleteVendorcategorie(Long id);
    
}
