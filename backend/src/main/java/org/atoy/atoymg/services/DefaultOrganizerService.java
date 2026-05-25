package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Organizer;
import org.atoy.atoymg.models.dto.OrganizerSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.OrganizerRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.OrganizerService;
import org.atoy.atoymg.specification.OrganizerSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultOrganizerService implements OrganizerService {
	private final OrganizerRepository organizerRepository;

	public DefaultOrganizerService(OrganizerRepository organizerRepository) {
	   this.organizerRepository = organizerRepository;
	}

	@Override
	public Page<Organizer> getAllOrganizer(Pageable pageable) {
	    try {
	        return organizerRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving organizer", ex);
	    }
	}

	@Override
	public Page<Organizer> getAllOrganizer(Pageable pageable, OrganizerSearch object) {
	    try {
	        Specification<Organizer> spec=OrganizerSpecification.filter(object);
	        return organizerRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving organizer", ex);
	    }
	}

	@Override
	public Organizer getOrganizerById(Long id) {
	    Optional<Organizer> organizer = organizerRepository.findById(id);
	    if (organizer.isPresent()) {
	        return organizer.get();
	    } else {
	        throw new ResourceNotFoundException("Organizer not found with id : " + id);
	    }
	}

 

	@Override
	public Organizer createOrganizer(Organizer organizer) {
	    try {
	        return organizerRepository.save(organizer);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating organizer", ex);
	    }
	}

	@Override
	public Organizer updateOrganizer(Long id, Organizer organizer) {
	    Optional<Organizer> existingOrganizer = organizerRepository.findById(id);
	    if (existingOrganizer.isPresent()) {
	        organizer.setId(id);
	        try {
	            return organizerRepository.save(organizer);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating organizer", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Organizer not found with id : " + id);
	    }
	}

	@Override
	public void deleteOrganizer(Long id) {
	    try {
	        organizerRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting organizer", ex);
	    }
	}


}
