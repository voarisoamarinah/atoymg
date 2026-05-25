package org.atoy.atoymg.repositories;

import org.atoy.atoymg.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findAllByName(String name);
    UserEntity findUserByName(String name);
}
