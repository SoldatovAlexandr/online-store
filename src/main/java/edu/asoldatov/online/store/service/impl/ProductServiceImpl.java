package edu.asoldatov.online.store.service.impl;

import edu.asoldatov.online.store.api.dto.ProductDto;
import edu.asoldatov.online.store.api.mapper.ProductMapper;
import edu.asoldatov.online.store.exception.NotFoundException;
import edu.asoldatov.online.store.mogel.Genre;
import edu.asoldatov.online.store.mogel.Product;
import edu.asoldatov.online.store.repository.GenreRepository;
import edu.asoldatov.online.store.repository.ProductRepository;
import edu.asoldatov.online.store.repository.specification.ProductSpecification;
import edu.asoldatov.online.store.service.ProductService;
import edu.asoldatov.online.store.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final TimeService timeService;
    private final GenreRepository genreRepository;

    @Override
    public ProductDto find(Long id) {
        Product product = findById(id);
        return productMapper.to(product);
    }

    @Override
    public Page<ProductDto> findAllBySpecification(ProductSpecification specification, Pageable pageable) {
        Page<Product> products = productRepository.findAll(specification, pageable);
        return products.map(productMapper::to);
    }

    @Override
    public ProductDto add(ProductDto productDto) {
        Product product = productMapper.to(productDto);
        Genre genre = genreRepository.findByName(productDto.getGenre());
        product.setGenre(genre);
        product.setCreatedAt(timeService.now());
        product.setActive(true);
        productRepository.save(product);
        return productMapper.to(product);
    }

    @Transactional
    @Override
    public ProductDto update(ProductDto productDto, Long id) {
        Product product = findById(id);
        product.setAmount(productDto.getAmount());
        product.setAuthor(productDto.getAuthor());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setAgeLimit(productDto.getAgeLimit());
        productRepository.save(product);
        return productMapper.to(product);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Product product = findById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    private Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
