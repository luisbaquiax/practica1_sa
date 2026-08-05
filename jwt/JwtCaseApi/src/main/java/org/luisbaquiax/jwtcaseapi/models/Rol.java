package org.luisbaquiax.jwtcaseapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private NombreRol nombre;
    private String descripcion;

    public enum NombreRol {
        ROLE_ADMIN_SISTEMA,
        ROLE_ADMIN_CINE,
        ROLE_ANUNCIANTE,
        ROLE_USUARIO
    }
}
