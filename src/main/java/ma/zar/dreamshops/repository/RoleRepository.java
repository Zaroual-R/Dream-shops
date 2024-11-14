package ma.zar.dreamshops.repository;

import ma.zar.dreamshops.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String role);

    boolean existsByName(String role);
}
