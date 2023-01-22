package ru.yandex.practicum.filmorate.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.EntityValidationException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<EntityValidationException> validationException(EntityValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new EntityValidationException(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundException> notFoundObject(NotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new NotFoundException(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<RuntimeException> runTimeException(RuntimeException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new RuntimeException(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}