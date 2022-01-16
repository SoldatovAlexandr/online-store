package edu.asoldatov.online.store.service.impl;

import edu.asoldatov.online.store.api.dto.RoleDto;
import edu.asoldatov.online.store.api.dto.UserDto;
import edu.asoldatov.online.store.api.mapper.RoleMapper;
import edu.asoldatov.online.store.api.mapper.UserMapper;
import edu.asoldatov.online.store.common.UserRoles;
import edu.asoldatov.online.store.exception.NotFoundException;
import edu.asoldatov.online.store.mogel.Basket;
import edu.asoldatov.online.store.mogel.Role;
import edu.asoldatov.online.store.mogel.User;
import edu.asoldatov.online.store.repository.BasketRepository;
import edu.asoldatov.online.store.repository.RoleRepository;
import edu.asoldatov.online.store.repository.UserRepository;
import edu.asoldatov.online.store.repository.specification.UserSpecification;
import edu.asoldatov.online.store.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BasketRepository basketRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto find(Long id) {
        User user = findById(id);
        return userMapper.to(user);
    }

    @Override
    public Page<UserDto> findAllBySpecification(UserSpecification specification, Pageable pageable) {
        Page<User> users = userRepository.findAll(specification, pageable);
        return users.map(userMapper::to);
    }

    @Transactional
    @Override
    public UserDto add(UserDto userDto) {
        User user = userMapper.to(userDto);
        user.setRoles(Set.of(findRoleByName(UserRoles.ROLE_USER)));
        userRepository.save(user);
        initBasket(user);
        return userMapper.to(user);
    }

    @Transactional
    @Override
    public UserDto update(UserDto userDto, Long id) {
        User user = findById(id);
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return userMapper.to(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(findById(id));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id [%s] not found", id)));
    }

    @Override
    public UserDto find(String userName) {
        User user = userRepository.findUserByLogin(userName)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Пользователь не найден.");
                });
        return userMapper.to(user);
    }

    @Transactional
    @Override
    public RoleDto addAdminRole(Long id) {
        User user = findById(id);
        Role admin = findRoleByName(UserRoles.ROLE_ADMIN);
        user.getRoles().add(admin);
        userRepository.save(user);
        return roleMapper.to(admin);
    }

    @Transactional
    @Override
    public RoleDto deleteAdminRole(Long id) {
        User user = findById(id);
        Role admin = findRoleByName(UserRoles.ROLE_ADMIN);
        user.getRoles().remove(admin);
        userRepository.save(user);
        return roleMapper.to(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> {
                    log.error("User with login {} not found", login);
                    throw new UsernameNotFoundException("Пользователь не найден.");
                });
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName().name())));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    private void initBasket(User user) {
        Basket basket = Basket.builder()
                .user(user)
                .build();
        basketRepository.save(basket);
    }

    private Role findRoleByName(UserRoles name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Role with name [%s]", UserRoles.ROLE_USER.name())));
    }
}
