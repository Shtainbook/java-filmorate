package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    public void validationOfUsersShouldReturnFalseWhenEmailWithOutDog() {
        assertFalse(UserController.validationOfUsers(new User(1, "11.ru", "erken", "besty", LocalDate.of(1992, 12, 01))));
    }

    @Test
    public void validationOfUsersShouldReturnFalseWhenLoginIsBlankOrSpace() {
        assertFalse(UserController.validationOfUsers(new User(1, "1@1.ru ", " ", "besty", LocalDate.of(1992, 12, 01))));
    }

    @Test
    public void validationOfUsersShouldSetNameWhenHaveOnlyLogin() {
        User user = new User(1, "1@1.ru ", "erken", "", LocalDate.of(1992, 12, 01));
        UserController.validationOfUsers(user);
        assertEquals(user.getLogin(), user.getName());
    }

    @Test
    public void validationOfUsersShouldReturnFalseWhenBirtDateInFuture() {
        assertFalse(UserController.validationOfUsers(new User(1, "1@1.ru", "erken", "besty", LocalDate.of(2026, 12, 1))));
    }

    @Test
    public void validationOfUsersShouldReturnTrueWhenAllFine() {
        assertTrue(UserController.validationOfUsers(new User(1, "1@1.ru", "erken", "besty", LocalDate.of(1992, 12, 1))));
    }
}