package org.luisbaquiax.jwtcaseapi.repositories;

import org.luisbaquiax.jwtcaseapi.models.Rol;
import org.luisbaquiax.jwtcaseapi.models.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"roles", "roles.rol"})
    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT COUNT(u) > 0  FROM Usuario u WHERE u.username = :username AND u.idUsuario <> :idUsuario")
    boolean existsByUsernameDifferentId(String username, Long idUsuario);

    @Query("SELECT COUNT(u) > 0  FROM Usuario u WHERE u.email = :username AND u.idUsuario <> :idUsuario")
    boolean existsByEmailDifferentId(String username, Long idUsuario);

    List<Usuario> findByActivo(boolean activo);

    @Query("""
                    SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
                    FROM Usuario u
                    JOIN u.roles ur
                    JOIN ur.rol r
                    WHERE u.idUsuario = :idUsuario AND r.nombre = :nameRol
            """)
    boolean existsByIdUsuarioAndRole(Long idUsuario, Rol.NombreRol nameRol);
}
