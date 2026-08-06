package org.luisbaquiax.jwtcaseapi.repositories;

import org.luisbaquiax.jwtcaseapi.models.Rol;
import org.luisbaquiax.jwtcaseapi.models.Usuario;
import org.luisbaquiax.jwtcaseapi.models.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    boolean existsByUsuarioAndRol(Usuario usuario, Rol rol);
}
