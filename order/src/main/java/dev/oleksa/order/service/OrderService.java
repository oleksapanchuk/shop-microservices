package dev.oleksa.order.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import dev.oleksa.order.dto.OrderDto;
import dev.oleksa.order.dto.request.PaymentInfoRequest;
import dev.oleksa.order.dto.request.PurchaseRequest;
import dev.oleksa.order.dto.response.OrderDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto createOrder(PurchaseRequest purchase);

    OrderDto fetchOrderById(Long orderId);

    OrderDto fetchOrderByTrackingNumber(String orderTrackingNumber);

    Page<OrderDto> fetchOrdersByEmail(String email, Pageable pageable);

    Page<OrderDto> fetchOrdersByTrackingNumber(String trackingNumber);

    OrderDetailsResponse fetchOrderDetails(Long orderId);

    PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfo) throws StripeException;

    boolean updateOrderStatus(Long orderId, String orderStatus);
}
