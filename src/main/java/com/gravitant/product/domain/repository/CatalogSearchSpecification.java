package com.gravitant.product.domain.repository;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import com.gravitant.product.domain.entity.Catalog;

public class CatalogSearchSpecification implements Specification<Catalog> {


	public CatalogSearchSpecification() {
	}
	
	public Predicate toPredicate(Root<Catalog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicateList = null;
	 
	    predicateList = new ArrayList<Predicate>();
	    
	    
	    Predicate[] predicates = new Predicate[predicateList.size()];
	    predicateList.toArray(predicates);
	    
	    return builder.and(predicates);
	}

}