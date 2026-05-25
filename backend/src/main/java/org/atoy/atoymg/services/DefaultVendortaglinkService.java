package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Vendortaglink;
import org.atoy.atoymg.models.dto.VendortaglinkSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.VendortaglinkRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.VendortaglinkService;
import org.atoy.atoymg.specification.VendortaglinkSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultVendortaglinkService implements VendortaglinkService {
	private final VendortaglinkRepository vendortaglinkRepository;

	public DefaultVendortaglinkService(VendortaglinkRepository vendortaglinkRepository) {
	   this.vendortaglinkRepository = vendortaglinkRepository;
	}

	@Override
	public Page<Vendortaglink> getAllVendortaglink(Pageable pageable) {
	    try {
	        return vendortaglinkRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendortaglink", ex);
	    }
	}

	@Override
	public Page<Vendortaglink> getAllVendortaglink(Pageable pageable, VendortaglinkSearch object) {
	    try {
	        Specification<Vendortaglink> spec=VendortaglinkSpecification.filter(object);
	        return vendortaglinkRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendortaglink", ex);
	    }
	}

	@Override
	public Vendortaglink getVendortaglinkById(Long id) {
	    Optional<Vendortaglink> vendortaglink = vendortaglinkRepository.findById(id);
	    if (vendortaglink.isPresent()) {
	        return vendortaglink.get();
	    } else {
	        throw new ResourceNotFoundException("Vendortaglink not found with id : " + id);
	    }
	}

 

	@Override
	public Vendortaglink createVendortaglink(Vendortaglink vendortaglink) {
	    try {
	        return vendortaglinkRepository.save(vendortaglink);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating vendortaglink", ex);
	    }
	}

	@Override
	public Vendortaglink updateVendortaglink(Long id, Vendortaglink vendortaglink) {
	    Optional<Vendortaglink> existingVendortaglink = vendortaglinkRepository.findById(id);
	    if (existingVendortaglink.isPresent()) {
	        vendortaglink.setId(id);
	        try {
	            return vendortaglinkRepository.save(vendortaglink);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating vendortaglink", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Vendortaglink not found with id : " + id);
	    }
	}

	@Override
	public void deleteVendortaglink(Long id) {
	    try {
	        vendortaglinkRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting vendortaglink", ex);
	    }
	}


}
