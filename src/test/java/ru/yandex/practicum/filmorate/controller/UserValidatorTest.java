package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.EntityValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @Test
    public void validationUserThrowExceptionWhenEmailWithOutDog() {
        EntityValidationException exception = Assertions.assertThrows(EntityValidationException.class, () ->
                UserValidator.validationOfUsers(
                        new User(1, "11.ru", "erken", "besty", LocalDate.of(1992, 12, 01))));

        Assertions.assertEquals("class ru.yandex.practicum.filmorate.exception.EntityValidationException Нужно иметь почту или Собаку@", exception.getMessage());
    }

    @Test
    public void validationUserThrowExceptionWhenLoginIsBlankOrSpace() {
        EntityValidationException exception = Assertions.assertThrows(EntityValidationException.class, () ->
                UserValidator.validationOfUsers(
                        new User(1, "1@1.ru ", " ", "besty", LocalDate.of(1992, 12, 01))));

        Assertions.assertEquals("class ru.yandex.practicum.filmorate.exception.EntityValidationException Логин не должен быть пустым и без пробелов", exception.getMessage());
    }

    @Test
    public void validationUserThrowExceptionWhenBirtDateInFuture() {
        EntityValidationException exception = Assertions.assertThrows(EntityValidationException.class, () ->
                UserValidator.validationOfUsers(
                        new User(1, "1@1.ru", "erken", "besty", LocalDate.of(2026, 12, 1))));

        Assertions.assertEquals("class ru.yandex.practicum.filmorate.exception.EntityValidationException Нельзя родиться в будущем!", exception.getMessage());
    }

    @Test
    public void validationOfUsersShouldSetNameWhenHaveOnlyLogin() {
        User user = new User(1, "1@1.ru", "erken", "", LocalDate.of(1992, 12, 01));
        UserValidator.validationOfUsers(user);

        assertEquals(user.getLogin(), user.getName());
    }

    @Test
    public void validationOfUsersShouldNotThrowExceptionWhenAllFine() {
        User user = new User(1, "1@1.ru", "erken", "erkek", LocalDate.of(1992, 12, 01));
        UserValidator.validationOfUsers(user);

        assertEquals("test", "test");
    }
}