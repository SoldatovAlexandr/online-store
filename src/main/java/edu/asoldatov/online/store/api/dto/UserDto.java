package edu.asoldatov.online.store.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "required.password.error")
    @Size(max = 64, message = "long.password.error")
    private String password;

    @Email(message = "incorrect.login.error")
    @NotBlank(message = "required.login.error")
    @Size(max = 64, message = "long.login.error")
    private String login;

    private Set<RoleDto> roles;
}
