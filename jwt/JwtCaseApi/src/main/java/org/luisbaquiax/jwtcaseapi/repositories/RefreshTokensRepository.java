package org.luisbaquiax.jwtcaseapi.repositories;

import org.luisbaquiax.jwtcaseapi.models.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokensRepository extends JpaRepository<RefreshTokens, Long> {

    Optional<RefreshTokens> findTopByUsuarioUsernameOrderByCreatedAtDesc(String username);

    Optional<RefreshTokens> findByUsuarioUsernameAndToken(String username, String token);

    Optional<RefreshTokens> findByToken(String token);
}
