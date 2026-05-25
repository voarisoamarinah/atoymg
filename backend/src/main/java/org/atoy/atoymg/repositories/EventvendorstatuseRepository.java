package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Eventvendorstatuse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventvendorstatuseRepository extends JpaRepository<Eventvendorstatuse, Long>, JpaSpecificationExecutor<Eventvendorstatuse> {
}
