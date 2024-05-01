package dev.oleksa.user.controller;

import dev.oleksa.user.constants.UserConstants;
import dev.oleksa.user.dto.UserDto;
import dev.oleksa.user.dto.request.SignUpRequest;
import dev.oleksa.user.dto.response.ResponseDto;
import dev.oleksa.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/v1/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createUser(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        Long id = userService.createUser(signUpRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(UserConstants.STATUS_201, "User was created. Id: " + id));
    }

    @GetMapping("/fetch/{user-id}")
    public ResponseEntity<UserDto> fetchUser(
            @PathVariable(name = "user-id") Long userId
    ) {

        UserDto user = userService.fetchUserById(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }


}
