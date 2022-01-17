package edu.asoldatov.online.store.api.mapper;

import edu.asoldatov.online.store.api.dto.BasketDto;
import edu.asoldatov.online.store.api.dto.BasketItemDto;
import edu.asoldatov.online.store.mogel.Basket;
import edu.asoldatov.online.store.mogel.BasketItem;
import edu.asoldatov.online.store.mogel.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasketMapper {

    public BasketDto to(Basket basket) {
        return BasketDto.builder()
                .totalAmount(calculateTotalAmount(basket))
                .items(toItems(basket.getItems()))
                .build();
    }

    private List<BasketItemDto> toItems(List<BasketItem> items) {
        return items.stream().map(basketItem -> {
            Product product = basketItem.getProduct();
            return BasketItemDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .ageLimit(product.getAgeLimit())
                    .yearOfPublication(product.getYearOfPublication())
                    .amount(product.getAmount())
                    .author(product.getAuthor())
                    .genre(product.getGenre().getName())
                    .description(product.getDescription())
                    .isActive(product.isActive())
                    .count(basketItem.getCount())
                    .build();
        }).collect(Collectors.toList());
    }

    private BigDecimal calculateTotalAmount(Basket basket) {
        BigDecimal amount = new BigDecimal(0);
        for (BasketItem item : basket.getItems()) {
            amount = amount.add(item.getProduct().getAmount().multiply(new BigDecimal(item.getCount())));
        }
        return amount;
    }
}
