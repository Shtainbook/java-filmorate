package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidator {

    public static void validationOfUsers(User user) {
        if (user == null) {
            log.error("ошибка зафиксирована: user - " + null);
            throw new ValidationException(ValidationException.class + " Пользователь отсутствует");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("ошибка зафиксирована: user - " + user);
            throw new ValidationException(ValidationException.class + " Нужно иметь почту или Собаку@");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("ошибка зафиксирована: user - " + user);
            throw new ValidationException(ValidationException.class + " Логин не должен быть пустым и без пробелов");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("ошибка зафиксирована: user - " + user);
            throw new ValidationException(ValidationException.class + " Нельзя родиться в будущем!");
        }
    }
}