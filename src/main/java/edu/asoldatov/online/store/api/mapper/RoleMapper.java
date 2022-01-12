package edu.asoldatov.online.store.api.mapper;

import edu.asoldatov.online.store.api.dto.RoleDto;
import edu.asoldatov.online.store.mogel.Role;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    public RoleDto to(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName().name())
                .build();
    }

    public Set<RoleDto> fromSet(Set<Role> roles) {
        return roles.stream().map(this::to).collect(Collectors.toSet());
    }
}
