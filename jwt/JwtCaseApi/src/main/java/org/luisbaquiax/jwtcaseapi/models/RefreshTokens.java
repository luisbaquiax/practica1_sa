package org.luisbaquiax.jwtcaseapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RefreshTokens")
public class RefreshTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idToken;

    @JoinColumn(name = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Usuario usuario;
    private String token;
    private LocalDateTime expiraEn;
    private boolean revocado;
    private LocalDateTime createdAt;
}
