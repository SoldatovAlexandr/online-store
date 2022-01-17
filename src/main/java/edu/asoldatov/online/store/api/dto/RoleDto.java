package edu.asoldatov.online.store.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    private Long id;

    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9 -_]*$", message = "incorrect.name.error")
    @NotBlank(message = "required.name.error")
    @Size(max = 64, message = "long.name.error")
    private String name;
}
