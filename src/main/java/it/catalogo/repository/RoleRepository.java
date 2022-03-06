package it.catalogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.catalogo.model.Role;
import it.catalogo.model.Roles;


public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(Roles role);
}
