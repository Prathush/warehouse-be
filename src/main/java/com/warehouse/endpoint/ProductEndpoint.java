package com.warehouse.endpoint;

import com.warehouse.exception.WarehouseException;
import com.warehouse.model.WarehouseConstant;
import com.warehouse.model.request.Product;
import com.warehouse.model.response.SuccessResponse;
import com.warehouse.model.response.WarehouseServiceResponse;
import com.warehouse.service.api.ProductService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Endpoint for adding and fetching Product Information
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/warehouse/v1/product")
@Api(value = "Product Service", tags = {"Product"})
public class ProductEndpoint {
    private final ProductService _productService;

    /**
     * Get Available Product for given product name search
     *
     * @param productName Product Name to be search
     * @return Matching products list for given product name
     * @throws WarehouseException throw product name exception when product name is null or empty
     */
    @ApiOperation(value = "Get Available Product for given product name search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched product information for given productName"),
            @ApiResponse(code = 400, message = "Invalid Request params "),
            @ApiResponse(code = 401, message = "Not Authorized to view the resources"),
            @ApiResponse(code = 404, message = "The resource you where trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal Server error occurred")
    })
    @GetMapping(path = "/getProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchProductInformationByName(
            @ApiParam(value = "productName", example = "TestProduct", required = true)
            @RequestParam final String productName) throws WarehouseException {
        WarehouseServiceResponse searchProductListResponse = WarehouseServiceResponse.<List<Product>>builder()
                .warehouseSearchProductList(_productService.fetchSearchProduct(productName)).build();

        log.debug("Product search successful");

        return new ResponseEntity<>(searchProductListResponse, HttpStatus.OK);
    }

    /**
     * Create Product or Add existing Product to the mentioned box
     *
     * @param product Product detail information
     * @return store product information response
     * @throws WarehouseException thrown when mandatory product information is null or empty
     */
    @ApiOperation(value = "Create Product or Add existing Product to the mentioned box")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created/added the product to the mention box"),
            @ApiResponse(code = 401, message = "Not Authorized to view the resources"),
            @ApiResponse(code = 404, message = "The resource you where trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal Server error occurred")
    })
    @PutMapping(path = "/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@Valid @RequestBody final Product product) throws WarehouseException {
        _productService.storeProduct(product);

        SuccessResponse createProductSuccessResponse = SuccessResponse.builder().status(WarehouseConstant.OK.getString())
                .message(WarehouseConstant.PRODUCT_CREATED.getString()).build();

        WarehouseServiceResponse createProductResponse = WarehouseServiceResponse.<SuccessResponse>builder()
                .warehouseSearchProductList(createProductSuccessResponse).build();

        log.debug("Successful Product created or added to the specified box");

        return new ResponseEntity<>(createProductResponse, HttpStatus.CREATED);
    }
}