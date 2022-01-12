package edu.asoldatov.online.store.api.mapper;

import edu.asoldatov.online.store.api.dto.UserDto;
import edu.asoldatov.online.store.mogel.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    private final PasswordEncoder passwordEncoder;

    public User to(UserDto userDto) {
        return User.builder()
                .login(userDto.getLogin())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
    }

    public UserDto to(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .roles(roleMapper.fromSet(user.getRoles()))
                .build();
    }
}
