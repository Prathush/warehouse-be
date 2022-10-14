package com.warehouse.service.api;

import com.warehouse.exception.WarehouseException;
import com.warehouse.model.request.Product;

import java.util.List;

public interface ProductService {
    void storeProduct(Product product) throws WarehouseException;

    List<Product> fetchSearchProduct(String productName) throws WarehouseException;
}
