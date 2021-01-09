package org.sid.billingservice.web;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BillingRestController {

    private static String token;

    ProductItemRepository productItemRepository;
    ProductItemRestClient productItemRestClient;

    public BillingRestController(ProductItemRepository productItemRepository, ProductItemRestClient productItemRestClient, BillRepository billRepository, CustomerRestClient customerRestClient) {
        this.productItemRepository = productItemRepository;
        this.productItemRestClient = productItemRestClient;
        this.billRepository = billRepository;
        this.customerRestClient = customerRestClient;
    }

    BillRepository billRepository;
    CustomerRestClient customerRestClient;



    public static void setToken(String token) {
        BillingRestController.token = token;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable Long id){

        Bill bill=billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerID(),token);
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi->{
            Product product = productItemRestClient.getProductById(pi.getProductID(),token);
            pi.setProduct(product);
        });

        return bill;
    }

}
