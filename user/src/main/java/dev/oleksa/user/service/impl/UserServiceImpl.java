package dev.oleksa.user.service.impl;

import dev.oleksa.user.dto.UserDto;
import dev.oleksa.user.dto.request.SignUpRequest;
import dev.oleksa.user.entity.User;
import dev.oleksa.user.exception.ResourceNotFoundException;
import dev.oleksa.user.exception.UserNotFoundException;
import dev.oleksa.user.mapper.UserMapper;
import dev.oleksa.user.repository.UserRepository;
import dev.oleksa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public Long createUser(SignUpRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        User savedUser = repository.save(user);
        return savedUser.getId();
    }

    @Override
    public UserDto fetchUserById(Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserMapper.mapToUsersDto(user);
    }

    @Override
    public UserDto fetchUserByEmail(String email) {
        User user = repository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email)
        );

        return UserMapper.mapToUsersDto(user);
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        User user = repository.findByEmail(userDto.getEmail()).orElseThrow(
                () -> new UserNotFoundException(userDto.getEmail())
        );

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());

        repository.save(user);

        return true;
    }

    @Override
    public boolean updatePassword(String email, String oldPassword, String newPassword) {
        User user = repository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email)
        );
/*
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            repository.save(user);
            return true;
        }*/
        return false;
    }

    @Override
    public boolean confirmUserAccount(String email) {
        User user = repository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email)
        );
        user.setVerified(true);
        repository.save(user);
        return true;
    }
}
