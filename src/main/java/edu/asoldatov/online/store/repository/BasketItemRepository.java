package edu.asoldatov.online.store.repository;

import edu.asoldatov.online.store.mogel.Basket;
import edu.asoldatov.online.store.mogel.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    void deleteByBasket(Basket basket);
}
