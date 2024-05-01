package dev.oleksa.product.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductDetailsRequest {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Integer unitsInStock;
    private String imageUrl;
    private Boolean active;
    private List<Long> categoryIds;
}
