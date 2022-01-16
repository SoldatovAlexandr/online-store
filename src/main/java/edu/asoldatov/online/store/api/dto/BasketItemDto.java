package edu.asoldatov.online.store.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasketItemDto {

    private Long id;

    private String name;
    private BigDecimal amount;
    private String description;
    private Integer ageLimit;
    private String author;
    private String genre;
    private Integer yearOfPublication;
    private boolean isActive;
    private Long count;
}
