package com.gravitant.product.web.resource;

import javax.inject.Inject;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.gravitant.product.service.dto.CatalogDto;
import com.gravitant.product.web.controller.CatalogController;

@Component
public class CatalogResourceAssembler extends ResourceAssemblerSupport<CatalogDto, CatalogResource> {
	
	@Inject private EntityLinks entityLinks;
	
	public CatalogResourceAssembler() {
		super(CatalogController.class, CatalogResource.class);
	}

    public CatalogResource toResource(CatalogDto catalogDto) {
        CatalogResource catalogResource = null;
        LinkBuilder catalogLinkBuilder = this.entityLinks.linkForSingleResource(CatalogResource.class, catalogDto.getId());
        
        catalogResource = new CatalogResource();
        catalogResource.setName(catalogDto.getName());
        catalogResource.setDescription(catalogDto.getDescription());
        catalogResource.setDisplayName(catalogDto.getDisplayName());
        catalogResource.add(catalogLinkBuilder.withSelfRel());
	   	return catalogResource;
	}
	
}
