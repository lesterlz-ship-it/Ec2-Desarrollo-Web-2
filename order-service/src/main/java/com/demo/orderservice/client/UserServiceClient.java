package com.demo.orderservice.client;

import com.demo.orderservice.dto.UserSummaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign que se comunica con user-service.
 * Esta es la pieza clave de la comunicación inter-servicio:
 * order-service llama a user-service para enriquecer las órdenes.
 *
 * Si el servicio remoto no responde, Feign lanza FeignException
 * y nuestro GlobalExceptionHandler la convierte en respuesta HTTP adecuada.
 */
@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserServiceClient {

    /**
     * Obtiene un usuario por su ID desde el user-service.
     */
    @GetMapping("/api/users/{id}")
    UserSummaryDTO getUserById(@PathVariable("id") Long id);
}
