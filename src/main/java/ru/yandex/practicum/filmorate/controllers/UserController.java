package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> userBase = new HashMap<>();
    long userIdGenerator = 1;

    @GetMapping
    public List<User> getUsers() {
        log.debug("Данные о пользователях получены");
        return new ArrayList<>(userBase.values());
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        try {
            if (user.getId() < 1) {
                user.setId(userIdGenerator++);
            }
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            userBase.put(user.getId(), user);
            log.debug("Пользователь успешно добавлен: " + user + ".");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Проблема с добавлением Юзера.");
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        try {
            if (!userBase.containsKey(user.getId())) {
                log.debug("Проблема с обновлением Юзера. Несоответствие ID.");
                return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
            }
            userBase.put(user.getId(), user);
            log.debug("Пользователь успешно обновлен: " + user + ".");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Проблема с обновлением Юзера. Юзер не обновлен.");
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }

    public long getUserIdGenerator() {
        return userIdGenerator;
    }
}