package org.spring.data.access.service;

import lombok.RequiredArgsConstructor;
import org.spring.data.access.model.User;
import org.spring.data.access.repository.UserRepositoryI;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryI userRepository;

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public Page<User> getUsersByName(String name, Pageable pageable) {
        return userRepository.findByName(name, pageable);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        var existingUser = userRepository.findById(user.getId()).orElseThrow();
        BeanUtils.copyProperties(user, existingUser, "id");
        return userRepository.save(existingUser);
    }

    public boolean deleteUser(long userId) {
        var user = getUserById(userId);
        if (Objects.nonNull(user)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}
