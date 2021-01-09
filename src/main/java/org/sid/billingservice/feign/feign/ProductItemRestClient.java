package org.sid.billingservice.feign.feign;


import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.ws.rs.QueryParam;

@FeignClient(name = "INVENTORY-SERVICE")
@CrossOrigin("http://localhost:4200")
public interface ProductItemRestClient {
    @GetMapping("/products")
    PagedModel<Product> pageProducts(@RequestHeader("Authorization") String bearerToken);
    @GetMapping(path = "/products/{id}")
    Product getProductById(@PathVariable(name = "id") Long id,@RequestHeader("Authorization") String bearerToken);
}
