package edu.asoldatov.online.store.repository;

import edu.asoldatov.online.store.mogel.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long>, JpaSpecificationExecutor<Basket> {

    @Query(value = "SELECT * FROM basket WHERE user_id=:userId", nativeQuery = true)
    Optional<Basket> findByUserId(Long userId);

}
