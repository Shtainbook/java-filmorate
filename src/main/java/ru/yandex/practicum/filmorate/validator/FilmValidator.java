package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.EntityValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class FilmValidator {

    public static void validationOfFilm(Film film) {
        if (film == null) {
            log.error("ошибка зафиксирована: film - " + null);
            throw new EntityValidationException(EntityValidationException.class + " Отсутствует запись о фильме");
        }
        if (film.getName().isBlank()) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new EntityValidationException(EntityValidationException.class + " Название не должно быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new EntityValidationException(EntityValidationException.class + " Описание должно быть до 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new EntityValidationException(EntityValidationException.class + " Релиз должен быть не ранее 1895-12-28");
        }
        if (film.getDuration() < 0) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new EntityValidationException(EntityValidationException.class + " Длительность должна быть позитивной");
        }
    }
}