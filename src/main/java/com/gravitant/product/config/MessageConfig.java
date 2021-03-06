package com.gravitant.product.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class MessageConfig {
	
	@Inject private ObjectMapper objectMapper;
	
	@Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter jackson2Converter = new MappingJackson2MessageConverter();
        jackson2Converter.setObjectMapper(this.objectMapper);
        return jackson2Converter;
    }

}
