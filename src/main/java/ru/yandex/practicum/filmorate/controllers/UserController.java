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
            //return null != userBase.put(user.getId(), user) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        try {
            if (!userBase.containsKey(user.getId())) {
                //throw new UserValidationException("Такого юезра нет. Обновлять нечего.");
                return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
            }

            log.debug("Пользователь успешно обновлен: " + user + ".");
            userBase.put(user.getId(), user);
            //return null != userBase.put(user.getId(), user) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }


    public long getUserIdGenerator() {
        return userIdGenerator;
    }
}