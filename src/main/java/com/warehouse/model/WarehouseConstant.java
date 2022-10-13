package com.warehouse.model;

/**
 * Constant class for the warehouse service application
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
public enum WarehouseConstant
{
    OK("ok"), //Success code of the application
    KO("ko"), //Failure code of the application
    BOX_CREATED("Successfully created the box with specified capacity"),
    BOX_ALREADY_EXIT("Box already exit"),
    PRODUCT_CREATED("Product created"),
    BOXES_WITH_AVAILABLE_CAPACITY_NOT_FOUND("Box with Available capacity not found");

    private final String constants;

    WarehouseConstant(final String constants) {
        this.constants = constants;
    }

    public String getString() {
        return constants;
    }

}
