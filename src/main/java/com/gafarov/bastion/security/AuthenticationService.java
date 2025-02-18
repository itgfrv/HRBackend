package com.gafarov.bastion.security;

import com.gafarov.bastion.controller.auth.AuthenticationRequest;
import com.gafarov.bastion.controller.auth.AuthenticationResponse;
import com.gafarov.bastion.controller.auth.RegisterRequest;
import com.gafarov.bastion.entity.PasswordReset;
import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.entity.user.UserStatus;
import com.gafarov.bastion.repository.PasswordResetRepository;
import com.gafarov.bastion.service.EmailService;
import com.gafarov.bastion.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final PasswordResetRepository passwordResetRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .activity(Activity.REGISTERED)
                .userStatus(UserStatus.REJECT)
                .createdDate(LocalDateTime.now())
                .lastActivityDate(LocalDateTime.now())
                .isViewed(false)
                .build();
        var savedUser = userService.addNewUser(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userService.findUserByEmail(request.email());
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public void updatePassword(Integer userId, String newPassword) {
        User user = userService.findUserById(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);
        return;
    }

    public String updatePasswordRequest(String userEmail) {
        try {
            User user = userService.findUserByEmail(userEmail);
            PasswordReset passwordReset = new PasswordReset();
            passwordReset.setUser(user);
            passwordReset.setExpiresAt(LocalDateTime.now().plusMinutes(15));
            passwordReset.setIsUsed(false);
            passwordReset = passwordResetRepository.save(passwordReset);
            emailService.sendChangePasswordRequest(userEmail, passwordReset.getId().toString());
        } catch (MessagingException me) {
            return "Ошибка отправки сообщения на почту. Повторите запос позднее.";
        } catch (Exception e) {
            return "Email не найден в системе";
        }
        return "";
    }

    public String changePasswordRequest(String resetPasswordUUID, String newPassword) {
        var passwordReset = passwordResetRepository.findById(UUID.fromString(resetPasswordUUID)).orElseThrow();
        if (passwordReset.getExpiresAt().isAfter(LocalDateTime.now()) && !passwordReset.getIsUsed()) {
            updatePassword(passwordReset.getUser().getId(), newPassword);
            passwordReset.setIsUsed(true);
            passwordResetRepository.save(passwordReset);
            return "";
        } else {
            return "Запрос истек, повторите смену пароля снова";
        }
    }
}
