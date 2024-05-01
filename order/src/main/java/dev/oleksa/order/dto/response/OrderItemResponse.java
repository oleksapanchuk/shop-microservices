package dev.oleksa.order.dto.response;

import dev.oleksa.order.dto.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Order item response object")
public class OrderItemResponse {

    @Schema(description = "Order item id")
    private Long id;

    @Schema(description = "Order item unit price")
    private BigDecimal unitPrice;

    @Schema(description = "Order item quantity")
    private int quantity;

    @Schema(description = "Product")
    private ProductDto product;

    @Schema(description = "Order id")
    private Long orderId;
}
