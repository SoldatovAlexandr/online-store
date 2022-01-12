package edu.asoldatov.online.store.repository;

import edu.asoldatov.online.store.mogel.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BasketRepository extends JpaRepository<Basket, Long>, JpaSpecificationExecutor<Basket> {
}
