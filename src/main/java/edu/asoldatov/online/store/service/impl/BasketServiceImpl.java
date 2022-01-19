package edu.asoldatov.online.store.service.impl;

import edu.asoldatov.online.store.api.dto.BasketDto;
import edu.asoldatov.online.store.api.dto.BasketItemDto;
import edu.asoldatov.online.store.api.mapper.BasketMapper;
import edu.asoldatov.online.store.exception.NotFoundException;
import edu.asoldatov.online.store.mogel.Basket;
import edu.asoldatov.online.store.mogel.BasketItem;
import edu.asoldatov.online.store.mogel.Product;
import edu.asoldatov.online.store.repository.BasketItemRepository;
import edu.asoldatov.online.store.repository.BasketRepository;
import edu.asoldatov.online.store.repository.ProductRepository;
import edu.asoldatov.online.store.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;
    private final BasketMapper basketMapper;

    @Override
    public BasketDto findById(Long userId) {
        Basket basket = getBasketByUserId(userId);
        return basketMapper.to(basket);
    }

    @Transactional
    @Override
    public BasketDto clear(Long id) {
        Basket basket = getBasketByUserId(id);
        basketItemRepository.deleteByBasket(basket);
        basket.clear();
        basketRepository.save(basket);
        return basketMapper.to(basket);
    }

    @Transactional
    @Override
    public BasketDto update(Long id, BasketDto basketDto) {
        Map<Long, Long> productCountByIdMap = getProductCountByIdMap(basketDto);
        List<Product> products = productRepository.findAllByIds(productCountByIdMap.keySet());
        Basket basket = getBasketByUserId(id);
        for (Product product : products) {
            BasketItem item = basket.getItems().stream()
                    .filter(basketItem -> basketItem.getProduct().getId().equals(product.getId()))
                    .findFirst().orElseGet(() -> addNew(basket, product));
            item.setCount(productCountByIdMap.get(product.getId()));
            basketItemRepository.save(item);
        }
        basketRepository.save(basket);
        return basketMapper.to(basket);
    }

    private BasketItem addNew(Basket basket, Product product) {
        BasketItem basketItem = BasketItem.builder()
                .basket(basket)
                .product(product)
                .build();
        basket.getItems().add(basketItem);
        return basketItem;
    }

    private Map<Long, Long> getProductCountByIdMap(BasketDto basketDto) {
        Map<Long, Long> productCountByIdMap = new HashMap<>();
        for (BasketItemDto item : basketDto.getItems()) {
            Long id = item.getId();
            Long count = item.getCount();
            if (id != null && count != null && count >= 0) {
                productCountByIdMap.put(id, count);
            }
        }
        return productCountByIdMap;
    }

    private Basket getBasketByUserId(Long userId) {
        return basketRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
    }
}
