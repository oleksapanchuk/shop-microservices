package dev.oleksa.order.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import dev.oleksa.order.dto.OrderDto;
import dev.oleksa.order.dto.request.PaymentInfoRequest;
import dev.oleksa.order.dto.request.PurchaseRequest;
import dev.oleksa.order.dto.response.OrderDetailsResponse;
import dev.oleksa.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
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

    @GetMapping("/fetch-by-tracking-number/{order-tracking-number}")
    public ResponseEntity<OrderDto> fetchOrderByTrackingNumber(
            @PathVariable(name = "order-tracking-number") String orderTrackingNumber
    ) {
        OrderDto orderDto = orderService.fetchOrderByTrackingNumber(orderTrackingNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderDto);
    }

    @GetMapping("/fetch-by-email/{email}")
    public ResponseEntity<Page<OrderDto>> fetchOrderByUsername(
            @PathVariable(name = "email") String email,
            Pageable pageable
    ) {
        Page<OrderDto> orderDtoPage = orderService.fetchOrdersByEmail(email, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderDtoPage);
    }

    @GetMapping("/fetch-order-details/{order-id}")
    public ResponseEntity<OrderDetailsResponse> fetchOrderDetailsById(
            @PathVariable(name = "order-id") Long orderId
    ) {
        OrderDetailsResponse orderDetailsResponse = orderService.fetchOrderDetails(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderDetailsResponse);
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderDto> placeOrder(
            @RequestBody PurchaseRequest purchase
    ) {
        OrderDto orderDto = orderService.createOrder(purchase);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderDto);
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfo) throws StripeException {

        log.info("PaymentInfoRequest.amount: {}", paymentInfo.getAmount());

        PaymentIntent paymentIntent = orderService.createPaymentIntent(paymentInfo);

        return ResponseEntity.ok(paymentIntent.toJson());
    }

}
