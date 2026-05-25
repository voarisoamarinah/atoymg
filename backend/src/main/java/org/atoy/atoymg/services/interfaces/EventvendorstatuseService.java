package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Eventvendorstatuse;
import org.atoy.atoymg.models.dto.EventvendorstatuseSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface EventvendorstatuseService {
    Page<Eventvendorstatuse> getAllEventvendorstatuse(Pageable pageable);

    Page<Eventvendorstatuse> getAllEventvendorstatuse(Pageable pageable, EventvendorstatuseSearch object);

    Eventvendorstatuse getEventvendorstatuseById(Long id);

    

    Eventvendorstatuse createEventvendorstatuse(Eventvendorstatuse eventvendorstatuse);

    Eventvendorstatuse updateEventvendorstatuse(Long id, Eventvendorstatuse eventvendorstatuse);

    void deleteEventvendorstatuse(Long id);
    
}
