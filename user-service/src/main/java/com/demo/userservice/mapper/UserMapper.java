package com.demo.userservice.mapper;

import com.demo.userservice.dto.UserCreateRequest;
import com.demo.userservice.dto.UserDTO;
import com.demo.userservice.dto.UserUpdateRequest;
import com.demo.userservice.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Mapper MapStruct para convertir entre la entidad User y sus DTOs.
 * Se genera una implementación en tiempo de compilación.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convierte una entidad User a UserDTO.
     */
    UserDTO toDTO(User user);

    /**
     * Convierte una lista de entidades User a una lista de UserDTO.
     */
    List<UserDTO> toDTOList(List<User> users);

    /**
     * Convierte un UserCreateRequest a una nueva entidad User.
     * Los campos createdAt y updatedAt son manejados por @PrePersist en la entidad.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserCreateRequest request);

    /**
     * Actualiza una entidad User existente a partir de un UserUpdateRequest.
     * Los campos nulos en el request son ignorados (no sobrescriben el valor actual).
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UserUpdateRequest request, @MappingTarget User user);
}
