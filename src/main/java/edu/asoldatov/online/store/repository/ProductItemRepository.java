package edu.asoldatov.online.store.repository;

import edu.asoldatov.online.store.mogel.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long>, JpaSpecificationExecutor<ProductItem> {

}
