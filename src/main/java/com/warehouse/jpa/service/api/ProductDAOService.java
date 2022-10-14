package com.warehouse.jpa.service.api;

import com.warehouse.exception.WarehouseException;
import com.warehouse.jpa.entity.ProductEntity;
import com.warehouse.model.request.Product;

import java.util.List;

public interface ProductDAOService {
    void saveProductInformation(Product product) throws WarehouseException;

    List<ProductEntity> retrieveProduct(String name);
}
