package edu.asoldatov.online.store.api.controller;

import edu.asoldatov.online.store.api.dto.RoleDto;
import edu.asoldatov.online.store.api.dto.UserDto;
import edu.asoldatov.online.store.repository.specification.UserSpecification;
import edu.asoldatov.online.store.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto get(final @PathVariable Long id) {
        log.info("Get user by id [{}]", id);
        return userService.find(id);
    }

    @GetMapping("/")
    public Page<UserDto> get(final UserSpecification specification,
                             @PageableDefault(sort = {"login"}, direction = Sort.Direction.DESC, size = 20) final Pageable pageable) {
        log.info("Get users by specification [{}]", specification);
        return userService.findAllBySpecification(specification, pageable);
    }

    @PostMapping("/")
    public UserDto add(final @Valid @RequestBody UserDto userDto) {
        log.info("Add user [{}]", userDto);
        return userService.add(userDto);
    }

    @PutMapping("/{id}")
    public UserDto update(final @Valid @RequestBody UserDto userDto,
                          final @PathVariable Long id) {
        log.info("Update user [{}] by id [{}]", userDto, id);
        return userService.update(userDto, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(final @PathVariable Long id) {
        log.info("Delete user by id [{}]", id);
        userService.delete(id);
    }

    @PostMapping("/{id}/roles/")
    public RoleDto addAdminRole(final @PathVariable Long id) {
        log.info("Add admin role for user [{}]", id);
        return userService.addAdminRole(id);
    }

    @DeleteMapping("/{id}/roles/")
    public RoleDto deleteAdminRole(final @PathVariable Long id) {
        log.info("Delete admin role for user [{}]", id);
        return userService.deleteAdminRole(id);
    }
}
