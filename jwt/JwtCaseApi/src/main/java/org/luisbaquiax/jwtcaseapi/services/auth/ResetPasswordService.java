package org.luisbaquiax.jwtcaseapi.services.auth;


import org.luisbaquiax.jwtcaseapi.dtos.ChangePasswordRequest;
import org.luisbaquiax.jwtcaseapi.dtos.ConfirmResetPasswordRequest;
import org.luisbaquiax.jwtcaseapi.dtos.MessageSuccess;

public interface ResetPasswordService {
    MessageSuccess resetPassword(String email);

    MessageSuccess confirmResetPassword(ConfirmResetPasswordRequest request);

    MessageSuccess changePassword(Long idUsuario, ChangePasswordRequest request);

}
