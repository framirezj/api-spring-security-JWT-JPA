package cl.fran.security_jwt_jpa.api.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(

        @NotBlank(message = "El usuario no puede estar vacío")
        String username,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String password

) {
}
