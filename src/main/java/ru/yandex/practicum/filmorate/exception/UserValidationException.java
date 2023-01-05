package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}