package edu.asoldatov.online.store.api.mapper;

import edu.asoldatov.online.store.api.dto.BasketDto;
import edu.asoldatov.online.store.api.dto.ProductItemDto;
import edu.asoldatov.online.store.mogel.Basket;
import edu.asoldatov.online.store.mogel.Product;
import edu.asoldatov.online.store.mogel.ProductItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasketMapper {

    public BasketDto to(Basket basket) {
        return BasketDto.builder()
                .id(basket.getId())
                .totalAmount(calculateTotalAmount(basket))
                .products(getProducts(basket.getProducts()))
                .build();
    }

    private List<ProductItemDto> getProducts(List<ProductItem> items) {
        List<ProductItemDto> products = new ArrayList<>();//TODO переделать этот ужас
        List<ProductItem> productItems = items.stream()
                .filter(v -> v.getCount() > 0L)
                .sorted((v1, v2) -> (int) (v2.getCount() - v1.getCount()))
                .collect(Collectors.toList());
        for (ProductItem item : productItems) {
            Product product = item.getProduct();
            products.add(ProductItemDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .ageLimit(product.getAgeLimit())
                    .yearOfPublication(product.getYearOfPublication())
                    .amount(product.getAmount())
                    .author(product.getAuthor())
                    .genre(product.getGenre().getName())
                    .description(product.getDescription())
                    .isActive(product.isActive())
                    .count(item.getCount())
                    .build());
        }
        return products;
    }

    private BigDecimal calculateTotalAmount(Basket basket) {
        BigDecimal amount = new BigDecimal(0);
        for (ProductItem item : basket.getProducts()) {
            amount = amount.add(item.getProduct().getAmount().multiply(new BigDecimal(item.getCount())));
        }
        return amount;
    }
}
