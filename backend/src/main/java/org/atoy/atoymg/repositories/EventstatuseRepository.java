package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Eventstatuse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventstatuseRepository extends JpaRepository<Eventstatuse, Long>, JpaSpecificationExecutor<Eventstatuse> {
}
