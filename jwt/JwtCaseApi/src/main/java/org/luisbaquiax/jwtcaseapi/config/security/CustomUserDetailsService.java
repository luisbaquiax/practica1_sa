package org.luisbaquiax.jwtcaseapi.config.security;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.models.Usuario;
import org.luisbaquiax.jwtcaseapi.repositories.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));

        List<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(usuarioRol -> new SimpleGrantedAuthority(
                        usuarioRol.getRol().getNombre().name()
                ))
                .collect(Collectors.toList());
        return new User(
                usuario.getUsername(),
                usuario.getPasswordHash(),
                usuario.isActivo(),
                true,
                true,
                true,
                authorities
        );
    }
}
