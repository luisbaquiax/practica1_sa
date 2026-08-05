package org.luisbaquiax.jwtcaseapi.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final FilterJWT jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(
                                        "/v1/users/registration/**",
                                        "/v1/users/auth/**"
//                                        "/v3/api-docs/**",
//                                        "/swagger-ui/**"
                                ).permitAll()


                                .requestMatchers(
                                        "/v1/users/internal/**",
                                        "/v1/users/common/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN_SISTEMA",
                                        "ROLE_USUARIO",
                                        "ROLE_ADMIN_CINE",
                                        "ROLE_ANUNCIANTE")

                                .requestMatchers(
                                        "/v1/users/admin/**"
                                ).hasAnyAuthority("ROLE_ADMIN_SISTEMA")

                                .requestMatchers(
                                        "/v1/users/me/**"
                                ).hasAnyAuthority(
                                        "ROLE_ADMIN_SISTEMA",
                                        "ROLE_ADMIN_CINE",
                                        "ROLE_ANUNCIANTE",
                                        "ROLE_USUARIO")
                                .anyRequest().authenticated()
                )
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
