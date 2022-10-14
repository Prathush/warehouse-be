package com.warehouse.jpa.repository;

import com.warehouse.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Product repository interface for handling box transaction
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    ProductEntity findByName(String name);

    List<ProductEntity> findByNameContainsIgnoreCaseOrderByNameAsc(String name);
}
