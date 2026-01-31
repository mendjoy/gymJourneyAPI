package io.github.mendjoy.gymJourneyAPI.repository;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmailIgnoreCaseAndVerifiedTrueAndActiveTrue(String email);

    Optional<User> findByToken(String token);

    Optional<Object> findByEmail(String email);
}
