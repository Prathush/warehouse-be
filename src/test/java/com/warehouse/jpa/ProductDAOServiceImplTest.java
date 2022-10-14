package com.warehouse.jpa;

import com.warehouse.exception.WarehouseException;
import com.warehouse.jpa.entity.BoxEntity;
import com.warehouse.jpa.entity.ProductEntity;
import com.warehouse.jpa.repository.BoxRepository;
import com.warehouse.jpa.repository.ProductRepository;
import com.warehouse.jpa.service.impl.ProductDAOServiceImpl;
import com.warehouse.model.request.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductDAOServiceImplTest {
    @InjectMocks
    ProductDAOServiceImpl _productDAOServiceImpl;

    @Mock
    ProductRepository _productRepository;

    @Mock
    BoxRepository _boxRepository;

    @Test
    public void shouldSaveProductWithBoxInformation() throws WarehouseException {
        when(_productRepository.findByName(anyString())).thenReturn(null);
        when(_boxRepository.findByNameAndAvailableCapacityNot(anyString(), anyInt())).thenReturn(getBoxEntityDetails());

        _productDAOServiceImpl.saveProductInformation(getProductDetails());

        verify(_productRepository).save(any(ProductEntity.class));
        verify(_boxRepository).save(any(BoxEntity.class));
    }

    @Test
    public void shouldMapExistingProductToNewBoxLocation() throws WarehouseException {
        ProductEntity productEntity = getProductEntityDetails();

        when(_productRepository.findByName(anyString())).thenReturn(productEntity);
        when(_boxRepository.findByNameAndAvailableCapacityNot(anyString(), anyInt())).thenReturn(getBoxEntityDetails());

        _productDAOServiceImpl.saveProductInformation(getProductDetails());

        verify(_productRepository).save(productEntity);
    }

    @Test(expected = WarehouseException.class)
    public void shouldNotMapProductToBoxWhenAvailableCapacityOfBoxLimitZero() throws WarehouseException {
        ProductEntity productEntity = getProductEntityDetails();

        when(_productRepository.findByName(anyString())).thenReturn(productEntity);
        when(_boxRepository.findByNameAndAvailableCapacityNot(anyString(), anyInt())).thenReturn(null);

        _productDAOServiceImpl.saveProductInformation(getProductDetails());
    }

    @Test
    public void shouldGetProductInformationByAvailableProductNameSearch() {
        ProductEntity productEntity = getProductEntityDetails();

        BoxEntity boxEntity = new BoxEntity();
        boxEntity.setId("1");
        boxEntity.setName("testBoxName");
        boxEntity.setCapacity(3);
        boxEntity.setAvailableCapacity(2);

        Set<BoxEntity> boxEntitySet = new HashSet<>();
        boxEntitySet.add(boxEntity);
        productEntity.setBoxes(boxEntitySet);

        when(_productRepository.findByNameContainsIgnoreCaseOrderByNameAsc(anyString()))
                .thenReturn(Arrays.asList(productEntity));

        List<ProductEntity> availableProducts = _productDAOServiceImpl.retrieveProduct("testProductName");
        ProductEntity searchProductResult = availableProducts.get(0);
        BoxEntity boxInformation = searchProductResult.getBoxes().stream().findFirst().get();

        Assert.assertNotNull(availableProducts); //Product Information Assertion
        Assert.assertEquals("1", searchProductResult.getId());
        Assert.assertEquals("testProductName", searchProductResult.getName());

        Assert.assertNotNull(searchProductResult.getBoxes()); //Box Information Assertion
        Assert.assertNotNull("1", boxInformation.getId());
        Assert.assertNotNull("testBoxName", boxInformation.getName());
        Assert.assertNotNull("3", boxInformation.getCapacity());
        Assert.assertNotNull("2", boxInformation.getAvailableCapacity());
    }

    @Test
    public void shouldNotGetProductInformationByUnAvailableProductNameSearch() {
        when(_productRepository.findByNameContainsIgnoreCaseOrderByNameAsc(anyString()))
                .thenReturn(null);

        List<ProductEntity> unAvailableProducts =
                _productDAOServiceImpl.retrieveProduct("testProductName");

        Assert.assertNull(unAvailableProducts);
    }

    private Product getProductDetails() {
        return Product.builder().name("testProductName").boxName("testBoxName").build();
    }

    private ProductEntity getProductEntityDetails() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId("1");
        productEntity.setName("testProductName");

        return productEntity;
    }

    private BoxEntity getBoxEntityDetails() {
        BoxEntity boxEntity = new BoxEntity();
        boxEntity.setName("testBoxName");
        boxEntity.setAvailableCapacity(1);
        boxEntity.setCapacity(3);

        return boxEntity;
    }
}
