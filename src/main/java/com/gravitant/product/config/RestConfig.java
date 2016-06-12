package com.gravitant.product.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Configuration
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class RestConfig {
	
	@Inject private ObjectMapper jacksonObjectMapper;
	
	@PostConstruct
	public void initialize() {
	    jacksonObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	    jacksonObjectMapper.setSerializationInclusion(Include.NON_NULL);
	    jacksonObjectMapper.registerModule(new JodaModule());
	    jacksonObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}	
}