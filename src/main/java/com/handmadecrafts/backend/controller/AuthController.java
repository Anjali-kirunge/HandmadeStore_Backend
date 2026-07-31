package com.handmadecrafts.backend.controller;

import com.handmadecrafts.backend.dto.*;
import com.handmadecrafts.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP sent successfully to your email. Please verify.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-registration-otp")
    public ResponseEntity<Map<String, String>> verifyRegistrationOtp(@Valid @RequestBody VerifyRegistrationOtpRequest request) {
        authService.verifyRegistrationOtp(request.getEmail(), request.getOtp());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Registration successful. You can now login.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP sent successfully to reset password.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-forgot-password-otp")
    public ResponseEntity<Map<String, String>> verifyForgotPasswordOtp(@Valid @RequestBody VerifyForgotPasswordOtpRequest request) {
        authService.verifyForgotPasswordOtp(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP verified successfully. You may now reset your password.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Password reset successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Password changed successfully.");
        return ResponseEntity.ok(response);
    }
}
