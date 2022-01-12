package edu.asoldatov.online.store.api.controller;

import edu.asoldatov.online.store.api.dto.BasketDto;
import edu.asoldatov.online.store.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/{id}")
    public BasketDto get(final @PathVariable Long id) {
        log.info("Get basket by id [{}]", id);
        return basketService.findById(id);
    }

    @PutMapping("/{id}/add/{productId}")
    public BasketDto addProduct(final @PathVariable Long id,
                                final @PathVariable Long productId) {
        log.info("Add basket by userId [{}]", id);
        return basketService.addProduct(id, productId);
    }

    @PutMapping("/{id}/delete/{productId}")
    public BasketDto deleteProduct(final @PathVariable Long id,
                                   final @PathVariable Long productId) {
        log.info("Delete basket by userId [{}]", id);
        return basketService.deleteProduct(id, productId);
    }

    @DeleteMapping("/{id}")
    public BasketDto clear(final @PathVariable Long id) {
        log.info("Clear basket by userId [{}]", id);
        return basketService.clear(id);
    }
}
