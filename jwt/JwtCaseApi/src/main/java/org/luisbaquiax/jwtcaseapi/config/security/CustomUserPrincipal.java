package org.luisbaquiax.jwtcaseapi.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserPrincipal extends User {
    private final Long idUsuario;

    public CustomUserPrincipal(String username, String password, boolean enabled,
                               Collection<? extends GrantedAuthority> authorities,
                               Long idUsuario) {
        super(username, password, enabled, true, true, true, authorities);
        this.idUsuario = idUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }
}