package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Eventvendor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventvendorRepository extends JpaRepository<Eventvendor, Long>, JpaSpecificationExecutor<Eventvendor> {
}
