package cl.fran.security_jwt_jpa.api.security;


import cl.fran.security_jwt_jpa.api.models.UserEntity;
import cl.fran.security_jwt_jpa.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    /*
    LA ANOTACION @REQUIREDARGSCONSTRUICTOR SE ENCARGA DE HACER LA INYECCION POR CONSTRUCTOR
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    */


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //buscar el usuario en la DB, si no existe lanza error

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Usuario no encontrado " + username)
                );

        //convierte los roles del usuario a "GrantedAuthority" de Spring Security

        Set<GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        //retorna el objeto de spring

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
    }


}
