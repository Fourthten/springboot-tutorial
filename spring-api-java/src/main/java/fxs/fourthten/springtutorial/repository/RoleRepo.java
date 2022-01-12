package fxs.fourthten.springtutorial.repository;

import fxs.fourthten.springtutorial.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findOneById(UUID id);
    Role findOneByName(String name);
    Role findOneByIdNotAndName(UUID id, String name);
}
