package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Organizer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizerRepository extends JpaRepository<Organizer, Long>, JpaSpecificationExecutor<Organizer> {
}
