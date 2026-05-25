package org.atoy.atoymg.repositories;

import org.atoy.atoymg.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findAllByName(String username);
}
