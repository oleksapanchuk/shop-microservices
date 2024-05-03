package dev.oleksa.order.dto;

public record OrderMsgDto(
        String email,
        String orderTrackingNumber
) {
}
