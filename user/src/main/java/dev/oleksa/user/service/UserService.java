package dev.oleksa.user.service;

import dev.oleksa.user.dto.UserDto;
import dev.oleksa.user.dto.request.SignUpRequest;

public interface UserService {

    Long createUser(SignUpRequest request);

    UserDto fetchUserById(Long id);

    UserDto fetchUserByEmail(String email);

    boolean updateUser(UserDto userDto);

    boolean confirmUserAccount(String email);

}
