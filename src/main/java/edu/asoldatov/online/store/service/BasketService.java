package edu.asoldatov.online.store.service;

import edu.asoldatov.online.store.api.dto.BasketDto;

public interface BasketService {
    BasketDto findById(Long id);

    BasketDto clear(Long id);

    BasketDto update(Long id, BasketDto basketDto);
}
