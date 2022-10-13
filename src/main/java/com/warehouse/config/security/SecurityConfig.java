package com.warehouse.config.security;

import java.util.Arrays;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Security configuration class for enabling basic authentication of the service
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		/*To enable authentication for every requst of the application*/
		http.csrf()
			.disable()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.httpBasic();

		/*To enable the H2 database database admin console, by default spring security disable it */
		http.headers().frameOptions().disable();

		/*To handle CORS configuration of the application*/
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
		corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
		corsConfiguration.setExposedHeaders(Arrays.asList("*"));

		http.cors().configurationSource(request -> corsConfiguration);
	}
}
