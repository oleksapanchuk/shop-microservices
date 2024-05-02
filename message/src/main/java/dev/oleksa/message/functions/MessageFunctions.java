package dev.oleksa.message.functions;

import dev.oleksa.message.dto.OrderMsgDto;
import dev.oleksa.message.dto.UserMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Configuration
public class MessageFunctions {

    @Bean
    public Function<UserMsgDto,Boolean> emailConfirmation() {
        return userMsgDto -> {
            log.info("Sending email confirmation with the details : " +  userMsgDto.toString());
            return true;
        };
    }

    @Bean
    public Function<OrderMsgDto,Boolean> orderDetails() {
        return orderMsgDto -> {
            log.info("Sending email with the details : " +  orderMsgDto.toString());
            return true;
        };
    }
}
