package org.spring.data.access.repository;

import lombok.Setter;
import org.spring.data.access.annotation.BindStaticData;
import org.spring.data.access.annotation.LogMethod;
import org.spring.data.access.model.User;
import org.spring.data.access.util.IDGenerator;
import org.spring.data.access.util.Paginator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Setter
@Repository
public class UserRepository {

    @BindStaticData(fileLocation = "static/userData.json", castTo = User.class)
    private final Map<Long, User> userMap = new HashMap<>();
    private IDGenerator idGenerator;
    private Paginator<User> paginator;

    @LogMethod
    public Optional<User> findById(long userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

    @LogMethod
    public Optional<User> findByEmail(String email) {
        return filter(user -> user.getEmail().equals(email)).stream().findFirst();
    }

    @LogMethod
    public List<User> findByName(String name, int pageSize, int pageNum) {
        return paginator.getPage(filter(user -> user.getName().contains(name)), pageNum, pageSize);
    }

    @LogMethod
    public User create(User user) {
        long userId = idGenerator.generate(User.class);
        user.setId(userId);
        userMap.put(userId, user);
        return user;
    }

    @LogMethod
    public User update(User user) {
        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
        }
        return null;
    }

    @LogMethod
    public boolean delete(long userId) {
        if (userMap.containsKey(userId)) {
            userMap.remove(userId);
            return true;
        }
        return false;
    }

    private List<User> filter(Predicate<User> predicate) {
        return userMap
            .values()
            .stream()
            .filter(predicate)
            .toList();
    }
}
