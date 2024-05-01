package dev.oleksa.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@Schema(description = "Order details response object")
public class OrderDetailsResponse {

    @Schema(description = "Order tracking number")
    private String orderTrackingNumber;

    @Schema(description = "Order total price")
    private Integer totalPrice;

    @Schema(description = "Order status")
    private String status;

    @Schema(description = "Order date created")
    private String dateCreated;

    @Schema(description = "Order shipping address")
    private String shippingAddress;

    @Schema(description = "Order items")
    private Set<OrderItemResponse> orderItems;
}
