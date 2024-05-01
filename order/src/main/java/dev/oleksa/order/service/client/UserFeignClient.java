package dev.oleksa.order.service.client;

import dev.oleksa.order.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user")
public interface UserFeignClient {

    @GetMapping("/api/v1/users/fetch/{user-id}")
    ResponseEntity<UserDto> fetchUser(@PathVariable(name = "user-id") Long userId);
}
