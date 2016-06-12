package com.gravitant.product.service.facade;

import org.springframework.data.domain.Page;
import com.gravitant.product.service.dto.CatalogDto;
import com.gravitant.product.service.dto.CatalogSearchDto;

public interface CatalogService {
    public Page<CatalogDto> searchCatalogs(CatalogSearchDto searchDto);
    public CatalogDto getCatalog(String catalogId);
	public CatalogDto createCatalog(CatalogDto catalog);
    public void updateCatalog(CatalogDto catalog);
    public void deleteCatalog(String catalogId);
}
