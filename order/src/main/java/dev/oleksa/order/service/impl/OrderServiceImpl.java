package dev.oleksa.order.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import dev.oleksa.order.constants.OrderStatus;
import dev.oleksa.order.dto.OrderDto;
import dev.oleksa.order.dto.ProductDto;
import dev.oleksa.order.dto.request.PaymentInfoRequest;
import dev.oleksa.order.dto.request.PurchaseRequest;
import dev.oleksa.order.dto.response.OrderDetailsResponse;
import dev.oleksa.order.dto.response.OrderItemResponse;
import dev.oleksa.order.entity.Address;
import dev.oleksa.order.entity.Order;
import dev.oleksa.order.entity.OrderItem;
import dev.oleksa.order.exception.ResourceNotFoundException;
import dev.oleksa.order.mapper.AddressMapper;
import dev.oleksa.order.mapper.OrderMapper;
import dev.oleksa.order.repository.OrderRepository;
import dev.oleksa.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static dev.oleksa.order.mapper.OrderMapper.mapToOrderDto;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            @Value("${stripe.keys.secret}") String secretKey) {
        this.orderRepository = orderRepository;

        // initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
    }

    @Override
    public OrderDto createOrder(PurchaseRequest purchase) {
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase request is null");
        }

        Order order = OrderMapper.mapToOrder(purchase.getOrder());
        order.setOrderTrackingNumber(generateOrderTrackingNumber());

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::addItem);


        // TODO
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new ResourceNotFoundException("User", "username", username)
//        );
//        order.setUser(user);

        Address shippingAddress = AddressMapper.mapToAddress(purchase.getShippingAddress());
        order.setShippingAddress(shippingAddress);
        order.setStatus(OrderStatus.ACCEPTED.name());
        orderRepository.save(order);

        return mapToOrderDto(order);
    }

    @Override
    public OrderDto fetchOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order", "id", orderId)
        );
        return mapToOrderDto(order);
    }

    @Override
    public OrderDto fetchOrderByTrackingNumber(String orderTrackingNumber) {
        Order order = orderRepository.findByOrderTrackingNumber(orderTrackingNumber).orElseThrow(
                () -> new ResourceNotFoundException("Order", "tracking number", orderTrackingNumber)
        );

        return mapToOrderDto(order);
    }

    @Override
    public Page<OrderDto> fetchOrdersByEmail(String email, Pageable pageable) {
        Page<Order> ordersPage = orderRepository.findByUserEmailOrderByDateCreatedDesc(email, pageable);

        List<OrderDto> orderDtoList = ordersPage.getContent().stream()
                .map(OrderMapper::mapToOrderDto)
                .toList();

        return new PageImpl<>(orderDtoList, pageable, ordersPage.getTotalElements());
    }

    @Override
    public OrderDetailsResponse fetchOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order", "id", orderId)
        );

        return OrderDetailsResponse.builder()
                .orderTrackingNumber(order.getOrderTrackingNumber())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .dateCreated(order.getDateCreated())
                .shippingAddress(getShippingAddressString(order.getShippingAddress()))
                .orderItems(getOrderItemResponses(order.getOrderItems()))
                .build();
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfo) throws StripeException {

        List<String> paymentMethodTypes = List.of("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "PanShop Purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

    private Set<OrderItemResponse> getOrderItemResponses(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::mapToOrderItemResponse)
                .collect(Collectors.toSet());
    }

    private static String getShippingAddressString(Address shippingAddress) {
        return shippingAddress.getStreet() + ", " +
                shippingAddress.getCity() + ", " +
                shippingAddress.getState() + ", " +
                shippingAddress.getCountry() + ", " +
                shippingAddress.getZipCode();
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {
//        Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(
//                () -> new ResourceNotFoundException("Product", "id", orderItem.getProductId())
//        );
//        ProductDto productDto = mapToProductDto(product);
        ProductDto productDto = ProductDto.builder().build(); // TODO

        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .product(productDto)
                .orderId(orderItem.getOrder().getId())
                .build();
    }
}
