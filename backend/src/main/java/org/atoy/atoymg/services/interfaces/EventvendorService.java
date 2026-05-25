package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Eventvendor;
import org.atoy.atoymg.models.dto.EventvendorSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface EventvendorService {
    Page<Eventvendor> getAllEventvendor(Pageable pageable);

    Page<Eventvendor> getAllEventvendor(Pageable pageable, EventvendorSearch object);

    Eventvendor getEventvendorById(Long id);

    

    Eventvendor createEventvendor(Eventvendor eventvendor);

    Eventvendor updateEventvendor(Long id, Eventvendor eventvendor);

    void deleteEventvendor(Long id);
    
}
