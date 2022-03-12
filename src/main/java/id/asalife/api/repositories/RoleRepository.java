package id.asalife.api.repositories;

import id.asalife.api.domains.ERole;
import id.asalife.api.domains.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}