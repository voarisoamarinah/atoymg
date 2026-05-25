package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Eventtype;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventtypeRepository extends JpaRepository<Eventtype, Long>, JpaSpecificationExecutor<Eventtype> {
}
