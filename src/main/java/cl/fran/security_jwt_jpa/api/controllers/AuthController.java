package cl.fran.security_jwt_jpa.api.controllers;


import cl.fran.security_jwt_jpa.api.dto.LoginDto;
import cl.fran.security_jwt_jpa.api.dto.RegisterDto;
import cl.fran.security_jwt_jpa.api.models.Role;
import cl.fran.security_jwt_jpa.api.models.UserEntity;
import cl.fran.security_jwt_jpa.api.repositories.RoleRepository;
import cl.fran.security_jwt_jpa.api.repositories.UserRepository;
import cl.fran.security_jwt_jpa.api.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // --- Endpoint de Login ---
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto){

        // 1. Intentamos autenticar con el Manager de Spring Security
        // Si la contraseña es incorrecta, esto lanzará una excepción (BadCredentialsException)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.username(),
                        loginDto.password()
                )
        );

        // 2. Si pasamos la línea anterior, es que las credenciales son válidas.
        // Guardamos la autenticación en el contexto (opcional para stateless, pero buena práctica)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generamos el Token JWT
        String token = jwtUtil.generateToken(loginDto.username());

        // 4. Retornamos el token al cliente
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){


        // 1. Verificamos si el usuario ya existe
        if (userRepository.existsByUsername(registerDto.username())) {
            return new ResponseEntity<>("El usuario ya existe ", HttpStatus.BAD_REQUEST);
        }

        // 2. Buscamos el rol "ROLE_USER" para asignarlo por defecto
        // OJO: Este rol DEBE existir en tu base de datos (veremos esto en el Paso 10)
        Role userRole =  roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Error: El rol ROLE_USER no existe en la BD"));

        // 3. Construimos el usuario usando el patrón Builder
        UserEntity user = UserEntity.builder()
                .username(registerDto.username())
                // ¡CRUCIAL! Nunca guardes la password en texto plano
                .password(passwordEncoder.encode(registerDto.password()))
                .roles(Set.of(userRole))
                .build();

        // 4. Guardamos en BD
        userRepository.save(user);

        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
    }

}
