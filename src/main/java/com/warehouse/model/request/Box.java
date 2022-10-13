package com.warehouse.model.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Box input request model class
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Box Details")
public class Box
{
	private String id;

	@ApiModelProperty(notes = "Name of the Box", name = "box", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(notes = "Available Capacity of Box", name = "box", required = true)
	@NotNull
	private int capacity;

	private List<String> availableBoxList;
}
