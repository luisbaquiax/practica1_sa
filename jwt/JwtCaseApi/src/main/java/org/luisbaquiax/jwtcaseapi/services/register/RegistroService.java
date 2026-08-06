package org.luisbaquiax.jwtcaseapi.services.register;


import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunRequest;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunResponse;

public interface RegistroService {
    UsuarioComunResponse register(UsuarioComunRequest request);
}
