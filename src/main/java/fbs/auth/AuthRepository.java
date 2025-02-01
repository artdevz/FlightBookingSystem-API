package fbs.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fbs.models.User;

@Repository
public interface AuthRepository extends JpaRepository<User, UUID> {
    
    public Optional<User> findByEmail(String login);

}
