package com.warehouse.jpa.service.impl;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.warehouse.exception.WarehouseException;
import com.warehouse.jpa.repository.BoxRepository;
import com.warehouse.jpa.repository.ProductRepository;
import com.warehouse.model.request.Product;
import org.springframework.stereotype.Repository;
import com.warehouse.jpa.entity.BoxEntity;
import com.warehouse.jpa.entity.ProductEntity;
import com.warehouse.jpa.service.api.ProductDAOService;

/**
 * ProductDAOServiceImpl class  for executing transaction
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Repository
public class ProductDAOServiceImpl implements ProductDAOService
{
	@Resource
    ProductRepository _productRepository;

	@Resource
    BoxRepository _boxRepository;

	@Transactional
	public void saveProductInformation(Product product) throws WarehouseException
	{
		ProductEntity productEntity;
		productEntity = _productRepository.findByProductName(product.getName());

		if (Objects.isNull(productEntity))
		{
			productEntity = new ProductEntity();
			productEntity.setName(product.getName());
		}

		BoxEntity boxEntity = _boxRepository.findByBoxNameAndAndBoxAvailableCapacityNot(product.getBoxName(),0);

		if (Objects.nonNull(boxEntity))
		{
			productEntity.getBoxes().add(boxEntity);
			_productRepository.save(productEntity);
			reduceBoxCapacity(boxEntity);
		}
		else
		{
			throw new WarehouseException("Product is not added due to incapacity of Box");
		}

	}

	private void reduceBoxCapacity(BoxEntity boxEntity)
	{
		boxEntity.setAvailableCapacity(boxEntity.getAvailableCapacity() -1);
		_boxRepository.save(boxEntity);
	}

	public List<ProductEntity> retrieveProduct(String productName)
	{
		return _productRepository.findByProductNameContainsIgnoreCaseOrderByProductNameAsc(productName);
	}
}
