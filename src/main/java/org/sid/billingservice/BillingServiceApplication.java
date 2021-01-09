package org.sid.billingservice;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.sid.billingservice.sec.CreateToken;
import org.sid.billingservice.web.BillingRestController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;


@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }



    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerRestClient customerRestClient,
            ProductItemRestClient productItemRestClient, RepositoryRestConfiguration restConfiguration){
        restConfiguration.exposeIdsFor(Bill.class);
        return args -> {

                CreateToken test=new CreateToken();
            System.out.printf(test.getTemporary_access_token());
                Customer customer = customerRestClient.getCustomerById(1L,"Bearer "+test.getTemporary_access_token());
                Bill bill1=billRepository.save(new Bill(null,new Date(),null,null,customer.getId()));

                PagedModel<Product> productPagedModel=productItemRestClient.pageProducts("Bearer "+test.getTemporary_access_token());
                productPagedModel.forEach(p->{
                    ProductItem productItem=new ProductItem();
                    productItem.setPrice(p.getPrice());
                    productItem.setQuantity(1+new Random().nextInt(100));
                    productItem.setBill(bill1);
                    productItem.setProductID(p.getId());
                    productItemRepository.save(productItem);

                });



        };
    }

}
