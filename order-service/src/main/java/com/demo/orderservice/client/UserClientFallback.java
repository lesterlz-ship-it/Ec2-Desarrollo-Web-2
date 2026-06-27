package com.demo.orderservice.client;

import com.demo.orderservice.dto.UserSummaryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementación fallback del cliente Feign.
 * Se ejecuta cuando user-service no está disponible o responde con error.
 * Permite que order-service degrade su respuesta de forma controlada.
 */
@Component
@Slf4j
public class UserClientFallback implements UserServiceClient {

    @Override
    public UserSummaryDTO getUserById(Long id) {
        log.warn("FALLBACK: user-service no disponible para el usuario {}", id);
        return UserSummaryDTO.builder()
                .id(id)
                .name("Usuario no disponible")
                .email("n/a (user-service caído)")
                .build();
    }
}
