package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Vendorreview;
import org.atoy.atoymg.models.dto.VendorreviewSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.VendorreviewRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.VendorreviewService;
import org.atoy.atoymg.specification.VendorreviewSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultVendorreviewService implements VendorreviewService {
	private final VendorreviewRepository vendorreviewRepository;

	public DefaultVendorreviewService(VendorreviewRepository vendorreviewRepository) {
	   this.vendorreviewRepository = vendorreviewRepository;
	}

	@Override
	public Page<Vendorreview> getAllVendorreview(Pageable pageable) {
	    try {
	        return vendorreviewRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendorreview", ex);
	    }
	}

	@Override
	public Page<Vendorreview> getAllVendorreview(Pageable pageable, VendorreviewSearch object) {
	    try {
	        Specification<Vendorreview> spec=VendorreviewSpecification.filter(object);
	        return vendorreviewRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendorreview", ex);
	    }
	}

	@Override
	public Vendorreview getVendorreviewById(Long id) {
	    Optional<Vendorreview> vendorreview = vendorreviewRepository.findById(id);
	    if (vendorreview.isPresent()) {
	        return vendorreview.get();
	    } else {
	        throw new ResourceNotFoundException("Vendorreview not found with id : " + id);
	    }
	}

 

	@Override
	public Vendorreview createVendorreview(Vendorreview vendorreview) {
	    try {
	        return vendorreviewRepository.save(vendorreview);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating vendorreview", ex);
	    }
	}

	@Override
	public Vendorreview updateVendorreview(Long id, Vendorreview vendorreview) {
	    Optional<Vendorreview> existingVendorreview = vendorreviewRepository.findById(id);
	    if (existingVendorreview.isPresent()) {
	        vendorreview.setId(id);
	        try {
	            return vendorreviewRepository.save(vendorreview);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating vendorreview", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Vendorreview not found with id : " + id);
	    }
	}

	@Override
	public void deleteVendorreview(Long id) {
	    try {
	        vendorreviewRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting vendorreview", ex);
	    }
	}


}
