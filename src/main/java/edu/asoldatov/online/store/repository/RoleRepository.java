package edu.asoldatov.online.store.repository;

import edu.asoldatov.online.store.common.UserRoles;
import edu.asoldatov.online.store.mogel.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRoles name);
}

