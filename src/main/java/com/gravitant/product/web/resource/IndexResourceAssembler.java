package com.gravitant.product.web.resource;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Component;

@Component
public class IndexResourceAssembler {
   private final RelProvider relProvider;
   private final EntityLinks entityLinks;
   
   @Autowired
   public IndexResourceAssembler(RelProvider relProvider, EntityLinks entityLinks) {
      this.relProvider = relProvider;
      this.entityLinks = entityLinks;
   }

   public IndexResource buildIndex() {
      final List<Link> links = asList(
            entityLinks.linkToCollectionResource(CatalogResource.class).withRel( relProvider.getCollectionResourceRelFor(CatalogResource.class) )
      );
      final List<Link> enumerationLinks = asList(
      );
      final IndexResource resource = new IndexResource("coreapi-product", "Product API description placeholder");
      resource.add(links);
      resource.add(enumerationLinks);
      return resource;
   }
}