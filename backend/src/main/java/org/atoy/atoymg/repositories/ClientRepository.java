package org.atoy.atoymg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.atoy.atoymg.models.Client;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
}
