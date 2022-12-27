package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    final static Map<Integer, User> userBase = new HashMap<>();

    public static boolean validationOfUsers(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) return false;
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) return false;
        if (user.getBirthday().isAfter(LocalDate.now())) return false;
        return true;
    }

    @GetMapping("/users-unique")
    public static List<User> getUsersUnique() {
        log.debug("Данные о пользователях получены");
        return new ArrayList<>(userBase.values());
    }

    @PostMapping("/user-unique")
    public static void addFilm(@Valid @NotNull @RequestBody User user) {
             if (!validationOfUsers(user)) {
                throw new ValidationException("Тут какой-то текст по валдиц");
            }

            userBase.put(user.getId(), user);
            log.debug("Пользователь успешно добавлен: " + user + ".");
    }

    @PutMapping("/update-user")
    public static void updateFilm(@Valid @NotNull @RequestBody User user) {
        if (!validationOfUsers(user)) {
            throw new ValidationException("Тут какой-то текст по валдиц");
        }

        userBase.put(user.getId(), user);
        log.debug("Пользователь успешно обновлен: " + user + ".");
    }

}
