package dev.oleksa.order.controller;

import dev.oleksa.order.dto.OrderDto;
import dev.oleksa.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/v1/orders", produces = {MediaType.APPLICATION_JSON_VALUE})
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/fetch-by-id/{order-id}")
    public ResponseEntity<OrderDto> fetchOrderById(
            @PathVariable(name = "order-id") Long orderId
    ) {
        OrderDto orderDto = orderService.fetchOrderById(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderDto);
    }

}
