package com.demo.orderservice.mapper;

import com.demo.orderservice.dto.OrderCreateRequest;
import com.demo.orderservice.dto.OrderDTO;
import com.demo.orderservice.entity.Order;
import com.demo.orderservice.entity.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper MapStruct para convertir entre la entidad Order y sus DTOs.
 * Implementación generada en tiempo de compilación.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    /**
     * Convierte una entidad Order a OrderDTO sin incluir datos del usuario.
     * El servicio se encarga de enriquecer el DTO con la llamada al user-service.
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "status", target = "status", qualifiedByName = "statusToString")
    OrderDTO toDTO(Order order);

    List<OrderDTO> toDTOList(List<Order> orders);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "status", target = "status", qualifiedByName = "stringToStatus")
    Order toEntity(OrderCreateRequest request);

    @Named("statusToString")
    default String statusToString(OrderStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("stringToStatus")
    default OrderStatus stringToStatus(String status) {
        if (status == null || status.isBlank()) return OrderStatus.PENDING;
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OrderStatus.PENDING;
        }
    }
}
