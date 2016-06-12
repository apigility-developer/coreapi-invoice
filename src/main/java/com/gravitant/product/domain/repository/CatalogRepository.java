package com.gravitant.product.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.gravitant.product.domain.entity.Catalog;

public interface CatalogRepository extends PagingAndSortingRepository<Catalog, String>, JpaSpecificationExecutor<Catalog> {
}
