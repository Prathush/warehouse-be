package com.warehouse.endpoint;

import com.warehouse.model.request.Product;
import com.warehouse.service.api.ProductService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductEndpoint.class)
public class ProductEndpointTest {
    @MockBean
    ProductService _productService;
    HttpHeaders httpHeaders;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        httpHeaders = new HttpHeaders() {{
            String auth = "Demo" + ":" + "Demo123";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    @Test
    public void shouldReturnOkWithValidContext() throws Exception {
        when(_productService.fetchSearchProduct(anyString())).thenReturn(getProductList());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/warehouse/v1/product/getProducts").param("productName", "testProductName")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .headers(httpHeaders);

        MvcResult mockResponse = mockMvc.perform(requestBuilder).andReturn();

        Assert.assertNotNull(mockResponse);
        Assert.assertEquals(200, mockResponse.getResponse().getStatus());
    }

    @Test
    public void shouldReturnBadRequestWithoutProductNameParam() throws Exception {
        when(_productService.fetchSearchProduct(anyString())).thenReturn(getProductList());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/warehouse/v1/product/getProducts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .headers(httpHeaders);

        MvcResult mockResponse = mockMvc.perform(requestBuilder).andReturn();

        Assert.assertNotNull(mockResponse);
        Assert.assertEquals(400, mockResponse.getResponse().getStatus());
    }

    @Test
    public void shouldThrowUnAuthorizedExceptionForInvalidCredentials() throws Exception {
        when(_productService.fetchSearchProduct(anyString())).thenReturn(getProductList());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/warehouse/v1/product/getProducts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mockResponse = mockMvc.perform(requestBuilder).andReturn();

        Assert.assertNotNull(mockResponse);
        Assert.assertEquals(401, mockResponse.getResponse().getStatus());
    }

    @Test
    public void shouldAddProduct() throws Exception {
        String productDetails = "{\n"
                + "\t\"name\": \"testProductName\",\n"
                + "\t\"boxName\": \"testBoxName\"\n"
                + "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/warehouse/v1/product/addProduct")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .headers(httpHeaders)
                .content(productDetails);

        MvcResult mockResponse = mockMvc.perform(requestBuilder).andReturn();

        Assert.assertNotNull(mockResponse);
        Assert.assertEquals(201, mockResponse.getResponse().getStatus());
    }

    private List<Product> getProductList() {
        return Arrays
                .asList(Product.builder().name("testProductName").locationList(Arrays.asList("testBoxName1,testBoxName2"))
                        .build());
    }
}
