package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;

public class FilmValidationException extends RuntimeException {
    public FilmValidationException(String message) {
        super(message);
    }
}
