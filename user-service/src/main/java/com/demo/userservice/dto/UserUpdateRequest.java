package com.demo.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO para actualizar un usuario existente (todos los campos son opcionales).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {

    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    @Size(max = 20, message = "El teléfono no debe exceder 20 caracteres")
    private String phone;

    @Size(max = 200, message = "La dirección no debe exceder 200 caracteres")
    private String address;
}
