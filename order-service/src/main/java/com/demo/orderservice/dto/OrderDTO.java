package com.demo.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO que representa una orden expuesta en la API del order-service.
 * Incluye la información del usuario obtenida desde user-service vía Feign.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private Long userId;
    private String product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /** Datos del usuario obtenidos del user-service (comunicación inter-servicio). */
    private UserSummaryDTO user;
}
