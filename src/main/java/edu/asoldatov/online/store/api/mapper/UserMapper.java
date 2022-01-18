package edu.asoldatov.online.store.api.mapper;

import edu.asoldatov.online.store.api.dto.UserDto;
import edu.asoldatov.online.store.mogel.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserDto to(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .roles(roleMapper.fromSet(user.getRoles()))
                .authType(user.getAuthType())
                .build();
    }
}
