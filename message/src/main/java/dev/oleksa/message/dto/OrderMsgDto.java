package dev.oleksa.message.dto;

public record OrderMsgDto(
        String email,
        String orderTrackingNumber
) {
}
