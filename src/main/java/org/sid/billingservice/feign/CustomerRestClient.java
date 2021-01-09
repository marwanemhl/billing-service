package org.sid.billingservice.feign;


import org.sid.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
//@QueryParam(value = "page") int page,@QueryParam(value = "size") int size

@FeignClient(name = "CUSTOMER-SERVICE")
@CrossOrigin("http://localhost:4200")
public interface CustomerRestClient {

    @GetMapping(path = "/customers/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id,@RequestHeader("Authorization") String bearerToken);


}
