package dev.oleksa.order.service.client;

import dev.oleksa.order.dto.response.ProductDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("product")
public interface  ProductFeignClient {

    @GetMapping(value = "/api/v1/fetch/{product-id}", consumes = "application/json")
    ResponseEntity<ProductDetailsResponse> fetchProduct(@PathVariable(name = "product-id") Long productId);

}
