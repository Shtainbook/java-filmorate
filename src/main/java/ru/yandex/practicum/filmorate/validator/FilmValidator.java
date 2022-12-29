package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class FilmValidator {

    public static void validationOfFilm(Film film) {
        if (film == null) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new FilmValidationException("Отсутствует запись о фильме");
        }
        if (film.getName().isBlank()) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new FilmValidationException("Название не должно быть пустым");
        }

        if (film.getDescription().length() > 200) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new FilmValidationException("Описание должно быть до 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28))) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new FilmValidationException("Релиз должен быть не ранее 1985-12-28");
        }

        if (film.getDuration().isNegative()) {
            log.error("ошибка зафиксирована: film - " + film);
            throw new FilmValidationException("Длительность должна быть позитивной");
        }
    }
}
