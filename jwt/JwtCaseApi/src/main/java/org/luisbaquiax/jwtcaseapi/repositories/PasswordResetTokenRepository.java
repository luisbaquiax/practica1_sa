package org.luisbaquiax.jwtcaseapi.repositories;

import org.luisbaquiax.jwtcaseapi.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByTokenHash(String tokenHash);
}
