package org.luisbaquiax.jwtcaseapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario_rol")
public class UsuarioRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_rol")
    private Long id;

    @JoinColumn(name = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Usuario usuario;

    public LocalDate fechaAsignacion;

    @JoinColumn(name = "id_rol")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Rol rol;
}
