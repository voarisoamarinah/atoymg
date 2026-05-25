package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Organizer;
import org.atoy.atoymg.models.dto.OrganizerSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface OrganizerService {
    Page<Organizer> getAllOrganizer(Pageable pageable);

    Page<Organizer> getAllOrganizer(Pageable pageable, OrganizerSearch object);

    Organizer getOrganizerById(Long id);

    

    Organizer createOrganizer(Organizer organizer);

    Organizer updateOrganizer(Long id, Organizer organizer);

    void deleteOrganizer(Long id);
    
}
