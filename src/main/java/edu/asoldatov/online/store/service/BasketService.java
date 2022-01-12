package edu.asoldatov.online.store.service;

import edu.asoldatov.online.store.api.dto.BasketDto;

public interface BasketService {
    BasketDto findById(Long id);

    BasketDto addProduct(Long id, Long productId);

    BasketDto deleteProduct(Long id, Long productId);

    BasketDto clear(Long id);
}
