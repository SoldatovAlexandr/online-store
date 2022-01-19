package edu.asoldatov.online.store.service;

import edu.asoldatov.online.store.api.dto.RoleDto;
import edu.asoldatov.online.store.api.dto.UserDto;
import edu.asoldatov.online.store.common.AuthType;
import edu.asoldatov.online.store.common.AuthUrl;
import edu.asoldatov.online.store.mogel.User;
import edu.asoldatov.online.store.repository.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    UserDto find(Long id);

    Page<UserDto> findAllBySpecification(UserSpecification specification, Pageable pageable);

    void delete(Long id);

    User save(User user);

    User findById(Long id);

    Optional<User> findByLoginAndAuthType(String login, AuthType authType);

    User find(String userName);

    RoleDto addAdminRole(Long id);

    RoleDto deleteAdminRole(Long id);
}
