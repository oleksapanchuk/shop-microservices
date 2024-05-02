package dev.oleksa.user.service.impl;

import dev.oleksa.user.entity.User;
import dev.oleksa.user.exception.ResourceNotFoundException;
import dev.oleksa.user.repository.UserRepository;
import dev.oleksa.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    private final UserRepository userRepository;
//    private final JwtService jwtService;
    private final JavaMailSender emailSender;

    @Override
    public boolean sendConfirmationEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email)
        );

//        String token = jwtService.generateToken(user, false); // TODO: implement this method
        String token = "some_token";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Confirm your account");
            message.setFrom(from);
            message.setTo(email);
            message.setText("Confirm your account by clicking on the link below: \n" +
                    "http://localhost:8080/api/users/confirm-account?token=" + token);
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Some Subject");
            message.setFrom(from);
            message.setTo(to);
            message.setText("Hello World \n Spring Boot Email");
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return true;
    }
}
