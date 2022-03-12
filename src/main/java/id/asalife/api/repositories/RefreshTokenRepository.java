package id.asalife.api.repositories;

import id.asalife.api.domains.entities.RefreshToken;
import id.asalife.api.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findById(Long id);

    RefreshToken findByToken(String token);

    void deleteByTokenEquals(String token);

    RefreshToken findFirstByUser(User user);
}
