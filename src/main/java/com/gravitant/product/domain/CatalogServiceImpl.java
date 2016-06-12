package com.gravitant.product.domain;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gravitant.product.domain.entity.Catalog;
import com.gravitant.product.domain.repository.CatalogRepository;
import com.gravitant.product.domain.repository.CatalogSearchSpecification;
import com.gravitant.product.service.dto.CatalogDto;
import com.gravitant.product.service.dto.DtoNotFoundException;
import com.gravitant.product.service.dto.DtoNotUniqueException;
import com.gravitant.product.service.facade.CatalogService;
import com.gravitant.product.service.dto.CatalogSearchDto;

@Service
@Transactional(readOnly=true)
public class CatalogServiceImpl implements CatalogService {

	@Value("${spring.application.name}")
	private String applicationName;
	
	@Inject private ApplicationEventPublisher eventPublisher;
	@Inject private CatalogRepository catalogRepository;
	@Inject private ModelMapper conversionService;
	
	public Page<CatalogDto> searchCatalogs(CatalogSearchDto searchDto) {
		PageImpl<CatalogDto> pagedDtoCatalogs = null;
		List<CatalogDto> catalogListDto = null;
		Page<Catalog> pagedCatalogs = null;
		CatalogSearchSpecification catalogSearchSpecification = null;
		
		catalogSearchSpecification = this.conversionService.map(searchDto, CatalogSearchSpecification.class);
		
		pagedCatalogs = this.catalogRepository.findAll(catalogSearchSpecification, searchDto.getPageable());
		
		if (pagedCatalogs != null) {
			catalogListDto = pagedCatalogs.getContent().stream()
			          .map(catalog -> this.conversionService.map(catalog, CatalogDto.class)).collect(Collectors.toList());
			pagedDtoCatalogs = new PageImpl<CatalogDto>(catalogListDto, 
													   new PageRequest(pagedCatalogs.getNumber(), 
															   		   pagedCatalogs.getSize(), 
															   		   pagedCatalogs.getSort()), 
													   pagedCatalogs.getTotalElements());
		}
		return pagedDtoCatalogs;
	}

	public CatalogDto getCatalog(String catalogId) {
		return this.conversionService.map(this.retrieveCatalogById(catalogId), CatalogDto.class);
	}
	
	@Transactional(readOnly=false)
	public CatalogDto createCatalog(CatalogDto catalogDto) {
		CatalogDto newCatalogDto = null;
		Catalog catalog = null;
		Catalog newCatalog = null;
		
		catalog = this.conversionService.map(catalogDto, Catalog.class);
        newCatalog = this.catalogRepository.save(catalog);
        newCatalogDto = this.conversionService.map(newCatalog, CatalogDto.class);
			
        this.eventPublisher.publishEvent(newCatalogDto);
		
		return newCatalogDto;
	}
	
	@Transactional(readOnly=false)
	public void updateCatalog(CatalogDto catalogDto) {
		Catalog updatedCatalog = null;
		Catalog catalog = null;
		
		catalog = this.conversionService.map(catalogDto, Catalog.class);
		updatedCatalog = this.retrieveCatalogById(catalog.getId());
		if (updatedCatalog != null) {
			if (catalog.getDisplayName() != null) {
				updatedCatalog.setDisplayName(catalog.getDisplayName());
			}
			if (catalog.getName() != null) {
				updatedCatalog.setName(catalog.getName());
			}
			if (catalog.getDescription() != null) {
				updatedCatalog.setDescription(catalog.getDescription());
			}
		}
	}
	
	@Transactional(readOnly=false)
	public void deleteCatalog(String catalogId) {
		this.catalogRepository.delete(this.retrieveCatalogById(catalogId));
	}
	

	private Catalog retrieveCatalogById(String catalogId) {
		Catalog catalog = this.catalogRepository.findOne(catalogId);
		if (catalog == null) {
			throw new DtoNotFoundException(Catalog.class, catalogId);
		}
		return catalog;
	}
	
}
