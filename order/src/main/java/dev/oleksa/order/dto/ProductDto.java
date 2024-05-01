package dev.oleksa.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Integer unitsInStock;
    private String imageUrl;
    private Boolean active;
}
