package com.handmadecrafts.backend.service;

import com.handmadecrafts.backend.dto.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    @Data
    @AllArgsConstructor
    private static class OtpData {
        private String otp;
        private LocalDateTime expiryTime;
    }

    @Data
    @AllArgsConstructor
    private static class RegistrationTempData {
        private RegisterRequest registerRequest;
        private String otp;
        private LocalDateTime expiryTime;
    }

    private final Map<String, RegistrationTempData> registrationCache = new ConcurrentHashMap<>();
    private final Map<String, OtpData> forgotPasswordCache = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> verifiedResetEmails = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateOtp() {
        int otpVal = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(otpVal);
    }

    public void storeRegistrationOtp(String email, RegisterRequest request, String otp) {
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);
        registrationCache.put(email, new RegistrationTempData(request, otp, expiry));
    }

    public RegisterRequest getAndValidateRegistrationRequest(String email, String otp) {
        RegistrationTempData tempData = registrationCache.get(email);
        if (tempData == null) {
            return null;
        }

        if (LocalDateTime.now().isAfter(tempData.getExpiryTime())) {
            registrationCache.remove(email);
            return null;
        }

        if (!tempData.getOtp().equals(otp)) {
            return null;
        }

        registrationCache.remove(email);
        return tempData.getRegisterRequest();
    }

    public void storeForgotPasswordOtp(String email, String otp) {
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);
        forgotPasswordCache.put(email, new OtpData(otp, expiry));
    }

    public boolean validateForgotPasswordOtp(String email, String otp) {
        OtpData otpData = forgotPasswordCache.get(email);
        if (otpData == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(otpData.getExpiryTime())) {
            forgotPasswordCache.remove(email);
            return false;
        }

        return otpData.getOtp().equals(otp);
    }

    public void clearForgotPasswordOtp(String email) {
        forgotPasswordCache.remove(email);
    }

    public void markEmailAsVerifiedForReset(String email) {
        verifiedResetEmails.put(email, LocalDateTime.now().plusMinutes(5));
    }

    public boolean isEmailVerifiedForReset(String email) {
        LocalDateTime expiry = verifiedResetEmails.get(email);
        if (expiry == null) {
            return false;
        }
        if (LocalDateTime.now().isAfter(expiry)) {
            verifiedResetEmails.remove(email);
            return false;
        }
        return true;
    }

    public void clearEmailVerifiedForReset(String email) {
        verifiedResetEmails.remove(email);
    }
}
