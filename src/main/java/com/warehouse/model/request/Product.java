package com.warehouse.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Product input request model class
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Product Details")
public class Product {
    @ApiModelProperty(notes = "product to be created", name = "product", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(notes = "box tagged to product", name = "box", required = true)
    @NotNull
    private String boxName;

    private List<String> locationList;
}
