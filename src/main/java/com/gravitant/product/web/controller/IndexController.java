package com.gravitant.product.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gravitant.product.web.resource.IndexResource;
import com.gravitant.product.web.resource.IndexResourceAssembler;

@RestController
@RequestMapping("/")
public class IndexController {
	
	private final IndexResourceAssembler indexResourceAssembler;
	   
	@Autowired
	public IndexController(IndexResourceAssembler indexResourceAssembler) {
		this.indexResourceAssembler = indexResourceAssembler;
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<IndexResource> index() {
      return ResponseEntity.ok(indexResourceAssembler.buildIndex());
	}
}
