package id.asalife.api.repositories;

import id.asalife.api.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

public interface UserRepository extends JpaRepository<User, Long> {
    @Nullable
    User findByNrp(String nrp);

    @Query("FROM User u WHERE u.otp = ?1")
    User findOneByOTP(String otp);
}