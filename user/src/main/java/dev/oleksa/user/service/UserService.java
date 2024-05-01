package dev.oleksa.user.service;

import dev.oleksa.user.dto.UserDto;
import dev.oleksa.user.dto.request.SignUpRequest;
import dev.oleksa.user.entity.User;

public interface UserService {

    Long createUser(SignUpRequest request);

    UserDto fetchUserById(Long id);

    UserDto fetchUserByEmail(String email);

    boolean updateUser(String email, UserDto userDto);

    boolean updatePassword(String email, String oldPassword, String newPassword);

    boolean confirmUserAccount(String email);

}
