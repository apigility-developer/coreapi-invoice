package com.gravitant.product.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gravitant.product.service.dto.CatalogDto;
import com.gravitant.product.service.facade.CatalogService;
import com.gravitant.product.web.resource.CatalogResource;
import com.gravitant.product.web.resource.CatalogResourceAssembler;
import com.gravitant.product.service.dto.CatalogSearchDto;

@RestController
@RequestMapping(value="/catalogs")
@ExposesResourceFor(CatalogResource.class)
public class CatalogController {
	
	@Inject private CatalogService catalogService;
	@Inject private CatalogResourceAssembler catalogResourceAssembler;

	@RequestMapping(method=RequestMethod.GET)
	public HttpEntity<PagedResources<CatalogResource>> searchCatalogs(
			@PageableDefault(page = 0, size = 10) Pageable pageable) {
		
		PagedResources<CatalogResource> catalogPagedResources = null;
		Page<CatalogDto> pagedCatalogs = null;
		PageMetadata pagedMetadata = null;
		List<CatalogResource> catalogResources = null;

		CatalogSearchDto catalogSearch = new CatalogSearchDto();
		catalogSearch.setPageable(pageable);
		
		pagedCatalogs = this.catalogService.searchCatalogs(catalogSearch);
		catalogResources = this.catalogResourceAssembler.toResources(pagedCatalogs.getContent());
		
		pagedMetadata = this.getPageMetadata(pagedCatalogs);
		catalogPagedResources = new PagedResources<CatalogResource>(catalogResources, pagedMetadata);
		
		return new ResponseEntity<PagedResources<CatalogResource>>(catalogPagedResources, HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST)
	public HttpEntity<Void> createCatalog(@RequestBody @Valid CatalogDto catalogDto) {
		HttpHeaders headers = null;
		CatalogDto catalog = null;
		
		catalog = this.catalogService.createCatalog(catalogDto);
		headers = new HttpHeaders();
		headers.setLocation(linkTo(CatalogController.class).slash(catalog.getId()).toUri());
		
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public HttpEntity<CatalogResource> getCatalog(@PathVariable("id") String catalogId) {
        CatalogResource catalogResource = null;
        CatalogDto catalog = null;

        catalog = this.catalogService.getCatalog(catalogId);
        catalogResource = this.catalogResourceAssembler.toResource(catalog);

        return new ResponseEntity<CatalogResource>(catalogResource, HttpStatus.OK);
    }


	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCatalog(@PathVariable("id") String identifier, @RequestBody CatalogDto catalogDto) {
		catalogDto.setId(identifier);
		this.catalogService.updateCatalog(catalogDto);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCatalog(@PathVariable("id") String catalogId) {
		this.catalogService.deleteCatalog(catalogId);
	}
	

	private PageMetadata getPageMetadata(Page<CatalogDto> catalogPageDto) {
		PageMetadata pageMetadata = null;
		Integer pageNumber = catalogPageDto.getNumber();
		Integer pageSize = catalogPageDto.getSize();
		Integer totalPages = catalogPageDto.getTotalPages();
		Long totalCount = catalogPageDto.getTotalElements();
		
		pageMetadata = new PageMetadata(pageSize, pageNumber, totalCount, totalPages);
		
		return pageMetadata;
	}
}
