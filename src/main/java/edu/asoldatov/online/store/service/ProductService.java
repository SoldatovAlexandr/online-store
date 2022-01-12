package edu.asoldatov.online.store.service;

import edu.asoldatov.online.store.api.dto.ProductDto;
import edu.asoldatov.online.store.repository.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDto find(Long id);

    Page<ProductDto> findAllBySpecification(ProductSpecification specification, Pageable pageable);

    ProductDto add(ProductDto productDto);

    ProductDto update(ProductDto productDto, Long id);

    void delete(Long id);

}
