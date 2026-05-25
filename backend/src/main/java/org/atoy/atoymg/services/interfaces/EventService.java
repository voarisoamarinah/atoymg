package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Event;
import org.atoy.atoymg.models.dto.EventSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface EventService {
    Page<Event> getAllEvent(Pageable pageable);

    Page<Event> getAllEvent(Pageable pageable, EventSearch object);

    Event getEventById(Long id);

    

    Event createEvent(Event event);

    Event updateEvent(Long id, Event event);

    void deleteEvent(Long id);
    
}
