package dev.oleksa.order.dto.request;

import dev.oleksa.order.dto.AddressDto;
import dev.oleksa.order.dto.OrderDto;
import dev.oleksa.order.entity.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PurchaseRequest {
    private AddressDto shippingAddress;
    private OrderDto order;
    private Set<OrderItem> orderItems;
}
