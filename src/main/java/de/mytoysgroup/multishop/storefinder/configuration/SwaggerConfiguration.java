package de.mytoysgroup.multishop.storefinder.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This service uses Swagger to document its REST API and this is the configuration
 *
 * @author Robin Ringleb (2017)
 */
@Configuration
@EnableSwagger2
@Profile({ "default", "dev", "stable", "ops" })
public class SwaggerConfiguration {

	/**
	 * Define the group name and the rest urls which swagger should show in the interface.
	 *
	 * @return a <code>Docket</code> with the information to give to the swagger ui.
	 */
	@Bean
	public Docket newsApi() {
		// Show only controller in package de.mytoysgroup.multishop.logging.controller
		// and an URL path that fits the given regex
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("de.mytoysgroup.multishop.shopfinder.controller"))
				.paths(regex(".*")).build();
	}

	/**
	 * Initialise the swagger ui with the service parameters.
	 *
	 * @return a <code>ApiInfo</code> with the information to give to the swagger ui.
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Storefinder Service")
				.description(
						"This is a store_finder Service for Multishop-platform.")
				.build();
	}

}
