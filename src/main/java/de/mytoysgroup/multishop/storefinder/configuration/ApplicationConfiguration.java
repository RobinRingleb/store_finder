package de.mytoysgroup.multishop.storefinder.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ApplicationConfiguration {
	
	@Value("${geodatabase.path}")
	private String geoDataBasePath;
	
	@Value("${jsonfile.path}")
	private String jsonFilePath;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	public String getJsonFilePath() {
		return this.jsonFilePath;
	}
	
	public String getGeoDataBasePath() {
		return this.geoDataBasePath;
	}
	
}
