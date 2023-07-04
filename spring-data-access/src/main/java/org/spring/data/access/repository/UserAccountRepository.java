package org.spring.data.access.repository;

import org.spring.data.access.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserId(long userId);
    boolean deleteByUserId(long userId);
    @Query(value = "Update user_account set balance = balance - :amount where userID = :userId", nativeQuery = true)
    UserAccount withdraw(@Param("userId") long userId,
                         @Param("amount") BigDecimal amount);
}
