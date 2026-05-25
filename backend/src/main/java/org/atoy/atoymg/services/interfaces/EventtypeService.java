package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Eventtype;
import org.atoy.atoymg.models.dto.EventtypeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface EventtypeService {
    Page<Eventtype> getAllEventtype(Pageable pageable);

    Page<Eventtype> getAllEventtype(Pageable pageable, EventtypeSearch object);

    Eventtype getEventtypeById(Long id);

    

    Eventtype createEventtype(Eventtype eventtype);

    Eventtype updateEventtype(Long id, Eventtype eventtype);

    void deleteEventtype(Long id);
    
}
