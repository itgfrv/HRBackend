package com.gafarov.bastion.controller.auth;

import com.gafarov.bastion.controller.BaseController;
import com.gafarov.bastion.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация пользователя"
    )
    public AuthenticationResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    @Operation(
            summary = "Авторизация пользователя"
    )
    public AuthenticationResponse authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return authenticationService.authenticate(request);
    }
    @PutMapping("/{user_id}")
    public void updatePassword(@PathVariable(name = "user_id") Integer userId, @RequestBody ChangeRequest newPassword){
        authenticationService.updatePassword(userId, newPassword.getNewPassword());
    }

    @PostMapping("/request")
    public ResponseEntity<String> updatePasswordRequest(@RequestParam String userEmail) {
        String response = authenticationService.updatePasswordRequest(userEmail);
        if (response.isEmpty()) {
            return ResponseEntity.ok("Запрос на смену пароля отправлен. Проверьте почту.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/change")
    public ResponseEntity<String> changePasswordRequest(@RequestParam String resetPasswordUUID, @RequestParam String newPassword) {
        String response = authenticationService.changePasswordRequest(resetPasswordUUID, newPassword);
        if (response.isEmpty()) {
            return ResponseEntity.ok("Пароль успешно изменен.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
