package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.time.LocalDate;

class FilmValidatorTest {
    @Test
    public void validationFilmThrowExceptionWhenFilmNameIsBlank() {
        FilmValidationException exception = Assertions.assertThrows(FilmValidationException.class, () ->
               FilmValidator.validationOfFilm(
                       new Film(1, " ", "эротодрамма", LocalDate.of(2005, 12, 22), 45)));

        Assertions.assertEquals("Название не должно быть пустым", exception.getMessage());
    }

    @Test
    public void validationFilmThrowExceptionWhenWhenDescriptionToMuchLong() {
        FilmValidationException exception = Assertions.assertThrows(FilmValidationException.class, () ->
                FilmValidator.validationOfFilm(
                        new Film(1, "медвежуть", "путинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегдапутинмедведевшойгунавсегда", LocalDate.of(2005, 12, 22), 45)));

        Assertions.assertEquals("Описание должно быть до 200 символов", exception.getMessage());
    }

    @Test
    public void validationFilmThrowExceptionWhenDateBefore1985() {
        FilmValidationException exception = Assertions.assertThrows(FilmValidationException.class, () ->
                FilmValidator.validationOfFilm(
                        new Film(1, "медвежуть", "эротодрамма", LocalDate.of(1892, 12, 22), 45)));

        Assertions.assertEquals("Релиз должен быть не ранее 1895-12-28", exception.getMessage());
    }

    @Test
    public void validationFilmThrowExceptionWhenDurationIsMinus() {
        FilmValidationException exception = Assertions.assertThrows(FilmValidationException.class, () ->
                FilmValidator.validationOfFilm(
                        new Film(1, "медвежуть", "эротодрамма", LocalDate.of(1999, 12, 22), -2)));

        Assertions.assertEquals("Длительность должна быть позитивной", exception.getMessage());
    }

    @Test
    public void validationOfShouldntMakeExceptionWhenAllFine() {
        FilmValidator.validationOfFilm(
                new Film(1, "медвежуть", "эротодрамма", LocalDate.of(2005, 12, 22), 45));

        Assertions.assertEquals("test", "test");
    }
}