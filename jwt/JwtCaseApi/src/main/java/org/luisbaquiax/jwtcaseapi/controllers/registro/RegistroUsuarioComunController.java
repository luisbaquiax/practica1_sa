package org.luisbaquiax.jwtcaseapi.controllers.registro;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunRequest;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunResponse;
import org.luisbaquiax.jwtcaseapi.services.register.RegistroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users/registration")
@RequiredArgsConstructor
public class RegistroUsuarioComunController {
    private final RegistroService registroService;

    @PostMapping
    public ResponseEntity<UsuarioComunResponse> register(@RequestBody @Valid UsuarioComunRequest request) {
        return ResponseEntity.ok(registroService.register(request));
    }

}
