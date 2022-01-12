package edu.asoldatov.online.store.api.controller;

import edu.asoldatov.online.store.api.dto.ProductDto;
import edu.asoldatov.online.store.repository.specification.ProductSpecification;
import edu.asoldatov.online.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto get(final @PathVariable Long id) {
        log.info("Get product by id [{}]", id);
        return productService.find(id);
    }

    @GetMapping("/")
    public Page<ProductDto> get(final ProductSpecification specification,
                             @PageableDefault(sort = {"name"}, direction = Sort.Direction.DESC, size = 20) final Pageable pageable) {
        log.info("Get products by specification [{}]", specification);
        return productService.findAllBySpecification(specification, pageable);
    }

    @PostMapping("/")
    public ProductDto add(final @Valid @RequestBody ProductDto productDto) {
        log.info("Add product [{}]", productDto);
        return productService.add(productDto);
    }

    @PutMapping("/{id}")
    public ProductDto update(final @Valid @RequestBody ProductDto productDto,
                          final @PathVariable Long id) {
        log.info("Update product [{}] by id [{}]", productDto, id);
        return productService.update(productDto, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(final @PathVariable Long id) {
        log.info("Delete product by id [{}]", id);
        productService.delete(id);
    }
}
