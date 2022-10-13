package com.warehouse.service.api;

import java.util.List;
import com.warehouse.exception.WarehouseException;
import com.warehouse.model.request.Product;

public interface ProductService
{
	void storeProduct(Product product) throws WarehouseException;
	List<Product> fetchSearchProduct(String productName) throws WarehouseException;
}
