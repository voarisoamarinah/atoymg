package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Eventvendor;
import org.atoy.atoymg.models.dto.EventvendorSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.EventvendorRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.EventvendorService;
import org.atoy.atoymg.specification.EventvendorSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultEventvendorService implements EventvendorService {
	private final EventvendorRepository eventvendorRepository;

	public DefaultEventvendorService(EventvendorRepository eventvendorRepository) {
	   this.eventvendorRepository = eventvendorRepository;
	}

	@Override
	public Page<Eventvendor> getAllEventvendor(Pageable pageable) {
	    try {
	        return eventvendorRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving eventvendor", ex);
	    }
	}

	@Override
	public Page<Eventvendor> getAllEventvendor(Pageable pageable, EventvendorSearch object) {
	    try {
	        Specification<Eventvendor> spec=EventvendorSpecification.filter(object);
	        return eventvendorRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving eventvendor", ex);
	    }
	}

	@Override
	public Eventvendor getEventvendorById(Long id) {
	    Optional<Eventvendor> eventvendor = eventvendorRepository.findById(id);
	    if (eventvendor.isPresent()) {
	        return eventvendor.get();
	    } else {
	        throw new ResourceNotFoundException("Eventvendor not found with id : " + id);
	    }
	}

 

	@Override
	public Eventvendor createEventvendor(Eventvendor eventvendor) {
	    try {
	        return eventvendorRepository.save(eventvendor);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating eventvendor", ex);
	    }
	}

	@Override
	public Eventvendor updateEventvendor(Long id, Eventvendor eventvendor) {
	    Optional<Eventvendor> existingEventvendor = eventvendorRepository.findById(id);
	    if (existingEventvendor.isPresent()) {
	        eventvendor.setId(id);
	        try {
	            return eventvendorRepository.save(eventvendor);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating eventvendor", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Eventvendor not found with id : " + id);
	    }
	}

	@Override
	public void deleteEventvendor(Long id) {
	    try {
	        eventvendorRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting eventvendor", ex);
	    }
	}


}
