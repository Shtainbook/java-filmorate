package ru.yandex.practicum.filmorate.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserValidationException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({FilmValidationException.class, UserValidationException.class})
    public ResponseEntity<UserValidationException> validationException(RuntimeException e) {
        log.error("validationException");
        return new ResponseEntity<>(new UserValidationException(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundException> notFoundObject(NotFoundException e) {
        log.error("notFoundObject");
        return new ResponseEntity<>(new NotFoundException(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<RuntimeException> runTimeException(RuntimeException e) {
        log.error("runTimeException");
        return new ResponseEntity<>(new RuntimeException(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}