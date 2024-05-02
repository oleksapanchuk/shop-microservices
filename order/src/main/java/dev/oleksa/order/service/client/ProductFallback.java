package dev.oleksa.order.service.client;

import dev.oleksa.order.dto.response.ProductDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductFallback implements ProductFeignClient {

    @Override
    public ResponseEntity<ProductDetailsResponse> fetchProduct(Long productId) {
        return null;
    }
}
