package com.warehouse.model.response;

import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * Success Response class of warehouse service
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Data
@Builder
@ApiModel(description = "Success Response")
public class SuccessResponse
{
	private String status;
	private String message;
}
