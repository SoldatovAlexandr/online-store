package edu.asoldatov.online.store.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.asoldatov.online.store.common.AuthType;
import lombok.*;

import java.util.Set;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;

    private String login;

    private String name;

    private AuthType authType;

    private Set<RoleDto> roles;
}
