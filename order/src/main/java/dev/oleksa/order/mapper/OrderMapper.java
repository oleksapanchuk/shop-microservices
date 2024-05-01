package dev.oleksa.order.mapper;

import dev.oleksa.order.dto.OrderDto;
import dev.oleksa.order.entity.Order;

public class OrderMapper {

    public static OrderDto mapToOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderTrackingNumber(order.getOrderTrackingNumber())
                .totalQuantity(order.getTotalQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .dateCreated(order.getDateCreated())
                .build();
    }

    public static Order mapToOrder(OrderDto orderDto) {
        return Order.builder()
                .orderTrackingNumber(orderDto.getOrderTrackingNumber())
                .totalQuantity(orderDto.getTotalQuantity())
                .totalPrice(orderDto.getTotalPrice())
                .status(orderDto.getStatus())
                .build();
    }

}
