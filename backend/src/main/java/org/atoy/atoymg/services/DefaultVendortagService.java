package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Vendortag;
import org.atoy.atoymg.models.dto.VendortagSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.VendortagRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.VendortagService;
import org.atoy.atoymg.specification.VendortagSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultVendortagService implements VendortagService {
	private final VendortagRepository vendortagRepository;

	public DefaultVendortagService(VendortagRepository vendortagRepository) {
	   this.vendortagRepository = vendortagRepository;
	}

	@Override
	public Page<Vendortag> getAllVendortag(Pageable pageable) {
	    try {
	        return vendortagRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendortag", ex);
	    }
	}

	@Override
	public Page<Vendortag> getAllVendortag(Pageable pageable, VendortagSearch object) {
	    try {
	        Specification<Vendortag> spec=VendortagSpecification.filter(object);
	        return vendortagRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendortag", ex);
	    }
	}

	@Override
	public Vendortag getVendortagById(Long id) {
	    Optional<Vendortag> vendortag = vendortagRepository.findById(id);
	    if (vendortag.isPresent()) {
	        return vendortag.get();
	    } else {
	        throw new ResourceNotFoundException("Vendortag not found with id : " + id);
	    }
	}

 

	@Override
	public Vendortag createVendortag(Vendortag vendortag) {
	    try {
	        return vendortagRepository.save(vendortag);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating vendortag", ex);
	    }
	}

	@Override
	public Vendortag updateVendortag(Long id, Vendortag vendortag) {
	    Optional<Vendortag> existingVendortag = vendortagRepository.findById(id);
	    if (existingVendortag.isPresent()) {
	        vendortag.setId(id);
	        try {
	            return vendortagRepository.save(vendortag);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating vendortag", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Vendortag not found with id : " + id);
	    }
	}

	@Override
	public void deleteVendortag(Long id) {
	    try {
	        vendortagRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting vendortag", ex);
	    }
	}


}
