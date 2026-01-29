package cl.fran.security_jwt_jpa.api.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Este método se invoca cuando un usuario intenta acceder a un recurso REST
    // protegido sin estar autenticado
    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        // Respondemos con un 401 Unauthorized y un JSON simple explicativo
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(
                "{\"error\": \"No autorizado\", \"message\": \"Debe iniciar sesión para acceder a este recurso.\"}");

        // Opcional: Podrías usar authException.getMessage() para dar más detalles,
        // pero a veces es mejor un mensaje genérico por seguridad.
    }
}
