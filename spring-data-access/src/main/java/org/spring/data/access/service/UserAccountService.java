package org.spring.data.access.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.data.access.exception.EntityNotFoundException;
import org.spring.data.access.model.UserAccount;
import org.spring.data.access.repository.UserAccountRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Transactional
    public UserAccount create(UserAccount userAccount) {
        if (Objects.isNull(userAccount)) {
            log.info("User account in null");
            return null;
        }
        log.info("Creating user account: " + userAccount);
        try {
            return userAccountRepository.save(userAccount);
        } catch (DataAccessException e) {
            log.info("User's account creation failed: " + e.getMessage());
            throw e;
        }
    }

    public UserAccount getUserAccountById(long id) {
        log.info("Find user account by id: {}", id);
        return userAccountRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public UserAccount getUserAccountByUserId(long id) {
        log.info("Find user account by userId: {}", id);
        return userAccountRepository.findByUserId(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public boolean deleteUserAccountByUserId(long userId) {
        log.info("Deleting userAccount for userId: {}", userId);
        return userAccountRepository.deleteByUserId(userId);
    }

    @Transactional
    public UserAccount withdraw(long userId, BigDecimal amount) {
        if (Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount is null or less then zero!");
        }
        log.info("Withdrawing user account, userId: {}, amount: {}", userId, amount);
        return userAccountRepository.withdraw(userId, amount);
    }

}
