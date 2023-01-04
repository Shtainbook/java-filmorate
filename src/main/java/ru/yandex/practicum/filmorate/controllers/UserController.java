package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> userBase = new HashMap<>();

    @GetMapping
    public List<User> getUsers() {

        log.debug("Данные о пользователях получены");
        return new ArrayList<>(userBase.values());
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        if (user.getId() < 1) {
            user.setId(user.getUserIdGenerator() + 1);
        }

        if (user.getName() == null){
            user.setName(user.getLogin());
        }

        log.debug("Пользователь успешно добавлен: " + user + ".");
        return null != userBase.put(user.getId(), user) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        if (!userBase.containsKey(user.getId())) {
            throw new UserValidationException(HttpStatus.NOT_FOUND, "");
        }
        log.debug("Пользователь успешно обновлен: " + user + ".");
        return null != userBase.put(user.getId(), user) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }
}