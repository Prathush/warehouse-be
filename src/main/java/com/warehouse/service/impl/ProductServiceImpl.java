package com.warehouse.service.impl;

import com.warehouse.exception.WarehouseException;
import com.warehouse.jpa.entity.ProductEntity;
import com.warehouse.jpa.service.api.ProductDAOService;
import com.warehouse.model.request.Product;
import com.warehouse.service.api.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Product Service Impl class of warehouse service
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */

@Slf4j
@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDAOService _productDAOService;

    @Override
    public void storeProduct(Product product) throws WarehouseException {
        _productDAOService.saveProductInformation(product);
    }

    @Override
    @Transactional
    public List<Product> fetchSearchProduct(String productName) throws WarehouseException {
        List<ProductEntity> productEntityList = _productDAOService.retrieveProduct(productName);

        if (CollectionUtils.isEmpty(productEntityList)) {
            log.warn("Product not found for given product name");
            throw new WarehouseException("Product not found for given search");
        }

        return productEntityList.stream().map(product -> buildProduct(product)).collect(Collectors.toList());
    }

    private Product buildProduct(ProductEntity productEntity) {
        return Product.builder().name(productEntity.getName())
                .locationList(productEntity.getBoxes().stream().map(box -> box.getName()).collect(
                        Collectors.toList())).build();
    }
}
