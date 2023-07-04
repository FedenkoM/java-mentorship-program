package org.spring.data.access.repository;

import org.spring.data.access.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryI extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Page<User> findByName(String name, Pageable pageable);
}
