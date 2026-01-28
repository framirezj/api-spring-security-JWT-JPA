package cl.fran.security_jwt_jpa.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(

        @NotBlank(message = "El usuario es obligatorio")
        @Size(min = 4, max = 20, message = "El usuario debe tener entre 4 y 20 caracteres")
        String username,


        @NotBlank(message = "La constraseña es obligatoria")
        @Size(min = 6, message = "La contraseña de tener al menos 6 caracteres")
        String password
) {
}
