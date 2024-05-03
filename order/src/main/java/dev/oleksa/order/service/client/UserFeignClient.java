package dev.oleksa.order.service.client;

import dev.oleksa.order.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user", fallback = UserFallback.class)
public interface UserFeignClient {

    @GetMapping("/api/v1/fetch/{user-id}")
    ResponseEntity<UserDto> fetchUser(@PathVariable(name = "user-id") Long userId);

    @GetMapping("/api/v1/by-email/{email}")
    ResponseEntity<UserDto> getByEmail(@PathVariable String email);
}
