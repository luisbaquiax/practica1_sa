package org.luisbaquiax.jwtcaseapi.mappers;

import org.luisbaquiax.jwtcaseapi.dtos.RolDTO;
import org.luisbaquiax.jwtcaseapi.dtos.UpdateProfileRequest;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunRequest;
import org.luisbaquiax.jwtcaseapi.dtos.UsuarioComunResponse;
import org.luisbaquiax.jwtcaseapi.models.Usuario;
import org.luisbaquiax.jwtcaseapi.models.UsuarioRol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Usuario toEntity(UsuarioComunRequest request);

    @Mapping(target = "roles", source = "roles")
    UsuarioComunResponse toUsuarioComunResponse(Usuario usuario);

    @Mapping(target = "nombre", source = "usuarioRol.rol.nombre")
    @Mapping(target = "idRol", source = "usuarioRol.rol.idRol")
    @Mapping(target = "descripcion", source = "usuarioRol.rol.descripcion")
    RolDTO toRolDTO(UsuarioRol usuarioRol);

    void toUpdatedEntity(UpdateProfileRequest request, @MappingTarget Usuario existingUsuario);

}
