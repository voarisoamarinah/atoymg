package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Eventtype;
import org.atoy.atoymg.models.dto.EventtypeSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.EventtypeRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.EventtypeService;
import org.atoy.atoymg.specification.EventtypeSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultEventtypeService implements EventtypeService {
	private final EventtypeRepository eventtypeRepository;

	public DefaultEventtypeService(EventtypeRepository eventtypeRepository) {
	   this.eventtypeRepository = eventtypeRepository;
	}

	@Override
	public Page<Eventtype> getAllEventtype(Pageable pageable) {
	    try {
	        return eventtypeRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving eventtype", ex);
	    }
	}

	@Override
	public Page<Eventtype> getAllEventtype(Pageable pageable, EventtypeSearch object) {
	    try {
	        Specification<Eventtype> spec=EventtypeSpecification.filter(object);
	        return eventtypeRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving eventtype", ex);
	    }
	}

	@Override
	public Eventtype getEventtypeById(Long id) {
	    Optional<Eventtype> eventtype = eventtypeRepository.findById(id);
	    if (eventtype.isPresent()) {
	        return eventtype.get();
	    } else {
	        throw new ResourceNotFoundException("Eventtype not found with id : " + id);
	    }
	}

 

	@Override
	public Eventtype createEventtype(Eventtype eventtype) {
	    try {
	        return eventtypeRepository.save(eventtype);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating eventtype", ex);
	    }
	}

	@Override
	public Eventtype updateEventtype(Long id, Eventtype eventtype) {
	    Optional<Eventtype> existingEventtype = eventtypeRepository.findById(id);
	    if (existingEventtype.isPresent()) {
	        eventtype.setId(id);
	        try {
	            return eventtypeRepository.save(eventtype);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating eventtype", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Eventtype not found with id : " + id);
	    }
	}

	@Override
	public void deleteEventtype(Long id) {
	    try {
	        eventtypeRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting eventtype", ex);
	    }
	}


}
