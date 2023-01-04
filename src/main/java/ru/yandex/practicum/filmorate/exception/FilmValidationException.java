package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;

public class FilmValidationException extends RuntimeException {
    public FilmValidationException(HttpStatus httpStatus, String message) {
        super(message);
    }
}
