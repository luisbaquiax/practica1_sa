package org.luisbaquiax.jwtcaseapi.mappers;

import org.luisbaquiax.jwtcaseapi.dtos.RolDTO;
import org.luisbaquiax.jwtcaseapi.models.Rol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper {

    RolDTO toRolDTO(Rol rol);

}
