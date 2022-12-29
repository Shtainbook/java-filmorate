package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class UserValidator {

    public static void validationOfUsers(User user) {
        if (user == null) {
            log.error("ошибка зафиксирована: user - " + user);
            throw new UserValidationException("Пользователь отсутствует");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("ошибка зафиксирована: user - " + user);
            throw new UserValidationException("Нужно иметь почту или Собаку@");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("ошибка зафиксирована: user - " + user);
            throw new UserValidationException("Логин не должен быть пустым и без пробелов");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("ошибка зафиксирована: user - " + user);
            throw new UserValidationException("Нельзя родиться в будущем!");
        }
    }
}
