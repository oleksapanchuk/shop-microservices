package dev.oleksa.message.functions;

import dev.oleksa.message.dto.OrderMsgDto;
import dev.oleksa.message.dto.UserMsgDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MessageFunctions {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    @Bean
    public Function<UserMsgDto, Boolean> emailConfirmation() {
        return userMsgDto -> {
            log.info("Sending email confirmation with the details : " + userMsgDto.toString());

            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setSubject("Confirm your account");
                message.setFrom(from);
                message.setTo(userMsgDto.email());
                message.setText("Confirm your account by clicking on the link below: \n" +
                        "http://localhost:8080/api/users/confirm-account?token=" + userMsgDto.token());
                emailSender.send(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }

            return true;
        };
    }

    @Bean
    public Function<OrderMsgDto, Boolean> orderDetails() {
        return orderMsgDto -> {
            log.info("Sending email with the details : " + orderMsgDto.toString());

            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setSubject("Orders details");
                message.setFrom(from);
                message.setTo(orderMsgDto.email());
                message.setText("Orders details " + orderMsgDto.orderTrackingNumber());
                emailSender.send(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }

            return true;
        };
    }
}
