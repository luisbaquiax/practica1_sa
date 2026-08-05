package org.luisbaquiax.jwtcaseapi.controllers.internal;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.UserEmailResponse;
import org.luisbaquiax.jwtcaseapi.services.adminusers.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/users/internal")
@RequiredArgsConstructor
public class UsersInternalController {

    private final UserService userService;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Boolean> existUser(@PathVariable Long idUsuario) {
        boolean exists = userService.existUser(idUsuario);
        return exists ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{idUsuario}/roles")
    public ResponseEntity<Boolean> existUserWithRole(@PathVariable Long idUsuario, @RequestParam String rol) {
        boolean exists = userService.existUserByIdAndRol(idUsuario, rol);
        return exists ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
