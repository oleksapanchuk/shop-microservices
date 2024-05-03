package dev.oleksa.user.controller;

import dev.oleksa.user.constants.UserConstants;
import dev.oleksa.user.dto.UserDto;
import dev.oleksa.user.dto.request.PasswordUpdateRequest;
import dev.oleksa.user.dto.request.SignUpRequest;
import dev.oleksa.user.dto.response.ResponseDto;
import dev.oleksa.user.service.EmailService;
import dev.oleksa.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

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

    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDto> getByEmail(
            @PathVariable String email
    ) {

        UserDto user = userService.fetchUserByEmail(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto user) {
        boolean isUpdated = userService.updateUser(user);
        return isUpdated
                ?
                new ResponseEntity<>(Collections.singletonMap("message", "User data is updated successfully"), HttpStatus.OK)
                :
                new ResponseEntity<>(Collections.singletonMap("message", "Failed to update user data"), HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/send-email-confirmation")
    public ResponseEntity<ResponseDto> sendEmailConfirmation(
            @RequestParam String email
    ) {
        boolean isSent = emailService.sendConfirmationEmail(email); // TODO: add token to the request

        if (isSent) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstants.STATUS_200, UserConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(UserConstants.STATUS_417, UserConstants.MESSAGE_417_DELETE));
        }
    }

    @PatchMapping("/confirm-account")
    public ResponseEntity<ResponseDto> confirmAccount(@RequestParam String token) {

        boolean isVerified = userService.confirmUserAccount(token);

        if (isVerified) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstants.STATUS_200, UserConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(UserConstants.STATUS_417, UserConstants.MESSAGE_417_DELETE));
        }
    }

}
