package edu.asoldatov.online.store.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasketDto {

    private Long id;
    private BigDecimal totalAmount;
    private List<ProductItemDto> products;

}
