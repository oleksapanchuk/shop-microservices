package dev.oleksa.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private Long id;
    private Long userId;
    private String orderTrackingNumber;
    private Integer totalQuantity;
    private Integer totalPrice;
    private String status;
    private String dateCreated;
}
