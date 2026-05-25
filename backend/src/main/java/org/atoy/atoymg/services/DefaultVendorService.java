package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Vendor;
import org.atoy.atoymg.models.dto.VendorSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.VendorRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.VendorService;
import org.atoy.atoymg.specification.VendorSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultVendorService implements VendorService {
	private final VendorRepository vendorRepository;

	public DefaultVendorService(VendorRepository vendorRepository) {
	   this.vendorRepository = vendorRepository;
	}

	@Override
	public Page<Vendor> getAllVendor(Pageable pageable) {
	    try {
	        return vendorRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendor", ex);
	    }
	}

	@Override
	public Page<Vendor> getAllVendor(Pageable pageable, VendorSearch object) {
	    try {
	        Specification<Vendor> spec=VendorSpecification.filter(object);
	        return vendorRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving vendor", ex);
	    }
	}

	@Override
	public Vendor getVendorById(Long id) {
	    Optional<Vendor> vendor = vendorRepository.findById(id);
	    if (vendor.isPresent()) {
	        return vendor.get();
	    } else {
	        throw new ResourceNotFoundException("Vendor not found with id : " + id);
	    }
	}

 

	@Override
	public Vendor createVendor(Vendor vendor) {
	    try {
	        return vendorRepository.save(vendor);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating vendor", ex);
	    }
	}

	@Override
	public Vendor updateVendor(Long id, Vendor vendor) {
	    Optional<Vendor> existingVendor = vendorRepository.findById(id);
	    if (existingVendor.isPresent()) {
	        vendor.setId(id);
	        try {
	            return vendorRepository.save(vendor);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating vendor", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Vendor not found with id : " + id);
	    }
	}

	@Override
	public void deleteVendor(Long id) {
	    try {
	        vendorRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting vendor", ex);
	    }
	}


}
