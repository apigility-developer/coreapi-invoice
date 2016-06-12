package com.gravitant.product.messaging;

import javax.inject.Inject;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import com.gravitant.product.service.dto.CatalogDto;

@Component
public class CatalogCreatedMessageProducer {

	@Inject private RabbitMessagingTemplate rabbitTemplate;
	@Inject private MappingJackson2MessageConverter messageConverter;
	
	public void send(CatalogDto dataObject) {
		this.rabbitTemplate.setMessageConverter(this.messageConverter);
		this.rabbitTemplate.convertAndSend("exchange.event", "catalog", dataObject);
	}
	
}
