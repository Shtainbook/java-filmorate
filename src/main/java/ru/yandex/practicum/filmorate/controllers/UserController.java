package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    final static Map<Integer, User> userBase = new HashMap<>();

    @GetMapping
    public static List<User> getUsersUnique() {
        log.debug("Данные о пользователях получены");
        return new ArrayList<>(userBase.values());
    }

    @PostMapping
    public static void addFilm(@Valid @NotNull @RequestBody User user) {
        UserValidator.validationOfUsers(user);

        userBase.put(user.getId(), user);
        log.debug("Пользователь успешно добавлен: " + user + ".");
    }

    @PutMapping
    public static void updateFilm(@Valid @NotNull @RequestBody User user) {
        UserValidator.validationOfUsers(user);

        userBase.put(user.getId(), user);
        log.debug("Пользователь успешно обновлен: " + user + ".");
    }

}
