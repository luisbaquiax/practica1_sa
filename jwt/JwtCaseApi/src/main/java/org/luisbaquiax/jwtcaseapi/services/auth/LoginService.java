package org.luisbaquiax.jwtcaseapi.services.auth;

import org.luisbaquiax.jwtcaseapi.dtos.AutenticacionRequest;
import org.luisbaquiax.jwtcaseapi.dtos.AutenticacionResponse;

public interface LoginService {
    AutenticacionResponse login(AutenticacionRequest request);
}
