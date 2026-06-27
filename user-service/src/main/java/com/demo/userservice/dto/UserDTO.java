package com.demo.userservice.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO que representa un usuario expuesto en la API.
 * Generado a partir de la entidad User mediante MapStruct.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
