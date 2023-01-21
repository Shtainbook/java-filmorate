package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    @Getter
    private final Map<Integer, User> userBase = new HashMap<>();
    int userIdGenerator = 1;

    @Override
    public ResponseEntity<User> create(User user) {
        UserValidator.validationOfUsers(user);
        user.setId(userIdGenerator++);
        userBase.put(user.getId(), user);
        log.debug("Пользователь успешно добавлен: " + user + ".");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> read(int id) {
        if (!userBase.containsKey(id)) {
            throw new NotFoundException("нет такого закона");
        }
        log.debug("Действие read залогировано");
        return new ResponseEntity<>(userBase.get(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> readAll() {
        log.debug("Действие readAll залогировано");
        return new ResponseEntity<>(new ArrayList<>(userBase.values()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> update(User user) {
        UserValidator.validationOfUsers(user);
        if (userBase.containsKey(user.getId())) {
            userBase.put(user.getId(), user);
            log.debug("Пользователь успешно обновлен: " + user + ".");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        log.debug("Пользователь не обновлен: " + user + ".");
        throw new NotFoundException("нет такого закона");
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        if (userBase.containsKey(id)) {
            userBase.remove(id);
            log.debug("Пользователь успешно удален");
            return new ResponseEntity<>("Пользователь успешно удален.", HttpStatus.OK);
        }
        log.debug("Пользователь успешно не удален");
        throw new NotFoundException("нет такого закона");
    }
}
