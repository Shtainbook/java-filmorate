package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    ResponseEntity<User> create(User user);

    ResponseEntity<User> read(int id);

    ResponseEntity<List<User>> readAll();

    ResponseEntity<?> update(User user);

    ResponseEntity<?> delete(int id);

    void addToFriend(int id, int targetId);

    void deleteFromFriend(int id, int targetId);

    Set<Integer> getFriends(int id);

    boolean contains(int id);

    User getUser(int id);

}
