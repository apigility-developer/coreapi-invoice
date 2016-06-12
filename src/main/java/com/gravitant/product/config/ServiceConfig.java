
package com.gravitant.product.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ServiceConfig {
	
	@Bean
    public ModelMapper mappingService() {
        ModelMapper modelMapper = new ModelMapper();
        org.modelmapper.config.Configuration mappingConfig = modelMapper.getConfiguration();
        
        mappingConfig.setMatchingStrategy(MatchingStrategies.LOOSE);
        mappingConfig.setFieldMatchingEnabled(true);
        mappingConfig.setImplicitMappingEnabled(true);
        
        return modelMapper;
    }
}
