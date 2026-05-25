package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Event;
import org.atoy.atoymg.models.dto.EventSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.EventRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.EventService;
import org.atoy.atoymg.specification.EventSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultEventService implements EventService {
	private final EventRepository eventRepository;

	public DefaultEventService(EventRepository eventRepository) {
	   this.eventRepository = eventRepository;
	}

	@Override
	public Page<Event> getAllEvent(Pageable pageable) {
	    try {
	        return eventRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving event", ex);
	    }
	}

	@Override
	public Page<Event> getAllEvent(Pageable pageable, EventSearch object) {
	    try {
	        Specification<Event> spec=EventSpecification.filter(object);
	        return eventRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving event", ex);
	    }
	}

	@Override
	public Event getEventById(Long id) {
	    Optional<Event> event = eventRepository.findById(id);
	    if (event.isPresent()) {
	        return event.get();
	    } else {
	        throw new ResourceNotFoundException("Event not found with id : " + id);
	    }
	}

 

	@Override
	public Event createEvent(Event event) {
	    try {
	        return eventRepository.save(event);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating event", ex);
	    }
	}

	@Override
	public Event updateEvent(Long id, Event event) {
	    Optional<Event> existingEvent = eventRepository.findById(id);
	    if (existingEvent.isPresent()) {
	        event.setId(id);
	        try {
	            return eventRepository.save(event);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating event", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Event not found with id : " + id);
	    }
	}

	@Override
	public void deleteEvent(Long id) {
	    try {
	        eventRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting event", ex);
	    }
	}


}
