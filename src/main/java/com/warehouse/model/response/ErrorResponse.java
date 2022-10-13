package com.warehouse.model.response;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

/**
 * Error Response class of warehouse service
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Data
@Builder
public class ErrorResponse
{
	private String status;
	private Date timestamp;
	private String message;
	private String description;
}
