package cl.fran.security_jwt_jpa.api.repositories;

import cl.fran.security_jwt_jpa.api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
