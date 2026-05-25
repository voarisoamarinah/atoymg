package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Eventvendorstatuse;
import org.atoy.atoymg.models.dto.EventvendorstatuseSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.EventvendorstatuseRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.EventvendorstatuseService;
import org.atoy.atoymg.specification.EventvendorstatuseSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultEventvendorstatuseService implements EventvendorstatuseService {
	private final EventvendorstatuseRepository eventvendorstatuseRepository;

	public DefaultEventvendorstatuseService(EventvendorstatuseRepository eventvendorstatuseRepository) {
	   this.eventvendorstatuseRepository = eventvendorstatuseRepository;
	}

	@Override
	public Page<Eventvendorstatuse> getAllEventvendorstatuse(Pageable pageable) {
	    try {
	        return eventvendorstatuseRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving eventvendorstatuse", ex);
	    }
	}

	@Override
	public Page<Eventvendorstatuse> getAllEventvendorstatuse(Pageable pageable, EventvendorstatuseSearch object) {
	    try {
	        Specification<Eventvendorstatuse> spec=EventvendorstatuseSpecification.filter(object);
	        return eventvendorstatuseRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving eventvendorstatuse", ex);
	    }
	}

	@Override
	public Eventvendorstatuse getEventvendorstatuseById(Long id) {
	    Optional<Eventvendorstatuse> eventvendorstatuse = eventvendorstatuseRepository.findById(id);
	    if (eventvendorstatuse.isPresent()) {
	        return eventvendorstatuse.get();
	    } else {
	        throw new ResourceNotFoundException("Eventvendorstatuse not found with id : " + id);
	    }
	}

 

	@Override
	public Eventvendorstatuse createEventvendorstatuse(Eventvendorstatuse eventvendorstatuse) {
	    try {
	        return eventvendorstatuseRepository.save(eventvendorstatuse);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating eventvendorstatuse", ex);
	    }
	}

	@Override
	public Eventvendorstatuse updateEventvendorstatuse(Long id, Eventvendorstatuse eventvendorstatuse) {
	    Optional<Eventvendorstatuse> existingEventvendorstatuse = eventvendorstatuseRepository.findById(id);
	    if (existingEventvendorstatuse.isPresent()) {
	        eventvendorstatuse.setId(id);
	        try {
	            return eventvendorstatuseRepository.save(eventvendorstatuse);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating eventvendorstatuse", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Eventvendorstatuse not found with id : " + id);
	    }
	}

	@Override
	public void deleteEventvendorstatuse(Long id) {
	    try {
	        eventvendorstatuseRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting eventvendorstatuse", ex);
	    }
	}


}
