package org.luisbaquiax.jwtcaseapi.services.adminusers;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.jwtcaseapi.dtos.RolDTO;
import org.luisbaquiax.jwtcaseapi.mappers.RolMapper;
import org.luisbaquiax.jwtcaseapi.repositories.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    /**
     * Obtiene una lista de todos los roles disponibles en el sistema.
     * @return Una lista de objetos Rol que representan los roles disponibles.
     */
    public List<RolDTO> getAllRoles() {
        return rolRepository.findAll().stream().map(rolMapper::toRolDTO).toList();
    }

}
