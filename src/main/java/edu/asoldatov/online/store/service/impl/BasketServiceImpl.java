package edu.asoldatov.online.store.service.impl;

import edu.asoldatov.online.store.api.dto.BasketDto;
import edu.asoldatov.online.store.api.mapper.BasketMapper;
import edu.asoldatov.online.store.exception.NotFoundException;
import edu.asoldatov.online.store.mogel.Basket;
import edu.asoldatov.online.store.mogel.Product;
import edu.asoldatov.online.store.mogel.ProductItem;
import edu.asoldatov.online.store.repository.BasketRepository;
import edu.asoldatov.online.store.repository.ProductItemRepository;
import edu.asoldatov.online.store.repository.ProductRepository;
import edu.asoldatov.online.store.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final BasketMapper basketMapper;

    @Override
    public BasketDto findById(Long id) {
        Basket basket = getBasketById(id);
        return basketMapper.to(basket);
    }

    @Transactional
    @Override
    public BasketDto addProduct(Long id, Long productId) {
        Basket basket = getBasketById(id);
        Product product = getProductById(productId);
        ProductItem productItem = getByProduct(basket, product);
        if (productItem == null) {
            productItem = ProductItem.builder()
                    .basket(basket)
                    .product(product)
                    .count(1L)
                    .build();
            basket.add(productItem);
        } else {
            productItem.add();
        }
        productItemRepository.save(productItem);
        basketRepository.save(basket);
        return basketMapper.to(basket);
    }

    @Transactional
    @Override
    public BasketDto deleteProduct(Long id, Long productId) {
        Basket basket = getBasketById(id);
        Product product = getProductById(productId);
        ProductItem productItem = getByProduct(basket, product);
        if (productItem == null) {
            throw new NotFoundException();
        } else {
            if (productItem.getCount() > 0L) {
                productItem.remove();
            }
        }
        productItemRepository.save(productItem);
        basketRepository.save(basket);
        return basketMapper.to(basket);
    }

    @Transactional
    @Override
    public BasketDto clear(Long id) {
        Basket basket = getBasketById(id);
        productItemRepository.deleteAll(basket.getProducts());
        basketRepository.save(basket);
        return basketMapper.to(basket);
    }

    private Basket getBasketById(Long id) {
        return basketRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    private ProductItem getByProduct(Basket basket, Product product) {
        for (ProductItem item : basket.getProducts()) {
            if (item.getProduct().equals(product)) {
                return item;
            }
        }
        return null;
    }
}
