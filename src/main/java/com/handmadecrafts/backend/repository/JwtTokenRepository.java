package com.handmadecrafts.backend.repository;

import com.handmadecrafts.backend.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Integer> {
    Optional<JwtToken> findByToken(String token);
    void deleteByUserUserId(Integer userId);
}
