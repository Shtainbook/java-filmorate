package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    ResponseEntity<User> create(User user);

    ResponseEntity<User> read(int id);

    ResponseEntity<List<User>> readAll();

    ResponseEntity<?> update(User user);

    ResponseEntity<?> delete(int id);
}
