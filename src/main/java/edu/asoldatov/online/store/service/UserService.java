package edu.asoldatov.online.store.service;

import edu.asoldatov.online.store.api.dto.UserDto;
import edu.asoldatov.online.store.mogel.User;
import edu.asoldatov.online.store.repository.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto find(Long id);

    Page<UserDto> findAllBySpecification(UserSpecification specification, Pageable pageable);

    UserDto add(UserDto userDto);

    UserDto update(UserDto userDto, Long id);

    void delete(Long id);

    User findById(Long id);

    UserDto find(String userName);
}
