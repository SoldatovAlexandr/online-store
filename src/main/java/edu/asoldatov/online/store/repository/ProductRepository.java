package edu.asoldatov.online.store.repository;

import edu.asoldatov.online.store.mogel.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "SELECT * FROM product WHERE id IN (:ids)", nativeQuery = true)
    List<Product> findAllByIds(Collection<Long> ids);
}
