package com.warehouse.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.warehouse.jpa.entity.ProductEntity;

/**
 * Product repository interface for handling box transaction
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
public interface ProductRepository extends JpaRepository<ProductEntity, String>
{
	ProductEntity findByProductName(String productName);

	List<ProductEntity> findByProductNameContainsIgnoreCaseOrderByProductNameAsc(String productName);
}
