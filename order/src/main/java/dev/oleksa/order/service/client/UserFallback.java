package dev.oleksa.order.service.client;

import dev.oleksa.order.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFallback implements UserFeignClient {

    @Override
    public ResponseEntity<UserDto> fetchUser(Long userId) {
        return null;
    }
}
