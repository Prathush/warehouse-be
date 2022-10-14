package com.warehouse.exception;

/**
 * Custom Exception handler of the warehouse service
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
public class WarehouseException extends Exception {
    public WarehouseException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
