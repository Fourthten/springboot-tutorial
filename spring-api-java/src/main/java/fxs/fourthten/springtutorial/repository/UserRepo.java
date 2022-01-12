package fxs.fourthten.springtutorial.repository;

import fxs.fourthten.springtutorial.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findOneById(UUID id);
    User findOneByUsername(String username);
    User findOneByUsernameOrEmail(String username, String email);
    User findOneByIdNotAndUsernameOrEmail(UUID id, String username, String email);
}
