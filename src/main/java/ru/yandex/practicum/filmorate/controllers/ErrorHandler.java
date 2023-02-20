package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationException> validationException(ValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ValidationException(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundException> notFoundObject(NotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new NotFoundException(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<RuntimeException> runTimeException(RuntimeException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new RuntimeException(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}



