package edu.asoldatov.online.store.api.mapper;

import edu.asoldatov.online.store.api.dto.ProductDto;
import edu.asoldatov.online.store.mogel.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto to(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .ageLimit(product.getAgeLimit())
                .yearOfPublication(product.getYearOfPublication())
                .amount(product.getAmount())
                .author(product.getAuthor())
                .genre(product.getGenre().getName())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .isActive(product.isActive())
                .image(product.getImage().getId())
                .build();
    }

    public Product to(ProductDto product) {
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .ageLimit(product.getAgeLimit())
                .yearOfPublication(product.getYearOfPublication())
                .amount(product.getAmount())
                .author(product.getAuthor())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .isActive(product.isActive())
                .build();
    }
}
