package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Eventstatuse;
import org.atoy.atoymg.models.dto.EventstatuseSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.EventstatuseRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.EventstatuseService;
import org.atoy.atoymg.specification.EventstatuseSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultEventstatuseService implements EventstatuseService {
	private final EventstatuseRepository eventstatuseRepository;

	public DefaultEventstatuseService(EventstatuseRepository eventstatuseRepository) {
	   this.eventstatuseRepository = eventstatuseRepository;
	}

	@Override
	public Page<Eventstatuse> getAllEventstatuse(Pageable pageable) {
	    try {
	        return eventstatuseRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving eventstatuse", ex);
	    }
	}

	@Override
	public Page<Eventstatuse> getAllEventstatuse(Pageable pageable, EventstatuseSearch object) {
	    try {
	        Specification<Eventstatuse> spec=EventstatuseSpecification.filter(object);
	        return eventstatuseRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving eventstatuse", ex);
	    }
	}

	@Override
	public Eventstatuse getEventstatuseById(Long id) {
	    Optional<Eventstatuse> eventstatuse = eventstatuseRepository.findById(id);
	    if (eventstatuse.isPresent()) {
	        return eventstatuse.get();
	    } else {
	        throw new ResourceNotFoundException("Eventstatuse not found with id : " + id);
	    }
	}

 

	@Override
	public Eventstatuse createEventstatuse(Eventstatuse eventstatuse) {
	    try {
	        return eventstatuseRepository.save(eventstatuse);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating eventstatuse", ex);
	    }
	}

	@Override
	public Eventstatuse updateEventstatuse(Long id, Eventstatuse eventstatuse) {
	    Optional<Eventstatuse> existingEventstatuse = eventstatuseRepository.findById(id);
	    if (existingEventstatuse.isPresent()) {
	        eventstatuse.setId(id);
	        try {
	            return eventstatuseRepository.save(eventstatuse);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating eventstatuse", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Eventstatuse not found with id : " + id);
	    }
	}

	@Override
	public void deleteEventstatuse(Long id) {
	    try {
	        eventstatuseRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting eventstatuse", ex);
	    }
	}


}
