package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Eventstatuse;
import org.atoy.atoymg.models.dto.EventstatuseSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface EventstatuseService {
    Page<Eventstatuse> getAllEventstatuse(Pageable pageable);

    Page<Eventstatuse> getAllEventstatuse(Pageable pageable, EventstatuseSearch object);

    Eventstatuse getEventstatuseById(Long id);

    

    Eventstatuse createEventstatuse(Eventstatuse eventstatuse);

    Eventstatuse updateEventstatuse(Long id, Eventstatuse eventstatuse);

    void deleteEventstatuse(Long id);
    
}
