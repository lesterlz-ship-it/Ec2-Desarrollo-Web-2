package com.demo.orderservice.dto;

import lombok.*;

/**
 * Resumen de los datos del usuario que se incluyen dentro del OrderDTO.
 * Se construye a partir de la respuesta del user-service (vía OpenFeign).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSummaryDTO {

    private Long id;
    private String name;
    private String email;
}
