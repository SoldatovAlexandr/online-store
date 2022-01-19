package edu.asoldatov.online.store.repository;

import edu.asoldatov.online.store.common.AuthType;
import edu.asoldatov.online.store.mogel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findUserByLoginAndAuthType(String login, AuthType authType);

    Optional<User> findUserByLogin(String userName);
}
