package org.luisbaquiax.jwtcaseapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private String nombres;
    @Column(nullable = false)
    private String apellidos;
    @Column(nullable = false)
    private String telefono;
    private LocalDate fechaNacimiento;
    private boolean activo;
    private boolean dobleFactorAuth;
    private boolean primeraVez;

    @Enumerated(EnumType.STRING)
    private MetodoAutenticacion metodo2FA;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<UsuarioRol> roles = new ArrayList<>();

    public enum MetodoAutenticacion {
        PASSWORD,
        EMAIL,
        SMS
    }

}
