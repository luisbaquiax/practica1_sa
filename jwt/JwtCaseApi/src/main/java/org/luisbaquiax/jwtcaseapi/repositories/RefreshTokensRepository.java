package org.luisbaquiax.jwtcaseapi.repositories;

import org.luisbaquiax.jwtcaseapi.models.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokensRepository extends JpaRepository<RefreshTokens, Long> {

    Optional<RefreshTokens> findByUsuarioUsernameAndToken(String username, String token);

    @Modifying
    @Query(
            "UPDATE RefreshTokens r " +
            "SET r.revocado = true " +
            "WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.revocado = false")
    void revocarTodosPorUsuario(@Param("idUsuario") Long idUsuario);
}
