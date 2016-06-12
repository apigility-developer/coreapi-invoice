package com.gravitant.product.domain;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.gravitant.product.messaging.CatalogCreatedMessageProducer;
import com.gravitant.product.service.dto.CatalogDto;

@Component
public class CatalogCreatedEventListener {
	
	@Inject private CatalogCreatedMessageProducer messageProducer;
	
	@TransactionalEventListener
	public void onCatalogCreated(CatalogDto catalogDto) throws Exception {
		this.messageProducer.send(catalogDto);
	}
	
}
