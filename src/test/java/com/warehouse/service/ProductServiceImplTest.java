package com.warehouse.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.*;

import com.warehouse.exception.WarehouseException;
import com.warehouse.jpa.entity.BoxEntity;
import com.warehouse.jpa.entity.ProductEntity;
import com.warehouse.jpa.service.api.ProductDAOService;
import com.warehouse.model.request.Product;
import com.warehouse.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest
{
	@InjectMocks
    ProductServiceImpl _productServiceImpl;

	@Mock
    ProductDAOService _productDAOService;

	@Test
	public void shouldStoreProductInformation() throws WarehouseException
	{
		_productServiceImpl.storeProduct(new Product());

		verify(_productDAOService).saveProductInformation(any(Product.class));
	}

	@Test
	public void shouldGetSearchProductList() throws WarehouseException
	{
		Set<BoxEntity> boxEntitySet = new HashSet<>();

		boxEntitySet.add(BoxEntity.builder().name("testBoxName").capacity(3).availableCapacity(2).build());

		ProductEntity productEntity = new ProductEntity();
		productEntity.setName("testProductName");

		productEntity.setBoxes(boxEntitySet);

		when(_productDAOService.retrieveProduct(anyString())).thenReturn(Arrays.asList(productEntity));

		List<Product> productList = _productServiceImpl.fetchSearchProduct("testProductName");
		Product product = productList.get(0);

		Assert.assertNotNull(productList);
		Assert.assertEquals("testProductName",product.getName());
		Assert.assertEquals("testBoxName",product.getLocationList().get(0));
	}

	@Test(expected = WarehouseException.class)
	public void shouldThrowExceptionWhenSearchProductNameNotFound() throws WarehouseException
	{
		when(_productDAOService.retrieveProduct(anyString())).thenReturn(null);
		_productServiceImpl.fetchSearchProduct("testProductName");
	}
}
