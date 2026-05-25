package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Vendorcategorie;
import org.atoy.atoymg.models.dto.VendorcategorieSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.VendorcategorieRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.VendorcategorieService;
import org.atoy.atoymg.specification.VendorcategorieSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultVendorcategorieService implements VendorcategorieService {
	private final VendorcategorieRepository vendorcategorieRepository;

	public DefaultVendorcategorieService(VendorcategorieRepository vendorcategorieRepository) {
	   this.vendorcategorieRepository = vendorcategorieRepository;
	}

	@Override
	public Page<Vendorcategorie> getAllVendorcategorie(Pageable pageable) {
	    try {
	        return vendorcategorieRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendorcategorie", ex);
	    }
	}

	@Override
	public Page<Vendorcategorie> getAllVendorcategorie(Pageable pageable, VendorcategorieSearch object) {
	    try {
	        Specification<Vendorcategorie> spec=VendorcategorieSpecification.filter(object);
	        return vendorcategorieRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendorcategorie", ex);
	    }
	}

	@Override
	public Vendorcategorie getVendorcategorieById(Long id) {
	    Optional<Vendorcategorie> vendorcategorie = vendorcategorieRepository.findById(id);
	    if (vendorcategorie.isPresent()) {
	        return vendorcategorie.get();
	    } else {
	        throw new ResourceNotFoundException("Vendorcategorie not found with id : " + id);
	    }
	}

 

	@Override
	public Vendorcategorie createVendorcategorie(Vendorcategorie vendorcategorie) {
	    try {
	        return vendorcategorieRepository.save(vendorcategorie);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating vendorcategorie", ex);
	    }
	}

	@Override
	public Vendorcategorie updateVendorcategorie(Long id, Vendorcategorie vendorcategorie) {
	    Optional<Vendorcategorie> existingVendorcategorie = vendorcategorieRepository.findById(id);
	    if (existingVendorcategorie.isPresent()) {
	        vendorcategorie.setId(id);
	        try {
	            return vendorcategorieRepository.save(vendorcategorie);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating vendorcategorie", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Vendorcategorie not found with id : " + id);
	    }
	}

	@Override
	public void deleteVendorcategorie(Long id) {
	    try {
	        vendorcategorieRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting vendorcategorie", ex);
	    }
	}


}
