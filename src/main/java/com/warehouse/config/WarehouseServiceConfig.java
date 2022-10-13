package com.warehouse.config;

import java.text.SimpleDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson configuration class
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */

@Configuration
public class WarehouseServiceConfig
{
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder()
	{
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_NULL); //Disable's null value attribute in the json response

		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);//Disable's Empty value attribute in the json response

		builder.configure(mapper);
		return builder;
	}
}
