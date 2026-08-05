package org.luisbaquiax.jwtcaseapi.controllers;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.MessageSuccess;
import org.luisbaquiax.jwtcaseapi.dtos.RolDTO;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunResponse;
import org.luisbaquiax.jwtcaseapi.models.Rol;
import org.luisbaquiax.jwtcaseapi.services.adminusers.AdminUsersService;
import org.luisbaquiax.jwtcaseapi.services.adminusers.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/users/admin")
@RequiredArgsConstructor
public class AdminUsersController {

    private final RoleService roleService;
    private final AdminUsersService adminUsersService;

    @GetMapping("/roles")
    public ResponseEntity<List<RolDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/users/{idUsuario}/roles/{idRol}")
    public ResponseEntity<UsuarioComunResponse> asignarRol(@PathVariable Long idUsuario, @PathVariable Long idRol) {
        return ResponseEntity.ok(adminUsersService.asignarRol(idUsuario, idRol));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsuarioComunResponse>> getAllUsers(
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) String rol) {
        if (Objects.nonNull(activo)) {
            return ResponseEntity.ok(adminUsersService.getAllUsersByStatus(activo));
        } else if (Objects.nonNull(rol)) {
            return ResponseEntity.ok(adminUsersService.getAllUsersByRol(Rol.NombreRol.valueOf(rol)));
        } else {
            return ResponseEntity.ok(adminUsersService.getAllUsers());
        }
    }

    @PatchMapping("/users/{idUsuario}/{activar}")
    public ResponseEntity<MessageSuccess> cambiarEstadoUsuario(
            @PathVariable Long idUsuario,
            @PathVariable boolean activar) {
        var response = activar ? adminUsersService.activarUsuario(idUsuario) : adminUsersService.desactivarUsuario(idUsuario);
        return ResponseEntity.ok(response);
    }

}
